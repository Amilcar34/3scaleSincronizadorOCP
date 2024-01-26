package ocp3.pods;

import app.ocp3.ApagarPodsOCP3;
import app.ocp3.PrenderPodsOCP3;
import app.Helper;

public class PrenderPodsSUME {

	private final static String[] namespaces = { 
			"sume1-test", 
			"sume2-test", 
			"sume3-test", 
			"sume-test" , 
			
			"sume1-dev", 
			"sume2-dev",
			"sume3-dev", 
			"sume-dev" 
			
			,"auditoriaterreno-test",
			"auditoriaterreno-dev",
			"auditoriaterreno2-dev",
			"auditoriaterreno2-test"
			};

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new PrenderPodsOCP3(namespace).star();
		}
System.out.println("FIN");
	}
}
