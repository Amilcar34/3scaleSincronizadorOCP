package ocp4.scale;

import static app.Helper.loginOCP4;

import java.io.IOException;

import app.Sincro3ScaleWhitAzure;

public class ADT {

	public static void main(String[] data) throws InterruptedException, IOException {

		String NAMESPACE = "auditoriaterreno-test";
		String SCALE = "git@ssh.dev.azure.com:v3/ASEConecta/Auditoria%20de%20Terreno/3scale";
		String PWD_3SCALE = null;

		loginOCP4();
		new Sincro3ScaleWhitAzure(NAMESPACE, SCALE, PWD_3SCALE).star();
	}
	
}
