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
		System.err.println("Se procede con el APAGADO de " + artefactos.size() + " DCs en " + namespace);
		for (String artefacto : artefactos) {
			String cmd = "oc scale dc " + artefacto + " --replicas=0";
			String response = Helper.ejecuteResponse(cmd);
//			System.out.println(response);
		}
		System.err.println("En " + namespace + " se APAGARON " + artefactos.size() + " DCs");
	}

	public static Set<String> getArtefactos() {

		String command = "oc get dc -o jsonpath=\"{.items[*]['metadata.name']}\"";
		String respuesta = Helper.ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}
}
