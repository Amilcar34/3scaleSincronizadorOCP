package app.ocp3;

import java.util.Set;

import app.Helper;

public class ApagarPodsOCP3 {
	
	String namespace;

	public ApagarPodsOCP3(String namespace) {
		this.namespace = namespace;
	}

	public void star() {

		Helper.selectNamespace(namespace);
		Set<String> artefactos = getArtefactos();
		for (String artefacto : artefactos) {
			String cmd = "oc scale dc " + artefacto + " --replicas=0";
			System.out.println(cmd);
			String response = Helper.ejecuteResponse(cmd);
			System.out.println(response);
		}
	}

	public static Set<String> getArtefactos() {

		String command = "oc get dc -o jsonpath=\"{.items[*]['metadata.name']}\"";
		String respuesta = Helper.ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}
}
