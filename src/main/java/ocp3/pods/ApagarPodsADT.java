package ocp3.pods;


import app.Helper;

public class ApagarPodsADT {

	private final static String namespace = "auditoriaterreno-test";
	
	public static void main(String[] args) {
		
		Helper.loginOCP3();
	
		new app.ocp3.ApagarPodsOCP3(namespace).star();
	
	}
}
