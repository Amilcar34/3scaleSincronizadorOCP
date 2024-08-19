package ocp4.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Helper;
import app.Resources;

public class ResourcesPaquete2 {

	public static void main(String[] data) throws InterruptedException, IOException {
		Helper.loginOCP4();
		new Resources("auditoriaterreno-test", false, auditoriaterreno, auditoriaterreno.keySet(), null).star();
		new Resources("medifemobile-test", false, medifeMoblie, medifeMoblie.keySet(), null).star();
		new Resources("sigo-test", false, sigo, sigo.keySet(), null).star();
	}

	private static Map<String, String> auditoriaterreno = new HashMap<String, String>();
	private static Map<String, String> medifeMoblie = new HashMap<String, String>();
	private static Map<String, String> sigo = new HashMap<String, String>();

	static {
		auditoriaterreno.put("autorizaciones-api", "20240305184021-migracion-ocp4-V0.0.1");
		auditoriaterreno.put("autorizaciones-ui", "20230317205411-develop-febrero");
		auditoriaterreno.put("bff-autorizaciones", "20230306194423-develop");
		auditoriaterreno.put("legacy-asociado-api", "20230505145849-develop-vistas");
		auditoriaterreno.put("lifia-integracion-api", "20230919142747-develop");
		auditoriaterreno.put("prestaciones-broker", "20230322143819-develop");
		auditoriaterreno.put("som-integracion-api", "20230825163930-develop");
		medifeMoblie.put("prestadores-api", "20231019220135-develop");
		medifeMoblie.put("traditum-integracion-api", "20220915163931-develop");
		medifeMoblie.put("proxy-api", "20230914000150-v_1.0");
		medifeMoblie.put("medife-mobile-api", "20240105182416-develop");
		sigo.put("archivos-api", "20220317203952-develop-ers-9");
		sigo.put("asociados-api", "20220308181843-develop-ers-9");
		sigo.put("comision-venta-api", "0.0.1-SNAPSHOT");
		sigo.put("empresas-api", "");
		sigo.put("engage-integracion-api", "");
		sigo.put("facturacion-api", "");
		sigo.put("facturacion-cc-api", "");
		sigo.put("grupo-familiar-api", "");
		sigo.put("integration-api", "");
		sigo.put("medife-amq-api", "");
		sigo.put("obra-social-api", "");
		sigo.put("openshift-activemq", "");
		sigo.put("padron-api", "");
		sigo.put("presupuesto-api", "");
		sigo.put("proxy-api", "");
		sigo.put("proxy-api-sigo", "");
		sigo.put("proxy-salesforce-api", "");
		sigo.put("responsable-pago-api", "");
		sigo.put("rhpam-integracion-api", "");
		sigo.put("sigo-mapper-api", "");
		sigo.put("sigo-ui", "");
		sigo.put("utilidades-api", "");
		sigo.put("valorizacion-api", "");
		sigo.put("vendedor-api", "");
		sigo.put("wellness-api", "");
//		sigo.put("wellness-cron-envio-contract", "");
//		sigo.put("wellness-cron-envio-member", "");
	}
}