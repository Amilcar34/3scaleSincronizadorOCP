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
		map.put("auditorias-api", "20240214133659-migracion-ocp4");
		map.put("prestaciones-ui", "20231110132925-develop");
		map.put("proxy-prestaciones", "20240207130057-migracion-ocp4-V1.1.0");
		map.put("archivos-ui", "20221122195928-wpVisualizacion");
		map.put("casos-api", "20240219143651-migracion-ocp4-V0.0.1");
		map.put("especialidades-api", "20240219131044-migracion-ocp4");
		map.put("diagnosticos-api", "20240214124025-migracion-ocp4-V0.0.1");
		map.put("legacy-medicamentos-api", "20240215181309-migracion-ocp4");
		map.put("practicas-api", "20240215140644-migracion-ocp4-V0.0.1");
		map.put("profesionales-api", "20240214131536-migracion-ocp4-V0.0.1");
		map.put("gestion-api", "20240206131958-migracion-ocp4");
		map.put("informes-api", "20240214193224-migracion-ocp4");
		map.put("instituciones-api", "20240214183229-migracion-ocp4-V1.5.0");
		map.put("legacy-liquidaciones-api", "20240215174222-migracion-ocp4-V1.0.0");
		map.put("legacy-prestadores-api", "20210728152239-20210319183302-develop");
		map.put("prestadores-api", "20240215131440-migracion-ocp4-V1.3.0");
		map.put("reportes-api", "20240214125045-migracion-ocp4-V1.4.0");
		map.put("proxy-reverso-api", "1.0-SNAPSHOT");
		map.put("sesion-api", "20240214112426-migracion-ocp4-V1.6.0");
		map.put("archivos-api", "1.3.0-BETA-2");
		map.put("camdoctor-api", "20220615230012-develop");
	}
}