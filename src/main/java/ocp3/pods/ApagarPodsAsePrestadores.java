package ocp3.pods;

import app.ocp3.ApagarPodsOCP3;
import app.Helper;

public class ApagarPodsAsePrestadores {

	private final static String namespace = "aseprestadores-test";
	
	public static void main(String[] args) {
		Helper.loginOCP3();
		new ApagarPodsOCP3(namespace).star();
	}
}
