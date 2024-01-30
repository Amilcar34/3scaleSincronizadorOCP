package app;

import java.util.Set;

public class ApagarPodsOCP4 {

	String namespace;

	public ApagarPodsOCP4(String namespace) {
		this.namespace = namespace;
	}

	public void star() {

		Set<String> artefactos = getArtefactos();
		System.err.println("Se procede con el APAGADO de " + artefactos.size() + " DCs en " + namespace);
		for (String artefacto : artefactos) {
			String cmd = "oc scale deployments " + artefacto + " --replicas=0 -n " + this.namespace;
//			System.out.println(cmd);
			String response = Helper.ejecuteResponse(cmd);
//			System.out.println(response);
		}
		System.err.println("En " + namespace + " se APAGARON " + artefactos.size() + " DCs");
	}

	public Set<String> getArtefactos() {

		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\" -n " + this.namespace;
		System.err.println(command);
		String respuesta = Helper.ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}
	
}