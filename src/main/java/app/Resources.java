package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import app.model.ReadinessProbe;
import app.model.Resource;
import static app.resources.Application.ejecute;

public class Resources {

	private String namespace = "aseautorizaciones-test";
	private boolean useArtefactosDinamicos = false;
	private Map<String, String> artefacttosTags;
	private String[] artefactos;

	public Resources(String namespace, boolean useArtefactosDinamicos, Map<String, String> artefacttosTags,
			String[] artefactos) {

		this.namespace = namespace;
		this.useArtefactosDinamicos = useArtefactosDinamicos;
		this.artefacttosTags = artefacttosTags;
		this.artefactos = artefactos;
	}
	
	public void star() throws InterruptedException, IOException {

		interateProject();

		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
		System.out.println("----- Incorrectos Por Recursos: " + incorrectosRecursos.size());
		incorrectosRecursos.forEach(System.out::println);

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

	private void interateProject() {

		selectNamespaceTest();
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

	private void iterateLivenessProbe() {

		System.out.println("--------------------LIVENESS PROBE -------");
		for (String aplication : getArtefactos()) {

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

	private void iterateReadinessProbe() {

		System.out.println("--------------------READINESS PROBE -------");
		for (String aplication : getArtefactos()) {

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

	private void iterateRecursos() {

		for (String aplication : getArtefactos()) {
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

	private void setTags(String aplication) {

		String image = "oc get deployments " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].image}\"";
		image = ejecute(image);
		int length = image.length();
		image = image.substring(image.lastIndexOf(":"), length);
		length = image.length();
		--length;
		image = image.substring(1, --length);
		tagsCluster.put(aplication, image);
	}

	private int extracted(Resource resource) {
		int intResource = 0;
		try {
			intResource = Integer.valueOf(resource.getCpu().replaceAll("[^-?0-9]+", ""));
		} catch (NumberFormatException e) {
			System.err.print("Error: " + resource.getCpu());
		}
		return intResource;
	}

	private void selectNamespaceTest() {
		String command = "oc project " + namespace;
		System.out.println(ejecute(command));
	}

	private String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	String[] getArtefactos() {

		if (useArtefactosDinamicos)
			if (artefactosDinamicos == null) {
				String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"";
				String respuesta = ejecute(command);
				String replaceAll = respuesta.replaceAll("\"", "");
				artefactosDinamicos = replaceAll.split(" ");
				return artefactosDinamicos;
			} else
				return artefactosDinamicos;
		return artefactos;
	}

	String[] artefactosDinamicos;
	
	Map<String, String> tagsCluster = new HashMap<String, String>();
	List<String> incorrectosRecursos = new ArrayList<String>();
	List<String> incorrectosReadinessProbe = new ArrayList<String>();
	List<String> incorrectosLivenessProbe = new ArrayList<String>();

}
