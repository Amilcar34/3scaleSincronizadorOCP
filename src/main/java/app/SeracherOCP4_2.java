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

public class SeracherOCP4_2 {
	
	private static final CharSequence search_key = "";
	private static final CharSequence search_value = "10.3.4.220";

	private static String tokenOCP4 = "0244_hIvfyiWah6xn6HG4rPfgHgmEypoyUYx6ApYVAQ";

	private final static String[] namespaces = { 
			"aseautorizaciones-test",
			"aseventas-dev",
			"aseventas-test",
			"auditoriaterreno-dev",
			"auditoriaterreno-prod",
			"auditoriaterreno-test",
			"auditoriaterreno-uat",
			"auditoriaterreno2-dev",
			"medifemobile-prod",
			"medifemobile-test",
			"servicioscomunes-prod",
			"servicioscomunes-test",
			"servidores-prod",
			"sume-dev",
			"sume-prod",
			"sume-test",
			"sume-uat",
	};

	public static void main(String[] args) throws IOException, InterruptedException {
		Set<String> lista = new HashSet<String>();
		System.out.println(LocalDateTime.now());
		loginOCP4();
		for (String namespace : namespaces) {
			Helper.selectNamespace(namespace);
			Set<String> artefactos = Helper.getArtefactosOCP4();
//			System.out.println("artefactos " + artefactos.size());
			artefactos.forEach(a -> {
//				System.out.println(a);
				Map<String, String> configMap = new HashMap<String, String>();
				try {
					configMap = Helper.getConfigMapByAplication(a);
				} catch (NegativeArraySizeException e) {
					System.err.println("No se encontro config map para " + a);
				}
				for (Map.Entry<String, String> entry : configMap.entrySet()) {
					String k = entry.getKey();
					String v = entry.getValue();
					if (k.contains(search_key) && v.contains(search_value)) {
						lista.add(namespace);
						System.err.format(leftAlignFormat, namespace, a, v);
					}
				}
			});
		}
		lista.forEach(System.out::println);
		System.out.println(LocalDateTime.now());
		System.out.println("FIN");
	}
	
	public static String loginOCP4() {
		System.out.println(loginOCP4);
		return Helper.ejecuteResponse(loginOCP4);
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

	static String loginOCP4 = "oc login --token=sha256~" + tokenOCP4
			+ " --server=https://api.osprod01.aseconecta.com.ar:6443";
	private static final String leftAlignFormat = "| %-18s | %-20s | %-70s | %n";
}
