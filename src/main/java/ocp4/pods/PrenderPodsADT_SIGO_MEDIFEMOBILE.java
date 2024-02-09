package ocp4.pods;

import app.Helper;
import app.PrenderPodsOCP4;

public class PrenderPodsADT_SIGO_MEDIFEMOBILE {

	private final static String[] namespaces = {"auditoriaterreno-dev", "sigo-dev", 
			"sigo-test", 
			"medifemobile-test", "medifemobile-dev"};

//	private final static String[] namespaces = {"auditoriaterreno-test"};

	public static void main(String[] args) {
		
		Helper.loginOCP4();
		for (String namespace : namespaces) {
			System.out.println();
			new PrenderPodsOCP4(namespace).star();
		}
	}
}