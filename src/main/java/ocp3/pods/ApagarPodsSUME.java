package ocp3.pods;

import app.ocp3.ApagarPodsOCP3;
import app.Helper;

public class ApagarPodsSUME {

	private final static String[] namespaces = {
			"sume2-dev", "sume3-dev",
			"auditoriaterreno2-dev", "medifemobile-dev",

			"sume-dev",
			"sume1-dev", 
			"auditoriaterreno-dev", 
			"sume2-test", 
			
			"sume1-test","sume3-test",	
			"sume-test"	, 
			"auditoriaterreno-test","auditoriaterreno2-test",
	};

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new ApagarPodsOCP3(namespace).star();
		}
		System.out.println("FIN");
	}
}
