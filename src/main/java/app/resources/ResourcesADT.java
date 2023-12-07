package app.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Resources;

public class ResourcesADT {

	static String namespace = "auditoriaterreno-test";
	static boolean useArtefactosDinamicos = false;

	public static void main(String[] data) throws InterruptedException, IOException {

		new Resources(namespace, useArtefactosDinamicos, artefactosTags, artefactos).star();
		
	}
	
	static String[] artefactos = new String[] { "auditorias-api", "prestaciones-ui", "proxy-prestaciones",
			"archivos-ui", "casos-api", "especialidades-api", "diagnosticos-api", "legacy-medicamentos-api",
			"practicas-api", "profesionales-api", "gestion-api", "informes-api", "instituciones-api",
			"legacy-liquidaciones-api", "legacy-prestadores-api", "prestaciones-ui", "prestadores-api", "reportes-api",
			"proxy-reverso-api", "sesion-api", "archivos-api", "camdoctor-api" };

	static Map<String, String> artefactosTags = new HashMap<String, String>();

	static {
		artefactosTags.put("auditorias-api", "20220203193609-develop");
		artefactosTags.put("prestaciones-ui", "20230830220034-develop");
		artefactosTags.put("proxy-prestaciones", "20210715215412-develop");
		artefactosTags.put("archivos-ui", "20221122195928-wpVisualizacion");
		artefactosTags.put("casos-api", "20230825163128-develop");
		artefactosTags.put("especialidades-api", "20221221161028-develop");
		artefactosTags.put("diagnosticos-api", "20210126192826-develop");
		artefactosTags.put("legacy-medicamentos-api", "20210126192205-develop");
		artefactosTags.put("practicas-api", "20220201174718-develop");
		artefactosTags.put("profesionales-api", "20221011170426-develop");
		artefactosTags.put("gestion-api", "20211222222522-develop");
		artefactosTags.put("informes-api", "20211028145611-develop");
		artefactosTags.put("instituciones-api", "20220328201328-develop");
		artefactosTags.put("legacy-liquidaciones-api", "20210416184749-develop");
		artefactosTags.put("legacy-prestadores-api", "20210728152239-20210319183302-develop");
		artefactosTags.put("prestaciones-ui", "20230830220034-develop");
		artefactosTags.put("prestadores-api", "20210728152312-fixMerge");
		artefactosTags.put("reportes-api", "20210602161337-develop");
		artefactosTags.put("proxy-reverso-api", "1.0-SNAPSHOT");
		artefactosTags.put("sesion-api", "20211018190446-develop");
		artefactosTags.put("archivos-api", "1.3.0-BETA-2");
		artefactosTags.put("camdoctor-api", "20220615230012-develop");
		artefactosTags.put("auditorias-api", "20220203193609-develop");
		artefactosTags.put("prestaciones-ui", "20230830220034-develop");
		artefactosTags.put("proxy-prestaciones", "20210715215412-develop");
		artefactosTags.put("archivos-ui", "20221122195928-wpVisualizacion");
		artefactosTags.put("casos-api", "20230825163128-develop");
		artefactosTags.put("especialidades-api", "20221221161028-develop");
		artefactosTags.put("diagnosticos-api", "20210126192826-develop");
		artefactosTags.put("legacy-medicamentos-api", "20210126192205-develop");

	}
}
