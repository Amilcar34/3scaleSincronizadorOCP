package ocp4.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Helper;
import app.Resources;

public class ResourcesAsePrestadores {

	static String namespace = "aseprestadores-test";
	static boolean useArtefactosDinamicos = false;
	static Map<String, String> artefacttosTags = new HashMap<String, String>();

	public static void main(String[] data) throws InterruptedException, IOException {

		Helper.loginOCP4();
		new Resources(namespace, useArtefactosDinamicos, artefacttosTags, artefacttosTags.keySet(), "").star();
	}

	static {
		artefacttosTags.put("archivos-ui", "20240103194618-refactor-ocp4");
		artefacttosTags.put("prestadores-api", "20240103173029-migracion-ocp4");
		artefacttosTags.put("sesion-api", "20230717130855-migracion-ocp4");
		artefacttosTags.put("legacy-prestadores-api", "20231206141831-migracion-ocp4");
	}
}
