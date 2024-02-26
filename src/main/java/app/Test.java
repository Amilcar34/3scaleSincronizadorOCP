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

	};

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
		// valido para OCP 3, en OCP 4 usar 'deployments config' -> 'deployments'
		// /Deploiment
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
