package app.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import app.Main;
import app.Resources;
import app.model.ReadinessProbe;
import app.model.Resource;

public class ResourcesAsePrestadores {

	static String namespace = "aseprestadores-test";
	static boolean useArtefactosDinamicos = false;
	static Map<String, String> artefacttosTags = new HashMap<String, String>();

	public static void main(String[] data) throws InterruptedException, IOException {

		new Resources(namespace, useArtefactosDinamicos, artefacttosTags, artefactos).star();
	}

	static String[] artefactos = new String[] { "archivos-ui", "prestadores-api", "sesion-api",
			"legacy-prestadores-api" };


	static {
		artefacttosTags.put("archivos-ui", "20231204191607-refactor-ocp4");
		artefacttosTags.put("prestadores-api", "20231206150047-migracion-ocp4");
		artefacttosTags.put("sesion-api", "20230717130855-migracion-ocp4");
		artefacttosTags.put("legacy-prestadores-api", "20231206141831-migracion-ocp4");
	}
}
