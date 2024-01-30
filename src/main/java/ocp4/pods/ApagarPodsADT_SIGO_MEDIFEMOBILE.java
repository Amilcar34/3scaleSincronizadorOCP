package ocp4.pods;

import app.Helper;

public class ApagarPodsADT_SIGO_MEDIFEMOBILE {

	private final static String[] namespaces = {"auditoriaterreno-dev", "sigo-dev", 
			"sigo-test", 
			"medifemobile-test", "medifemobile-dev"};
	
	public static void main(String[] args) {
		
		Helper.loginOCP4();
		for (String namespace : namespaces) {
			System.out.println();
			new app.ApagarPodsOCP4(namespace).star();
		}
	}
}
