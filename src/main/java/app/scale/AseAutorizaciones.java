package app.scale;

import java.io.IOException;

import app.Sincro3ScaleWhitAzure;

import static app.Main.login;

public class AseAutorizaciones {

	public static void main(String[] data) throws InterruptedException, IOException {

		String NAMESPACE = "aseautorizaciones-test";
		String SCALE = "git@ssh.dev.azure.com:v3/ASEConecta/ASEAutorizaciones/3scale";
		String PWD_3SCALE = "/home/alberino_a/java/3scale";

		login();
		new Sincro3ScaleWhitAzure(NAMESPACE, SCALE, PWD_3SCALE).star();
	}
	
}
