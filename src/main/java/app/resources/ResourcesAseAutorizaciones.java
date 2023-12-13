package app.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Main;
import app.Main;
import app.Resources;

public class ResourcesAseAutorizaciones {

	static String namespace = "aseautorizaciones-test";
	static boolean useArtefactosDinamicos = false;
	static Map<String, String> artefacttosTags = new HashMap<String, String>();

	public static void main(String[] data) throws InterruptedException, IOException {

		Main.ejecute(Main.login);
		new Resources(namespace, useArtefactosDinamicos, artefacttosTags, artefactos,
				"04d6b08b4fd321cd6c9cfea35dac7774").star();

	}

	static String[] artefactos = new String[] { "autorizaciones-ui", "autorizaciones-bff", "openshift-activemq",
			"alertas-api", "aprobaciones-api", "ase-numera-api", "auditor-api", "auth-api", "autorizacion-api",
			"autorizaciones-brokermq-api", "beneficiario-api", "canal-api", "circuito-api", "documento-auditoria-api",
			"documento-checklist-api", "docu-check-legacy-api", "efecto-api", "empresa-api", "enfermedades-api",
			"filial-api", "frecuencia-api", "historial-api", "jwt-api", "jwt-validator-api", "legacy-manager-api",
			"medicamento-api", "medicos-api", "nomenclador-api", "notificaciones-api", "parametria-api", "pmo1-api",
			"reintegros-api", "reintegros-bff", "reintegros-ui", "sanatorio-api" };

	static {
		artefacttosTags.put("autorizaciones-ui", "20231212193945-migracion-ocp4-bff-V1.6");
		artefacttosTags.put("autorizaciones-bff", "20231213140906-migracion-ocp4");
		artefacttosTags.put("openshift-activemq", "7.11.0-8");
		artefacttosTags.put("alertas-api", "20231107150731-migracion-ocp4");
		artefacttosTags.put("aprobaciones-api", "20231212122700-migracion-ocp4-V1.5.5");
		artefacttosTags.put("ase-numera-api", "20231107132637-migracion-ocp4");
		artefacttosTags.put("auditor-api", "20231107193410-migracion-ocp4-1.0.0");
		artefacttosTags.put("auth-api", "20231107192042-migracion-ocp4-V1.5");
		artefacttosTags.put("autorizacion-api", "20231211170534-migracion-ocp4-V1.5");
		artefacttosTags.put("autorizaciones-brokermq-api", "20231212124258-migracion-ocp4-noupgrade");
		artefacttosTags.put("beneficiario-api", "20231114185437-migracion-ocp4-V1.5.5");
		artefacttosTags.put("canal-api", "20231114125129-migracion-ocp4");
		artefacttosTags.put("circuito-api", "20231108151227-migracion-ocp4");
		artefacttosTags.put("documento-auditoria-api", "20231211153953-migracion-ocp4-V1.5.4");
		artefacttosTags.put("documento-checklist-api", "20231213174608-migracion-ocp4-V1.5.1");
		artefacttosTags.put("docu-check-legacy-api", "20231109112600-migracion-opc4-V1.5.1");
		artefacttosTags.put("efecto-api", "20231116133426-migracion-ocp4");
		artefacttosTags.put("empresa-api", "20231109124143-migracion-ocp4");
		artefacttosTags.put("enfermedades-api", "20231109122932-migracion-ocp4");
		artefacttosTags.put("filial-api", "20231109121906-migracion-ocp4");
		artefacttosTags.put("frecuencia-api", "20231109120757-migracion-ocp4");
		artefacttosTags.put("historial-api", "20231211155942-migracion-ocp4-V1.3");
		artefacttosTags.put("jwt-api", "20231108152930-migracion-ocp4");
		artefacttosTags.put("jwt-validator-api", "20231108150734-migracion-ocp4");
		artefacttosTags.put("legacy-manager-api", "20231213175719-migracion-ocp4-V1.5.2");
		artefacttosTags.put("medicamento-api", "20231108123312-migracion-ocp4");
		artefacttosTags.put("medicos-api", "20231107175801-migracion-ocp4");
		artefacttosTags.put("nomenclador-api", "20231012172853-migracion-ocp4-V1.2.24");
		artefacttosTags.put("notificaciones-api", "20231211165946-migracion-ocp4-V1.5.1");
		artefacttosTags.put("parametria-api", "20230929172134-migracion-ocp4");
		artefacttosTags.put("pmo1-api", "20231113192717-migracion-ocp4");
		artefacttosTags.put("reintegros-api", "20230920151142-migracion-ocp4-V1.7.0");
		artefacttosTags.put("reintegros-bff", "20231213181514-migracion-ocp4-V1.7.0");
		artefacttosTags.put("reintegros-ui", "20231128135419-refactor-ocp4-V1.7.0");
		artefacttosTags.put("sanatorio-api", "20231106205839-migracion-ocp4");
	}
}
