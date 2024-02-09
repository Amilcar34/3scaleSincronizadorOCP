package ocp4.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Helper;
import app.Resources;

public class ResourcesADT {

	static String namespace = "auditoriaterreno-test";
	static boolean useArtefactosDinamicos = false;

	public static void main(String[] data) throws InterruptedException, IOException {
		Helper.loginOCP4();
		new Resources(namespace, useArtefactosDinamicos, map, map.keySet(), null).star();
	}

	private static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put("auditorias-api", "20220203193609-develop");
		map.put("prestaciones-ui", "20230830220034-develop");
		map.put("archivos-ui", "20221122195928-wpVisualizacion");
		map.put("casos-api", "20231110131839-develop");
		map.put("especialidades-api", "20240201115404-prueba");
		map.put("diagnosticos-api", "20240201112633-prueba");
		map.put("legacy-medicamentos-api", "20210126192205-develop");
		map.put("practicas-api", "20220201174718-develop");
		map.put("profesionales-api", "20240131210949-prueba");
		map.put("gestion-api", "20240130154007-prod-queueTime-OCP4");
		map.put("informes-api", "20211028145611-develop");
		map.put("instituciones-api", "20240201120748-prueba");
		map.put("legacy-liquidaciones-api", "20210416184749-develop");
		map.put("legacy-prestadores-api", "20210728152239-20210319183302-develop");
		map.put("prestadores-api", "20210728152312-fixMerge");
		map.put("reportes-api", "20210602161337-develop");
		map.put("sesion-api", "20211018190446-develop");
		map.put("archivos-api", "1.3.0-BETA-2");
		map.put("camdoctor-api", "20220615230012-develop");
		map.put("auditorias-api", "20240131185706-prueba");
		map.put("archivos-ui", "20221122195928-wpVisualizacion");
		map.put("especialidades-api", "20240201115404-prueba");
		map.put("diagnosticos-api", "20240201112633-prueba");
		map.put("legacy-medicamentos-api", "20210126192205-develop");
	}
}