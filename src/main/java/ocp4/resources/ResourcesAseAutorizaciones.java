package ocp4.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import app.Helper;
import app.Resources;

public class ResourcesAseAutorizaciones {

	static String namespace = "aseautorizaciones-test";
	static boolean useArtefactosDinamicos = false;
	static Map<String, String> artefactosTags = new HashMap<String, String>();

	public static void main(String[] data) throws InterruptedException, IOException {

		Helper.loginOCP4();
//		new Resources(namespace, useArtefactosDinamicos, artefactosTags, artefactosTags.keySet(),
//				"04d6b08b4fd321cd6c9cfea35dac7774").star();
		boolean useArtefactosDinamicos = true;
		new Resources(namespace, useArtefactosDinamicos, null, null,
				null).star();
		
	}

	static {
		artefactosTags.put("autorizaciones-ui", "20231218131825-migracion-ocp4-bff-V1.7.0");
		artefactosTags.put("autorizaciones-bff", "20231220131933-migracion-ocp4");
		artefactosTags.put("openshift-activemq", "7.11.0-8");
		artefactosTags.put("alertas-api", "20231107150731-migracion-ocp4");
		artefactosTags.put("aprobaciones-api", "20231220144007-migracion-ocp4-V1.5.5");
		artefactosTags.put("ase-numera-api", "20231107132637-migracion-ocp4");
		artefactosTags.put("auditor-api", "20231107193410-migracion-ocp4-1.0.0");
		artefactosTags.put("auth-api", "20231107192042-migracion-ocp4-V1.5");
		artefactosTags.put("autorizacion-api", "20231221135203-migracion-ocp4-V1.5.4");
		artefactosTags.put("beneficiario-api", "20231114185437-migracion-ocp4-V1.5.5"); // migracion-ocp4-V1.5.7
		artefactosTags.put("autorizaciones-brokermq-api", "20231222123929-migracion-ocp4-noupgrade");
		artefactosTags.put("canal-api", "20231114125129-migracion-ocp4");
		artefactosTags.put("circuito-api", "20231108151227-migracion-ocp4");
		artefactosTags.put("documento-auditoria-api", "20231214194503-migracion-ocp4-V1.5.4");
		artefactosTags.put("documento-checklist-api", "20231218175616-migracion-ocp4-V1.5.1");
		artefactosTags.put("docu-check-legacy-api", "20231109112600-migracion-opc4-V1.5.1");
		artefactosTags.put("efecto-api", "20231116133426-migracion-ocp4");
		artefactosTags.put("empresa-api", "20231109124143-migracion-ocp4");
		artefactosTags.put("enfermedades-api", "20231109122932-migracion-ocp4");
		artefactosTags.put("filial-api", "20231109121906-migracion-ocp4");
		artefactosTags.put("frecuencia-api", "20231109120757-migracion-ocp4");
		artefactosTags.put("historial-api", "20231211155942-migracion-ocp4-V1.3");
		artefactosTags.put("jwt-api", "20231108152930-migracion-ocp4");
		artefactosTags.put("jwt-validator-api", "20231108150734-migracion-ocp4");
		artefactosTags.put("legacy-manager-api", "20231213175719-migracion-ocp4-V1.5.2");
		artefactosTags.put("medicamento-api", "20231108123312-migracion-ocp4");
		artefactosTags.put("medicos-api", "20231107175801-migracion-ocp4");
		artefactosTags.put("nomenclador-api", "20231215140545-migracion-ocp4-V1.2.24");
		artefactosTags.put("notificaciones-api", "20231211165946-migracion-ocp4-V1.5.1");
		artefactosTags.put("parametria-api", "20231215175816-migracion-ocp4");
		artefactosTags.put("pmo1-api", "20231113192717-migracion-ocp4");
		artefactosTags.put("reintegros-api", "20231219162439-migracion-ocp4-V1.7.0");
		artefactosTags.put("reintegros-bff", "20231218134801-develop-migracion-ocp4");
		artefactosTags.put("reintegros-ui", "20231218140321-refactor-ocp4-V1.7.0");
		artefactosTags.put("sanatorio-api", "20231106205839-migracion-ocp4");
	}
}
