package app;

import java.io.IOException;

import app.resources.Application;

public class ResourcesNamespacesTest {

	public static void main(String[] data) throws InterruptedException, IOException {

		Application.ejecute(Main.login);
		for (String namespace : namespaces) {
			new Resources(namespace, true, null, null).star();
		}
	}

	static String[] namespaces = new String[] { "aseautorizaciones-test", "aseprestadores-test", "aseventas-test",
			"auditoriaterreno-test", "medifemobile-test", "sigo-test"
//			, "servicioscomunes-test"
	};

}
