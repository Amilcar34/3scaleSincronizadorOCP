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

	private static final CharSequence search_key = "kie-test-kieserver-rhpam-73";
	private static final CharSequence search_value = "kie-test-kieserver-rhpam-73";

	public static void main(String[] args) throws IOException, InterruptedException {
		Set<String> lista = new HashSet<String>();
		System.out.println(LocalDateTime.now());
		Helper.loginOCP4();
		Set<String> namespaces = Helper.getNamespaces();
		for (String namespace : namespaces) {
			Set<String> artefactos = Helper.getArtefactosByNamespace(namespace);
			artefactos.forEach(a -> {
				Map<String, String> configMap = new HashMap<String, String>();
				try {
					Helper helper = new Helper();
					configMap = helper.getConfigMapByAplicationByNamespace(a, namespace);
				} catch (NegativeArraySizeException e) {
					System.err.println("No se encontro config map para " + a + " de " + namespace);
				}
				for (Map.Entry<String, String> entry : configMap.entrySet()) {
					String k = entry.getKey();
					String v = entry.getValue();
					if (k.contains(search_key) || v.contains(search_value)) {
						lista.add(namespace + "  |  " + a);
						System.err.format(leftAlignFormat, namespace, a, v);
					}
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
