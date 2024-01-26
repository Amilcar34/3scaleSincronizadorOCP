package ocp3.pods;

import java.time.Duration;
import java.time.LocalDateTime;

import app.Helper;
import app.ocp3.PrenderPodsOCP3;

public class PrenderPodsAU_Presta {

	private final static String[] namespaces = { "aseautorizaciones2-test", "aseautorizaciones2-dev",
			"aseautorizaciones-dev", "aseautorizaciones-test", "aseprestadores-test", "aseprestadores-dev" };

	public static void main(String[] args) {

		LocalDateTime dateTime = LocalDateTime.now();

		Helper.loginOCP3();
		for (String namespace : namespaces)
			new Thread(new Runnable() {
				public void run() {
					new PrenderPodsOCP3(namespace).star();
				}
			}).start();

		System.out.println(Duration.between(dateTime, LocalDateTime.now()).toMillis());
	}

	static {
	}
}
