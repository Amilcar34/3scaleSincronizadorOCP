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
//			"sigo-dev", 
//			"auditoriaterreno-dev", 
			
			"sigo-test", 
			"auditoriaterreno-test",
			
//			"aseautorizaciones-test",
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
					if (v.contains("pw9tst01-scan.medife.com"))
						System.err.format(leftAlignFormat, a, k, v.replace("apps.openshift.ase.local", "APPS.OPENSHIFT.ASE.LOCAL"));
//					if (v.contains("apps.openshift.ase.local"))
//						System.err.format(leftAlignFormat, a, k, v);
					
				}
			});
		}
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
}
