package scale;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.gson.Gson;

import model.Backend;
import model.MappingRule;
import model.Spec;

public class Sincro3ScaleWhitAzure {

	private static final String NAMESPACE = "aseautorizaciones-test";
	private static final String SCALE = "git@ssh.dev.azure.com:v3/ASEConecta/ASEAutorizaciones/3scale";
	private static final String PWD_3SCALE = "/home/alberino_a/java/3scale";

	public static void main(String[] data) throws InterruptedException, IOException {

		login();
		ejecute("oc project 3scale");

		System.out.println("----- Inicializa BackendsFrom3Scale -----");
		System.out.println();
		BackendsFrom3Scale();

		System.out.println("----- Inicializa BackendsFrom3Scale -----");
		System.out.println();
		BackendsFromAzure();

		System.out.println("----- Inicializa BackendsCompare-----");
		System.out.println();
		BackendsCompareName();

		System.out.println("----- Count MappingRules-----");
		System.out.println();
		CompareCountMappingRules();

		System.out.println("----- Content MappingRules segun Azure-----");
		System.out.println();
		CompareContentMappingRulesFromAzure();

		System.out.println("----- Content MappingRules segun 3Scale-----");
		System.out.println();
		CompareContentMappingRulesFrom3Scale();
		
		System.out.println("----- FIN -----");

	}

	private static void CompareContentMappingRulesFrom3Scale() {
		
		backendFrom3Scale.forEach((k, v) -> {
			MappingRule[] MappingRulesFromAzure = backendsFromAzure.get(k).getSpec().getMappingRules();
			MappingRule[] MappingRulesFrom3Scale = v.getMappingRules();

			for (MappingRule from3Scale : MappingRulesFrom3Scale) {
				boolean exist = false;
				for (MappingRule fromAzure : MappingRulesFromAzure) {
					if (from3Scale.equals(fromAzure))
						exist = true;
				}
				if(!exist)
					System.err.println(k + " falta " + from3Scale + " en Azure");
			}
		});
		System.out.println();
	}

	private static void CompareContentMappingRulesFromAzure() {

		backendsFromAzure.forEach((k, v) -> {
			MappingRule[] MappingRulesFrom3Scale = backendFrom3Scale.get(k).getMappingRules();
			MappingRule[] MappingRulesFromAzure = v.getSpec().getMappingRules();

			for (MappingRule fromAzure : MappingRulesFromAzure) {
				boolean exist = false;
				for (MappingRule from3Scale : MappingRulesFrom3Scale) {
					if (from3Scale.equals(fromAzure))
						exist = true;
				}
				if(!exist)
					System.err.println(k + " falta " + fromAzure + " en 3scale");
			}
		});
		System.out.println();
	}

	private static void CompareCountMappingRules() {

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

	private static void BackendsCompareName() {

		System.out.println("----- Verifica cuales BACKENDS existen en AZURE-----");
		backendsFromAzure.forEach((k, v) -> {
			if (backendFrom3Scale.get(k) == null)
				System.err.println("no existe " + k + " en 3scale cluster");
		});
		System.out.println();
		System.out.println("----- Verifica cuales BACKENDS existen en CLUSTER-----");
		backendFrom3Scale.forEach((k, v) -> {
			if (backendsFromAzure.get(k) == null)
				System.err.println("no existe " + k + " en 3scale AZURE");
		});
		System.out.println();
	}

	private static void BackendsFromAzure() throws IOException {

		File[] files = new File(PATH_3SCALE).listFiles();
		for (File file : files) {
			Backend readValue = mapperYAML.readValue(file, Backend.class);
			backendsFromAzure.put(readValue.getSpec().getSystemName(), readValue);
		}
	}

	private static void BackendsFrom3Scale() throws JsonMappingException, JsonProcessingException {

		String backends = "oc get products " + NAMESPACE + " -o jsonpath='{.spec.backendUsages}'";
		backends = ejecute(backends);
		backends = clean(backends);
		Map<String, Map<String, String>> backendUsages = new Gson().fromJson(backends, Map.class);
		Set<String> backendUsageskeySet = backendUsages.keySet();

		for (String k : backendUsageskeySet) {

			if (!k.equalsIgnoreCase(ignore)) {
				System.out.println(k);
				String ejecute = ejecute("oc get backend " + k + " -o jsonpath='{.spec}'");
				Spec spec = new Gson().fromJson(clean(ejecute), Spec.class);
				backendFrom3Scale.put(k, spec);
			}
		}

	}

	static {

	}

	private static String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	private static String ejecute(String command) {

		StringBuffer response = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);

			InputStream inputStream = proc.getInputStream();
			InputStreamReader in = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(in);

			int read = reader.read();
			response = new StringBuffer(read);
			String line = "";
			while ((line = reader.readLine()) != null) {
				response.append(line + "\n");
			}
			proc.waitFor();

		} catch (IOException | InterruptedException e) {
			System.err.println("Error al ejecutar");
			System.err.println(command);
			e.printStackTrace();
		}
		return response.toString();
	}

	private static String login() {
//		ejecute(Main.login);
		String token = "sha256~sCoUCOT9clvhdIJBNf-InbSoUd9lnuWbHytl2NMErLI";
		return ejecute("oc login --token=" + token + " --server=https://api.osnoprod01.aseconecta.com.ar:6443");
	}

	static final String ignore = "protesis-bff";
	static Map<String, Spec> backendFrom3Scale = new HashMap<>();
	private static Map<String, Backend> backendsFromAzure = new HashMap<String, Backend>();
	private static final ObjectMapper mapperYAML = new ObjectMapper(new YAMLFactory());
	private static final String PATH_3SCALE = PWD_3SCALE + "/test/" + NAMESPACE + "/backends";
	private static final CharSequence replace = "-" + NAMESPACE;
}
