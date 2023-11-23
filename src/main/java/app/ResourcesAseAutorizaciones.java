package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import model.ReadinessProbe;
import model.Resource;

public class ResourcesAseAutorizaciones {

	static String namespace = "aseautorizaciones-test";

	public static void main(String[] data) throws InterruptedException, IOException {

		interateProject();

//		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
//		System.out.println("----- Incorrectos Por Recursos: " + incorrectosRecursos.size());
//		incorrectosRecursos.forEach(System.out::println);

		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
		System.out.println("----- Incorrectos Por ReadinessProbe: " + incorrectosReadinessProbe.size());
		incorrectosReadinessProbe.forEach(System.out::println);

		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
		System.out.println("----- Incorrectos Por LivenessProbe: " + incorrectosLivenessProbe.size());
		incorrectosLivenessProbe.forEach(System.out::println);

//		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
//		System.out.println("----- Artefactos - tags OCP4: ");
//		tags.forEach((k, v) -> System.out.println(k + " - " + v));
//		System.out.println("----- Artefactos - tags Docuemnto: ");
//		artefacttosTags.forEach((k, v) -> System.out.println(k + " - " + v));

//		System.out.println("----- Artefactos - tags DIFERENCIAS: ");
//		artefacttosTags.forEach((k, v) -> {
//			if (!tags.get(k).equals(v)) {
//				System.out.println(k);
//			}
//		});

	}

	private static void interateProject() {
		
		selectAseAutorizacionesTest();
		iterateRecursos();
		System.out.println("Finalizo el chequeo por recursos");
		System.out.println();
		iterateReadinessProbe();
		System.out.println("Finalizo el chequeo por ReadinessProbe");
		System.out.println();
		iterateLivenessProbe();
		System.out.println("Finalizo el chequeo por LivenessProbe");
		System.out.println();
	}

	private static void iterateLivenessProbe() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"\n";
		String respuesta = ejecute(command);
		String replaceAll = respuesta.replaceAll("\"", "");
		String[] applicatios = replaceAll.split(" ");
		System.out.println("--------------------LIVENESS PROBE -------");
		for (String aplication : applicatios) {

			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].livenessProbe}\"";

			requests = clean(ejecute(requests));

			if (requests.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("NO POSEE LivenessProbe");
				incorrectosLivenessProbe.add(aplication);
			} else {
				ReadinessProbe resource = new Gson().fromJson(requests, ReadinessProbe.class);
				if (resource.httpGet == null || resource.httpGet.path == null) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("NO POSEE DATOS LivenessProbe");
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.initialDelaySeconds != 120) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("initialDelaySeconds distinto de 120 : " + resource.initialDelaySeconds);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.periodSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("periodSeconds distinto de 30 : " + resource.periodSeconds);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.successThreshold != 1) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("successThreshold distinto de 1 : " + resource.successThreshold);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.timeoutSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("timeoutSeconds distinto de 30 : " + resource.timeoutSeconds);
					incorrectosLivenessProbe.add(aplication);
				}
			}
		}
	}

	private static void iterateReadinessProbe() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"\n";
		String respuesta = ejecute(command);
		String replaceAll = respuesta.replaceAll("\"", "");
		String[] applicatios = replaceAll.split(" ");
		System.out.println("--------------------READINESS PROBE -------");
		for (String aplication : applicatios) {

			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].readinessProbe}\"";

			requests = clean(ejecute(requests));
			if (requests.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("NO POSEE readinessProbe");
				incorrectosReadinessProbe.add(aplication);
			} else {
				ReadinessProbe resource = new Gson().fromJson(requests, ReadinessProbe.class);
				if (resource.httpGet == null || resource.httpGet.path == null) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("NO POSEE DATOS readinessProbe");
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.initialDelaySeconds != 120) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("initialDelaySeconds distinto de 120 : " + resource.initialDelaySeconds);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.periodSeconds != 60) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("periodSeconds distinto de 60 : " + resource.periodSeconds);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.successThreshold != 1) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("successThreshold distinto de 1 : " + resource.successThreshold);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.timeoutSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("timeoutSeconds distinto de 30 : " + resource.timeoutSeconds);
					incorrectosReadinessProbe.add(aplication);
				}
			}
		}
	}

	private static void iterateRecursos() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}";
		String respuesta = ejecute(command);
		String replaceAll = respuesta.replaceAll("\"", "");
//		String[] applicatios = replaceAll.split(" ");
		for (String aplication : artefactos) {
			String limits = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.limits}\"";
			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.requests}\"";

			setTags(aplication);
			limits = clean(ejecute(limits));
			requests = clean(ejecute(requests));

			if (requests.isBlank() || limits.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("NO POSEE limite de recursos asignados");
				incorrectosRecursos.add(aplication);
			} else {
				Resource resource = new Gson().fromJson(requests, Resource.class);
				if (resource.getCpu().endsWith("m") && resource.getCpu().startsWith("10")) {

				} else if (resource.getCpu().endsWith("m")) {
					int intResource = extracted(resource);
					if (intResource > 10) {
						System.err.print(namespace + " ");
						System.out.println(aplication);
						System.out.println(requests);
						incorrectosRecursos.add(aplication);
					}
				}

			}
		}
	}

	private static void setTags(String aplication) {
		String image = "oc get deployments " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].image}\"";
		image = ejecute(image);
		int length = image.length();
		image = image.substring(image.lastIndexOf(":"), length);
		length = image.length();
		--length;
		image = image.substring(1, --length);
		tags.put(aplication, image);
	}

	private static int extracted(Resource resource) {
		int intResource = 0;
		try {
			intResource = Integer.valueOf(resource.getCpu().replaceAll("[^-?0-9]+", ""));
		} catch (NumberFormatException e) {
			System.err.print("Error: " + resource.getCpu());
		}
		return intResource;
	}

	private static void login() {
		ejecute(Main.login);
	}

	private static String ejecute(String command) {

		StringBuffer response = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			response = new StringBuffer(reader.read());
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

	private static void selectAseAutorizacionesTest() {
		login();
		String command = "oc project " + namespace;
		System.out.println(ejecute(command));
	}

	private static String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	static String[] artefactos = new String[] { "autorizaciones-ui", "autorizaciones-bff", "openshift-activemq",
			"alertas-api", "aprobaciones-api", "ase-numera-api", "auditor-api", "auth-api", "autorizacion-api",
			"autorizaciones-brokermq-api", "beneficiario-api", "canal-api", "circuito-api", "documento-auditoria-api",
			"documento-checklist-api", "docu-check-legacy-api", "efecto-api", "empresa-api", "enfermedades-api",
			"filial-api", "frecuencia-api", "historial-api", "jwt-api", "jwt-validator-api", "legacy-manager-api",
			"medicamento-api", "medicos-api", "nomenclador-api", "notificaciones-api", "parametria-api", "pmo1-api",
			"reintegros-api", "reintegros-bff", "reintegros-ui", "sanatorio-api" };

	static Map<String, String> tags = new HashMap<String, String>();
	static List<String> incorrectosRecursos = new ArrayList<String>();
	static List<String> incorrectosReadinessProbe = new ArrayList<String>();
	static List<String> incorrectosLivenessProbe = new ArrayList<String>();
	static Map<String, String> artefacttosTags = new HashMap<String, String>();

	static {
		artefacttosTags.put("autorizaciones-ui", "20231006172830-migracion-ocp4-bff-V1.6");
		artefacttosTags.put("autorizaciones-bff", "20231109113554-main");
		artefacttosTags.put("openshift-activemq", "7.11.0-8");
		artefacttosTags.put("alertas-api", "20231107150731-migracion-ocp4");
		artefacttosTags.put("aprobaciones-api", "20231114163801-migracion-ocp4-V1.5.5");
		artefacttosTags.put("ase-numera-api", "20231107132637-migracion-ocp4");
		artefacttosTags.put("auditor-api", "20231107193410-migracion-ocp4-1.0.0");
		artefacttosTags.put("auth-api", "20231107192042-migracion-ocp4-V1.5");
		artefacttosTags.put("autorizacion-api", "20231108132503-migracion-ocp4-V1.5");
		artefacttosTags.put("autorizaciones-brokermq-api", "20231114135818-migracion-ocp4-noupgrade");
		artefacttosTags.put("beneficiario-api", "20231114185437-migracion-ocp4-V1.5.5");
		artefacttosTags.put("canal-api", "20231114125129-migracion-ocp4");
		artefacttosTags.put("circuito-api", "20231108151227-migracion-ocp4");
		artefacttosTags.put("documento-auditoria-api", "20231114150839-migracion-ocp4-V1.5.4");
		artefacttosTags.put("documento-checklist-api", "20231109111155-migracion-ocp4-V1.5.1");
		artefacttosTags.put("docu-check-legacy-api", "20231109112600-migracion-opc4-V1.5.1");
		artefacttosTags.put("efecto-api", "20231116133426-migracion-ocp4");
		artefacttosTags.put("empresa-api", "20231109124143-migracion-ocp4");
		artefacttosTags.put("enfermedades-api", "20231109122932-migracion-ocp4");
		artefacttosTags.put("filial-api", "20231109121906-migracion-ocp4");
		artefacttosTags.put("frecuencia-api", "20231109120757-migracion-ocp4");
		artefacttosTags.put("historial-api", "20231109115249-migracion-ocp4");
		artefacttosTags.put("jwt-api", "20231108152930-migracion-ocp4");
		artefacttosTags.put("jwt-validator-api", "20231108150734-migracion-ocp4");
		artefacttosTags.put("legacy-manager-api", "20231108145232-migracion-ocp4-V1.5.2");
		artefacttosTags.put("medicamento-api", "20231108123312-migracion-ocp4");
		artefacttosTags.put("medicos-api", "20231107175801-migracion-ocp4");
		artefacttosTags.put("nomenclador-api", "20231012172853-migracion-ocp4-V1.2.24");
		artefacttosTags.put("notificaciones-api", "20231107171042-migracion-ocp4-V1.5.1");
		artefacttosTags.put("parametria-api", "20230929172134-migracion-ocp4");
		artefacttosTags.put("pmo1-api", "20231113192717-migracion-ocp4");
		artefacttosTags.put("reintegros-api", "20230920151142-migracion-ocp4-V1.7.0");
		artefacttosTags.put("reintegros-bff", "20231012200755-725e49efebf477655e9fb5a9a0c9d570cd3bd48b");
		artefacttosTags.put("reintegros-ui", "20231020135300-refactor-ocp4-V1.7.0");
		artefacttosTags.put("sanatorio-api", "20231106205839-migracion-ocp4");

	}
}
