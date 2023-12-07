package app.resources;

import java.io.IOException;

import app.Resources;

public class ResourcesAseVentas {

	static String namespace = "aseventas-test";
	static boolean useArtefactosDinamicos = true;

	public static void main(String[] data) throws InterruptedException, IOException {

		new Resources(namespace, useArtefactosDinamicos, null, null).star();
	}
}
