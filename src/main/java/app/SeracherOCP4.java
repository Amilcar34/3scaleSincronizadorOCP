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

public class SeracherOCP4 {
	

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
			"servicioscomunes-dev",
			"servicioscomunes-test",
			"servicioscomunes-uat",
			"sigo-dev",
			"sigo-test",
			"sigo-uat",
			"sigoprestaciones-dev",
			"sigoprestaciones-test",
			"sigoprestaciones-uat",
			"sume-dev",
			"sume-test",
			"rhpam",
			"3scale",
			"ase-rhpam",
			"rhpam-medife-uat",
			"rhpam-sandbox",
			"sonarqube",
			"sso",
	};

	public static void main(String[] args) throws IOException, InterruptedException {
		Set<String> lista = new HashSet<String>();
		System.out.println(LocalDateTime.now());
		Helper.loginOCP4();
		for (String namespace : namespaces) {
			Helper.selectNamespace(namespace);
			Set<String> artefactos = Helper.getArtefactosOCP4();
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
					if (v.contains("kie")) {
						lista.add(namespace);
						System.err.format(leftAlignFormat, namespace, a,
								v.replace("apps.openshift.ase.local", "APPS.OPENSHIFT.ASE.LOCAL"));
					}
//					if (v.contains("apps.openshift.ase.local"))
//						System.err.format(leftAlignFormat, a, k, v);
				}
			});
		}
		lista.forEach(System.out::println);
		System.out.println(LocalDateTime.now());
		System.out.println("FIN");
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

	private static final String leftAlignFormat = "| %-18s | %-20s | %-70s | %n";
}
