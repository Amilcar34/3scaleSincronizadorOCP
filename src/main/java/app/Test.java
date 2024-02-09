package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {
	private static final String leftAlignFormat = "| %-18s | %-20s | %-70s | %n";

	private final static String[] namespaces = { 
			"aseautorizaciones-dev",
			"aseautorizaciones-prod",
			"aseautorizaciones-test",
			"aseautorizaciones-uat",
			"aseautorizaciones2-dev",
			"aseautorizaciones2-test",
			"aseprestadores-dev",
			"aseprestadores-prod",
			"aseprestadores-test",
			"aseventas-dev",
			"aseventas-prod",
			"aseventas-test",
			"aseventas-uat",
			"auditoriaterreno-dev",
			"auditoriaterreno-prod",
			"auditoriaterreno-test",
			"auditoriaterreno-uat",
			"auditoriaterreno2-dev",
			"auditoriaterreno2-test",
			"cicd",
			"medifemobile-dev",
			"medifemobile-prod",
			"medifemobile-test",
			"medifemobile-uat",
			"servicioscomunes-dev",
			"servicioscomunes-prod",
			"servicioscomunes-test",
			"servicioscomunes-uat",
			"sume-dev",
			"sume-prod",
			"sume-test",
			"sume-uat",
			"sume1-dev",
			"sume1-test",
			"sume2-dev",
			"sume2-test",
			"sume3-dev",
			"sume3-test",
			};

	// busca en ADT los que se conectan a la DB pw9tst01-scan.medife.com
	public static void main(String[] args) throws IOException, InterruptedException {
		Set<String> lista = new HashSet<String>();
		System.out.println(LocalDateTime.now());
		Helper.loginOCP3();
		for (String namespace : namespaces) {
			Helper.selectNamespace(namespace);
			Set<String> artefactos = getArtefactos();
			artefactos.forEach(a -> {
				Map<String, String> configMap = new HashMap<String, String>();
				try {
					configMap = getConfigMapByAplication(a);
				} catch (NegativeArraySizeException e) {
					System.err.println("No se encontro config map para " + a);
				}
				for (Map.Entry<String, String> entry : configMap.entrySet()) {
					String k = entry.getKey();
					String v = entry.getValue();
					if (v.equals("pw9tst01-scan.medife.com") && namespace.contains("dev"))
						lista.add(namespace);	
//						System.err.format(leftAlignFormat, namespace, a, v.replace("apps.openshift.ase.local", "APPS.OPENSHIFT.ASE.LOCAL"));
//					if (v.contains("apps.openshift.ase.local"))
//						System.err.format(leftAlignFormat, a, k, v);
					
				}
			});
		}
		lista.forEach(System.out::println);
		System.out.println(LocalDateTime.now());
		System.out.println("FIN");
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
