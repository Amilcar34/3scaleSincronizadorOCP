package ocp4.scale;

import static app.Helper.loginOCP4;

import java.io.IOException;

import app.Sincro3ScaleWhitAzure;

public class AseAutorizaciones {

	public static void main(String[] data) throws InterruptedException, IOException {

		String NAMESPACE = "aseautorizaciones-test";
		String SCALE = "git@ssh.dev.azure.com:v3/ASEConecta/ASEAutorizaciones/3scale";
		String PWD_3SCALE = "/home/alberino_a/java/3scale";

		loginOCP4();
		new Sincro3ScaleWhitAzure(NAMESPACE, SCALE, PWD_3SCALE).star();
	}
	
}
