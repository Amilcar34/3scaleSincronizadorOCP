package ocp3.pods;

import app.Helper;
import app.ocp3.ApagarPodsOCP3;

public class ApagarPodsSUME {

	private final static String[] namespaces = 
		{ 
				"sume2-dev", "sume3-dev", "auditoriaterreno2-dev",
			"auditoriaterreno2-test",

			"sume-dev", "sume1-dev", "sume2-test", "auditoriaterreno-dev",

			"sume1-test", "sume3-test", "sume-test", "medifemobile-dev", "medifemobile-test",
			"auditoriaterreno-test", };

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new ApagarPodsOCP3(namespace).star();
		}
		System.out.println("FIN");
	}
}
