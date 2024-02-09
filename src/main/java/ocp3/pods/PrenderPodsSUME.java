package ocp3.pods;

import app.Helper;
import app.ocp3.PrenderPodsOCP3;

public class PrenderPodsSUME {

	private final static String[] namespaces = { 
			"sume2-test", 
			
			"auditoriaterreno2-dev",
//			"sume2-dev",
//			"sume1-dev", 
//			"sume-dev",
//			"sume3-dev", 
//			"auditoriaterreno-dev",

//			"sume-test" , "sume1-test", "sume3-test", 
//			"auditoriaterreno-test", "auditoriaterreno2-test"
			};

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new PrenderPodsOCP3(namespace).star();
		}
		System.out.println("FIN");
	}
}
