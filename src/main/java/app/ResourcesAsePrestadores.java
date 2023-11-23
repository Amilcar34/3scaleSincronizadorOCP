package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import model.ReadinessProbe;
import model.Resource;

public class ResourcesAsePrestadores {

	static String namespace = "aseprestadores-test";
	public static void main(String[] data) throws InterruptedException, IOException {

		login();
		selectAsePrestadoresTest();
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
	}

	private static void interateProject() {

		iterateRecursos();
		System.out.println("Finalizo el chequeo por recursos");
		iterateReadinessProbe();
		System.out.println("Finalizo el chequeo por ReadinessProbe");
		iterateLivenessProbe();
		System.out.println("Finalizo el chequeo por LivenessProbe");
	
	}

	private static void iterateLivenessProbe() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"\n";
		String respuesta = ejecute(command);
		String replaceAll = respuesta.replaceAll("\"", "");
		String[] applicatios = replaceAll.split(" ");
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
				if (resource.httpGet == null && resource.httpGet.path == null) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("NO POSEE DATOS LivenessProbe");
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
				}
			}
		}
	}

	private static void iterateRecursos() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"\n";
		String respuesta = ejecute(command);
		String replaceAll = respuesta.replaceAll("\"", "");
		String[] applicatios = replaceAll.split(" ");
		for (String aplication : applicatios) {
			String limits = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.limits}\"";
			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.requests}\"";

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

	private static String ejecute(String command, String grep) throws InterruptedException, IOException {

		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		StringBuffer response = new StringBuffer(reader.read());
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.contains(grep))
				response.append(line + "\n");
		}
		proc.waitFor();
		return response.toString();
	}

	private static void selectAsePrestadoresTest() {
		String command = "oc project " + namespace;
		System.out.println(ejecute(command));
	}

	private static String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}
	
	static List<String> incorrectosRecursos = new ArrayList<String>();
	static List<String> incorrectosReadinessProbe = new ArrayList<String>();
	static List<String> incorrectosLivenessProbe = new ArrayList<String>();
}

