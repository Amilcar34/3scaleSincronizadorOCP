package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SeracherOCP4 {

	private static final CharSequence search_key = "archivos-api";
	private static final CharSequence search_value = "archivos-api-servicioscomunes-dev";
	private volatile static Set<String> lista = new HashSet<String>();

	public static void main(String[] args) throws IOException, InterruptedException {
		LocalDateTime start = LocalDateTime.now();
		System.out.println(start);
		Helper.loginOCP4();
		Set<String> namespaces = Helper.getNamespaces();
		for (String namespace : namespaces) {
			String[] artefactos = Helper.getArtefactosByNamespace(namespace);
			for (int i = 0; i < artefactos.length; i++) {
				String artefacto = artefactos[i];
				if(i == artefactos.length)
					searched(lista, namespace, artefacto);
				else
					new Thread(() -> searched(lista, namespace, artefacto)).start();
			}
		}
		lista.forEach(System.out::println);
		Duration d = Duration.between(start, LocalDateTime.now());
		System.out.println(d);
		System.out.println(d.toString());
		System.out.println(d.toSeconds());
		System.out.println(d.toSecondsPart());
		System.out.println("FIN");
	}

	private static void searched(Set<String> lista, String namespace, String artefacto) {
		Map<String, String> configMap = new HashMap<String, String>();
		try {
			Helper helper = new Helper();
			configMap = helper.getConfigMapByAplicationByNamespace(artefacto, namespace);
		} catch (NegativeArraySizeException e) {
			System.err.println("No se encontro config map para " + artefacto + " de " + namespace);
		}
		for (Map.Entry<String, String> entry : configMap.entrySet()) {
			String k = entry.getKey();
			String v = entry.getValue();
			if (k.contains(search_key) || v.contains(search_value)) {
				lista.add(namespace + " | " + artefacto);
				System.err.format(leftAlignFormat, namespace, artefacto, v);
			}
		}
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
