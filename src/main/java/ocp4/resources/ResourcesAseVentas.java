package ocp4.resources;

import java.io.IOException;

import app.Helper;
import app.Resources;

public class ResourcesAseVentas {

	static String namespace = "aseventas-test";
	static boolean useArtefactosDinamicos = true;

	public static void main(String[] data) throws InterruptedException, IOException {

		Helper.loginOCP4();
		new Resources(namespace, useArtefactosDinamicos, null, null, null).star();
	}
}
