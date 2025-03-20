package ocp4.pods;

import app.Helper;

public class ApagarPodsAseVentas {

	private final static String[] namespaces = {
			"aseventas-dev", "aseventas-test" 
			};

	public static void main(String[] args) {
		
		Helper.loginOCP4();
		for (String namespace : namespaces) {
			System.out.println();
			new app.ApagarPodsOCP4(namespace).star();
		}
	}
}
