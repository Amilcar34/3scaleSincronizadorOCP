package app;

import java.io.IOException;

public class ResourcesNamespacesTest {

	public static void main(String[] data) throws InterruptedException, IOException {

		Helper.loginOCP4();
		for (String namespace : namespaces) {
			System.out.println();
			System.out.println("---------------------------------------------------TRABAJANDO EN: " + namespace);
			new Resources(namespace, true, null, null, null).star();
			System.out.println();
		}
	}

	static String[] namespaces = new String[] { "aseautorizaciones-dev",
			"aseautorizaciones-test",
			"aseautorizaciones-uat",
			"aseconectadevops-test",
			"aseprestadores-dev",
			"aseprestadores-test",
			"aseprestadores-uat",
			"aseredes-dev",
			"aseredes-test",
			"aseventas-dev",
			"aseventas-test",
			"auditoriaterreno-dev",
			"auditoriaterreno-test",
			"cicd",
			"medifemobile-dev",
			"medifemobile-test",
			"mediflex-test",
			"minio-operator",
			"nexus",
			"openshift-operators",
			"openshift-operators-redhat",
			"openshift-tempo-operator",
			"poc",
			"revproxy-test",
			"rhpam",
			"rhpam-73",
			"servicioscomunes-dev",
			"servicioscomunes-test",
			"sigo-dev",
			"sigo-test",
			"sigoingresos-dev",
			"sigoingresos-test",
			"sigoprestaciones-dev",
			"sigoprestaciones-test",
			"sonarqube",
			"threescale",
	};

}
