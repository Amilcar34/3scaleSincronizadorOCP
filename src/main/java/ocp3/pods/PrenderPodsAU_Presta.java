package ocp3.pods;

import app.Helper;
import app.ocp3.PrenderPodsOCP3;

public class PrenderPodsAU_Presta {

	private final static String[] namespaces = { "aseautorizaciones2-test", "aseautorizaciones2-dev",
			"aseautorizaciones-dev", "aseautorizaciones-test", "aseprestadores-test", "aseprestadores-dev" };

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new PrenderPodsOCP3(namespace).star();
		}

	}

	static {
		new Thread(new Runnable() {
			@Override
			public void run() {
			}
		}).start();
	}
}
