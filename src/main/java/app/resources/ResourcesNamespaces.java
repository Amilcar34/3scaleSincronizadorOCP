package app.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.Main;

public class ResourcesNamespaces {

	public static void main(String[] data) throws InterruptedException, IOException {

		login();
		interateProject();
	}

	private static void interateProject() {
		for (String namespace : namespaces) {
			selectProject(namespace);
			deploymentsByProject(namespace);
		}
	}

	private static void deploymentsByProject(String namespace) {
		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"\n";
		try {
			String respuesta = ejecute(command);
			String replaceAll = respuesta.replaceAll("\"", "");
			String[] applicatios = replaceAll.split(" ");
			for (String aplication : applicatios) {
				String limits = "oc get deployments " + aplication
						+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.limits}\"";
				String requests = "oc get deployments " + aplication
						+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.requests}\"";

				limits = ejecute(limits);
				requests = ejecute(requests);
				System.out.println(namespace + " - " + aplication);
				if (requests.equals(limits))
					System.err.println("NO POSEE");
				else {
					System.out.print(requests);
					System.out.print(limits);
				}
			}
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void selectProject(String namespace) {
		String command = "oc project " + namespace;
		try {
			String respuesta = ejecute(command);
//			System.out.println(respuesta);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	private static void login() {

		try {
			String respuesta = ejecute(Main.login);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	private static String ejecute(String command) throws InterruptedException, IOException {

		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		StringBuffer response = new StringBuffer(reader.read());
		String line = "";
		while ((line = reader.readLine()) != null) {
			response.append(line + "\n");
		}
		proc.waitFor();
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

	static String[] namespaces = new String[] { "aseautorizaciones-dev", "aseautorizaciones-test", "aseprestadores-dev",
			"aseprestadores-test", "aseventas-dev", "aseventas-test", "aseventas-uat", "auditoriaterreno-dev",
			"auditoriaterreno-test", "medifemobile-dev", "medifemobile-test", "servicioscomunes-dev",
			"servicioscomunes-test", "sigo-dev", "sigo-test" };

}
