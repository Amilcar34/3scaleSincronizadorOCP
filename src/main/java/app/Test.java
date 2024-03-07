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
import java.util.stream.Collectors;

import com.google.common.collect.Table;

public class Test {

	final static String namespaceOCP3 = "auditoriaterreno-prod";
	final static String namespaceOCP4 = "auditoriaterreno-test";

	public static void main(String[] args) throws IOException, InterruptedException {

		Helper.loginOCP3();
		Helper.selectNamespace(namespaceOCP3);
		Set<String> artefactsOCP3 = Helper.getArtefactosOCP3();
		artefactsOCP3 = cleansNumers(artefactsOCP3);
		
		for (String artefactOCP3 : artefactsOCP3) {
			System.out.println(artefactOCP3);
			Helper.loginOCP3();
			Helper.selectNamespace(namespaceOCP4);
			Map<String, String> configsMapsOCP3 = getConfigMapByAplication(artefactOCP3);
			
			Helper.loginOCP4();
			Helper.selectNamespace(namespaceOCP4);
			String artefactOCP4 = search(artefactOCP3);
			Map<String, String> configsMapsOCP4 = Helper.getConfigMapByAplication(artefactOCP3);
			
			if(configsMapsOCP3.equals(configsMapsOCP4))
				System.out.println(artefactOCP3 + " esta OK");
			else
				System.err.println(artefactOCP3 + " hay diferencias");
			
			System.out.println(" - - - - FIN - - - - ");
		}
		
	}

	private static String search(String artefactOCP3) {
		Helper.loginOCP4();
		Helper.selectNamespace(namespaceOCP4);
		Set<String> artefactsOCP4 = Helper.getArtefactosOCP4();
		for (String artefactOCP4 : artefactsOCP4) {
			if(artefactOCP3.equals(artefactOCP4))
				return artefactOCP4;
		}
		System.out.println(artefactOCP3);
		for (String artefactOCP4 : artefactsOCP4) {
			System.err.println(artefactOCP4);
		}
		return null;
	}

	private static Set<String> cleansNumers(Set<String> artefactsOCP3) {
		Set<String> artefactsOCP3Temp = new HashSet<String>();
		for (String artefactOCP3 : artefactsOCP3) {
			if (artefactOCP3.contains("-2")) 
				artefactsOCP3Temp.add(artefactOCP3.substring(0, artefactOCP3.indexOf("-2")));
			if (artefactOCP3.contains("-1")) 
				artefactsOCP3Temp.add(artefactOCP3.substring(0, artefactOCP3.indexOf("-1")));
		}
		return artefactsOCP3Temp;
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
