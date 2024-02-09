package ocp3.pods;

import app.Helper;
import app.ocp3.PrenderPodsOCP3;

public class PrenderPodsAsePrestadores {

	private final static String namespace = "aseprestadores-test";
	
	public static void main(String[] args) {
		Helper.loginOCP3();
		new PrenderPodsOCP3(namespace).star();
	}
}
