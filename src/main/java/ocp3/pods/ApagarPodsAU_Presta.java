package ocp3.pods;

import app.ocp3.ApagarPodsOCP3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.Helper;

public class ApagarPodsAU_Presta {

	private final static String[] namespaces = { "aseautorizaciones2-test", "aseautorizaciones2-dev",
			"aseautorizaciones-dev", "aseautorizaciones-test", "aseprestadores-test", "aseprestadores-dev" };

	public static void main(String[] args) {

		Helper.loginOCP3();
		for (String namespace : namespaces) {
			new ApagarPodsOCP3(namespace).star();
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
