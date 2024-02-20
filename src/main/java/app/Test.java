package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Test {
	private static final String leftAlignFormat = "| %-18s | %-20s | %-70s | %n";

	private final static String[] namespaces = { 
			"aseautorizaciones-dev",
			"aseautorizaciones-test",
			"aseautorizaciones-uat",
			"aseprestadores-dev",
			"aseprestadores-test",
			"aseprestadores-uat",
			"aseventas-dev",
			"aseventas-test",
			"aseventas-uat",
			"auditoriaterreno-dev",
			"auditoriaterreno-test",
			"auditoriaterreno-uat",
			"medifemobile-dev",
			"medifemobile-test",
			"medifemobile-uat",
			"rhpam",
			"servicioscomunes-dev",
			"servicioscomunes-test",
			"servicioscomunes-uat",
			"sigo-dev",
			"sigo-test",
			"sigo-uat",
			"3scale",
			"ase-rhpam",
			"aseautorizaciones-dev",
			"aseautorizaciones-test",
			"aseautorizaciones-uat",
			"aseprestadores-dev",
			"aseprestadores-test",
			"aseprestadores-uat",
			"aseventas-dev",
			"aseventas-test",
			"aseventas-uat",
			"auditoriaterreno-dev",
			"auditoriaterreno-test",
			"auditoriaterreno-uat",
			"medifemobile-dev",
			"medifemobile-test",
			"medifemobile-uat",
			"rhpam",
			"rhpam-medife-uat",
			"rhpam-sandbox",
			"servicioscomunes-dev",
			"servicioscomunes-test",
			"servicioscomunes-uat",
			"sigo-dev",
			"sigo-test",
			"sigo-uat",
			"sigoprestaciones-dev",
			"sigoprestaciones-test",
			"sigoprestaciones-uat",
			"sonarqube",
			"sso",
			"sume-dev",
			"sume-test",
			};

	// busca en ADT los que se conectan a la DB pw9tst01-scan.medife.com
	public static void main(String[] args) throws IOException, InterruptedException {
		Set<String> names = new LinkedHashSet<String>();
		for (String namespace : namespaces) 
			names.add(namespace);
		names.forEach(s -> System.out.println("\"" + s + "\","));

	}
	
	public static Map<String, String> getConfigMapByAplication(String aplication) {
		String idConfigmap = getIdConfigMap(aplication);
		// se descarta si no tiene configMap
		return idConfigmap.length() > 3 ? Helper.getConfigMapById(idConfigmap) : Map.of();
	}

	public static String getIdConfigMap(String aplication) {
		String idConfigmapCommand = "oc get dc " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].envFrom[0].configMapRef.name}\"";
		String idConfigmap = Helper.clean(Helper.ejecuteResponse(idConfigmapCommand));
		return idConfigmap;
	}

	public static Set<String> getArtefactos() {
		// valido para OCP 3, en OCP 4 usar 'deployments config' -> 'deployments' /Deploiment
		String command = "oc get dc -o jsonpath=\"{.items[*]['metadata.name']}\"";
		String respuesta = Helper.ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}

	public static void print(InputStream input) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader bf = new BufferedReader(new InputStreamReader(input));
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					System.out.println("IOException");
				}
			}
		}).start();
	}
}
