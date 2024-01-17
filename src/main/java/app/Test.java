package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class Test {

	// busca en ADT los que se conectan a la DB pw9tst01-scan.medife.com
	public static void main(String[] args) throws IOException, InterruptedException {

		Helper.loginOCP4();
		Helper.selectNamespace("auditoriaterreno-test");
		Set<String> Artefactos = Helper.getArtefactos();
		Artefactos.forEach(a -> {
			Map<String, String> configMap = Helper.getConfigMapByAplication(a);
			configMap.forEach((k, v) -> {
				
				if(configMap.size() > 10 && v.equals("10.3.4.220")) {
					System.out.println(a);
				}
			});
		});
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
