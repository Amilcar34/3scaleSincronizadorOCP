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
		auditoriaterreno.put("autorizaciones-api", "");
//		auditoriaterreno.put("autorizaciones-cron-detele-archivos", "");
//		auditoriaterreno.put("autorizaciones-cron-mas-informacion", "");
//		auditoriaterreno.put("autorizaciones-cron-send-mails", "");
		auditoriaterreno.put("autorizaciones-ui", "");
		auditoriaterreno.put("bff-autorizaciones", "");
//		auditoriaterreno.put("broker-amq", "");
		auditoriaterreno.put("legacy-asociado-api", "");
		auditoriaterreno.put("lifia-integracion-api", "");
		auditoriaterreno.put("prestaciones-broker", "");
		auditoriaterreno.put("som-integracion-api", "");
		medifeMoblie.put("prestadores-api", "");
		medifeMoblie.put("traditum-integracion-api", "");
		sigo.put("archivos-api", "");
		sigo.put("asociados-api", "");
		sigo.put("comision-venta-api", "");
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