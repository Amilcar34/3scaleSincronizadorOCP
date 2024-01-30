package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import app.ocp3.PrenderPodsOCP3;

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
			"sume-uat",
			};

	// busca en ADT los que se conectan a la DB pw9tst01-scan.medife.com
	public static void main(String[] args) throws IOException, InterruptedException {

		Helper.loginOCP4();
		for (String namespace : namespaces) {
			Helper.selectNamespace(namespace);
			Set<String> artefactos = Helper.getArtefactos();
			artefactos.forEach(a -> {
				Map<String, String> configMap = new HashMap<String, String>();
				try {
					configMap = Helper.getConfigMapByAplication(a);
				} catch (NegativeArraySizeException e) {
					System.err.println("No se encontro config map para " + a);
				}
				for (Map.Entry<String, String> entry : configMap.entrySet()) {
					String k = entry.getKey();
					String v = entry.getValue();
					if (v.equals("pw9tst01-scan.medife.com"))
						System.err.format(leftAlignFormat, namespace, a, v.replace("apps.openshift.ase.local", "APPS.OPENSHIFT.ASE.LOCAL"));
//					if (v.contains("apps.openshift.ase.local"))
//						System.err.format(leftAlignFormat, a, k, v);
					
				}
			});
		}
		System.out.println("FIN");
	}
	
	public static Set<String> getArtefactos() {
		// valido para OCP 4, en OCP 3 usar 'deployments' -> 'dc' /DeploimentConfig
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
