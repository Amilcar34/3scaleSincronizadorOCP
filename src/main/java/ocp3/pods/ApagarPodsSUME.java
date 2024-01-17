package ocp3.pods;

import app.ocp3.ApagarPodsOCP3;
import app.Helper;

public class ApagarPodsSUME {

	private final static String[] namespaces = { "sume-test", "sume1-test", "sume2-test", "sume3-test", 
			"sume-dev", "sume1-dev", "sume2-dev", "sume3-dev" };

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new ApagarPodsOCP3(namespace).star();
		}

	}
}
