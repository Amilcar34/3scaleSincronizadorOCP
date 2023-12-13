package app.scale;

import java.io.IOException;

import app.Sincro3ScaleWhitAzure;

import static app.Main.login;

public class ADT {

	public static void main(String[] data) throws InterruptedException, IOException {

		String NAMESPACE = "auditoriaterreno-test";
		String SCALE = "git@ssh.dev.azure.com:v3/ASEConecta/Auditoria%20de%20Terreno/3scale";
		String PWD_3SCALE = null;

		login();
		new Sincro3ScaleWhitAzure(NAMESPACE, SCALE, PWD_3SCALE).star();
	}
	
}
