package app.ocp3;

import java.util.Set;

import app.Helper;

public class PrenderPodsOCP3 {
	
	String namespace;

	public PrenderPodsOCP3(String namespace) {
		this.namespace = namespace;
	}

	public void star() {

		Set<String> artefactos = getArtefactos();
		System.err.println("Se procede con el PRENDIDO de " + artefactos.size() + " DCs en " + namespace);
		for (String artefacto : artefactos) {
			String cmd = "oc scale dc " + artefacto + " --replicas=1 -n " + this.namespace;
//			System.out.println(cmd);
			String response = Helper.ejecuteResponse(cmd);
//			System.out.println(response);
		}
		System.err.println("En " + namespace + " se PRENDIERON " + artefactos.size() + " DCs");
	}

	public Set<String> getArtefactos() {

		String command = "oc get dc -o jsonpath=\"{.items[*]['metadata.name']}\" -n " + this.namespace ;
		String respuesta = Helper.ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}

}