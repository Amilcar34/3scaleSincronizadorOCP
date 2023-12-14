package app;


import static app.Main.ejecute;
import static app.Main.ejecuteResponse;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;

import app.helper.Progress;
import app.model.Backend;
import app.model.Condition;
import app.model.MappingRule;
import app.model.Spec;
import app.model.Status;
import app.model.Type;

public class Sincro3ScaleWhitAzure {

	private final String NAMESPACE;
	private final String ssh_repo;
	private final String PWD_3SCALE;
	static final String ignore = "protesis-bff";

	public Sincro3ScaleWhitAzure(String nAMESPACE, String sCALE, String pWD_3SCALE) {
		this.NAMESPACE = nAMESPACE;
		this.ssh_repo = sCALE;
		this.PWD_3SCALE = pWD_3SCALE;
		this.replace = "-" + NAMESPACE;
	}

	public String getPATH_3SCALE(String PWD_3SCALE) {
		return PWD_3SCALE + "/test/backends";
//		return PWD_3SCALE + "/test/" + NAMESPACE + "/backends";
	}

	public void star() throws InterruptedException, IOException {

		ejecute("oc project 3scale");

		System.out.println("----- Inicializa BackendsFrom3Scale -----");
		Progress.runner();
		BackendsFrom3Scale();
		Progress.stall();

		System.out.println("----- Inicializa BackendsFromAzure -----\n");
		BackendsFromAzure();

		System.out.println("----- Inicializa BackendsCompare-----\n");
		BackendsCompareName();

		System.out.println("\n----- Count MappingRules-----\n");
		CompareCountMappingRules();

		System.out.println("----- Content MappingRules segun Azure-----\n");
		CompareContentMappingRulesFromAzure();

		System.out.println("----- Content MappingRules segun 3Scale-----\n");
		CompareContentMappingRulesFrom3Scale();

		System.out.println("----- Check de privateBaseURL -----\n");
		CheckPrivateBaseURL();

		System.out.println("----- Check backend status TEST-----\n");
		CheckBackendStatusTest();

		System.out.println("----- Check backend status UAT-----\n");
		CheckBackendStatusUAT();

		// oc get backend alertas-api-aseautorizaciones-test -o jsonpath='{.status}'
		// oc get backend alertas-api-aseautorizaciones-uat -o jsonpath='{.status}'

		// type -> status
		System.out.println("- - - - - FIN - - - - -");

	}

	private static void CheckBackendStatusUAT() {

		Progress.runner();
		Gson gson = Main.getGsonCondition();
		Set<String> backends = backendFrom3Scale.keySet();

		backend: for (String backend : backends) {
			backend = backend.replace("test", "uat");
			String command = "oc get backend " + backend + " -o jsonpath='{.status.conditions}'";
			try {
				String ejecute = ejecuteResponse(command);
				Condition[] conditions = gson.fromJson(clean(ejecute), Condition[].class);
				for (Condition condition : conditions) {
					if (condition.type.equals(Type.Synced))
						if (condition.status.equals(Status.True))
							continue backend;
				}
				System.err.println("ERROR: condition " + backend);
			} catch (NegativeArraySizeException e) {
				System.err.println("FALLO: " + backend);
				System.err.println(command);
			}
		}
		Progress.stall();
	}

	private static void CheckBackendStatusTest() {
		Progress.runner();
		Gson gson = Main.getGsonCondition();
		Set<String> backends = backendFrom3Scale.keySet();

		backend: for (String backend : backends) {
			String ejecute = ejecuteResponse("oc get backend " + backend + " -o jsonpath='{.status.conditions}'");
			Condition[] conditions = gson.fromJson(clean(ejecute), Condition[].class);
			for (Condition condition : conditions) {
				if (condition.type.equals(Type.Synced))
					if (condition.status.equals(Status.True))
						continue backend;
			}
			System.err.println("ERROR: condition " + backend);
		}
		Progress.stall();
	}

	private void CheckPrivateBaseURL() {

		System.out.println("Check de uso de rutas publicas en 3scale");
		backendFrom3Scale.forEach((k, v) -> {
			boolean isService = v.getpublicBaseURL().contains(".apps.osnoprod01.aseconecta.com.ar");
			if (isService) {
				System.err.println("WARNING: " + k + " del cluster expone una ruta publica: ");
				System.out.println(v.getpublicBaseURL());
			}
		});
		System.out.println();
		System.out.println("Check de uso de rutas publicas en Azure");
		backendsFromAzure.forEach((k, v) -> {
			boolean isService = v.getSpec().getpublicBaseURL().contains(".apps.osnoprod01.aseconecta.com.ar");
			if (isService) {
				System.err.println("WARNING: " + k + " del Azure expone una ruta publica: ");
				System.out.println(v.getSpec().getpublicBaseURL());
			}
		});
	}

	private void CompareContentMappingRulesFrom3Scale() {

		backendFrom3Scale.forEach((k, v) -> {
			MappingRule[] MappingRulesFromAzure = backendsFromAzure.get(k).getSpec().getMappingRules();
			MappingRule[] MappingRulesFrom3Scale = v.getMappingRules();

			for (MappingRule from3Scale : MappingRulesFrom3Scale) {
				boolean exist = false;
				for (MappingRule fromAzure : MappingRulesFromAzure) {
					if (from3Scale.equals(fromAzure))
						exist = true;
				}
				if (!exist)
					System.err.println(k + " falta " + from3Scale + " en Azure");
			}
		});
	}

	private void CompareContentMappingRulesFromAzure() {

		backendsFromAzure.forEach((k, v) -> {
			MappingRule[] MappingRulesFrom3Scale = backendFrom3Scale.get(k).getMappingRules();
			MappingRule[] MappingRulesFromAzure = v.getSpec().getMappingRules();

			for (MappingRule fromAzure : MappingRulesFromAzure) {
				boolean exist = false;
				for (MappingRule from3Scale : MappingRulesFrom3Scale) {
					if (from3Scale.equals(fromAzure))
						exist = true;
				}
				if (!exist)
					System.err.println(k + " falta " + fromAzure + " en 3scale");
			}
		});
	}

	private void CompareCountMappingRules() {

		backendsFromAzure.forEach((k, v) -> {
			MappingRule[] MappingRulesFrom3Scale = backendFrom3Scale.get(k).getMappingRules();
			MappingRule[] MappingRulesFromAzure = v.getSpec().getMappingRules();

			if (MappingRulesFrom3Scale.length != MappingRulesFromAzure.length) {
				System.err.println(k + " no coinciden la cantidad de MappingRules");
				System.err.println(k + " MappingRules 3Scale " + MappingRulesFrom3Scale.length);
				System.err.println(k + " MappingRules Azure " + MappingRulesFromAzure.length);
			}
		});
	}

	private void BackendsCompareName() {

		System.out.println("----- Verifica cuales BACKENDS existen en AZURE-----");
		backendsFromAzure.forEach((k, v) -> {
			if (backendFrom3Scale.get(k) == null)
				System.err.println("no existe " + k + " en 3scale cluster");
		});
		System.out.println("\n----- Verifica cuales BACKENDS existen en CLUSTER-----");
		backendFrom3Scale.forEach((k, v) -> {
			if (backendsFromAzure.get(k) == null)
				System.err.println("no existe " + k + " en 3scale AZURE");
		});
	}

	private void BackendsFromAzure() throws IOException {

		File[] files = null;
		if (this.PWD_3SCALE == null || this.PWD_3SCALE.isBlank()) {
			String command = "git clone " + this.ssh_repo;
			ejecute(command);
			File file = new File(getPATH_3SCALE("./3scale"));
			files = file.listFiles();
		} else {
			files = new File(getPATH_3SCALE(this.PWD_3SCALE)).listFiles();
		}
		for (File file : files) {
			Backend readValue = mapperYAML.readValue(file, Backend.class);
			backendsFromAzure.put(readValue.getSpec().getSystemName(), readValue);
		}
	}

	private void BackendsFrom3Scale() throws JsonMappingException, JsonProcessingException {

		String backends = "oc get products " + NAMESPACE + " -o jsonpath='{.spec.backendUsages}'";
		backends = ejecuteResponse(backends);
		backends = clean(backends);
		Map<String, Map<String, String>> backendUsages = new Gson().fromJson(backends, Map.class);
		Set<String> backendUsageskeySet = backendUsages.keySet();

		for (String k : backendUsageskeySet) {

			String ejecute = ejecuteResponse("oc get backend " + k + " -o jsonpath='{.spec}'");
			Spec spec = new Gson().fromJson(clean(ejecute), Spec.class);
			backendFrom3Scale.put(k, spec);
		}

	}

	static {

	}

	private static String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	static Map<String, Spec> backendFrom3Scale = new HashMap<>();
	private Map<String, Backend> backendsFromAzure = new HashMap<String, Backend>();
	private final ObjectMapper mapperYAML = new ObjectMapper(new YAMLFactory());
	private final CharSequence replace;
}
