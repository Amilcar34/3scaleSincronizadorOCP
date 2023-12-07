package app;

import java.io.IOException;

public class ResourcesNamespacesTest {

	public static void main(String[] data) throws InterruptedException, IOException {

		Main.ejecute(Main.login);
		for (String namespace : namespaces) {
			System.out.println();
			System.out.println("---------------------------------------------------TRABAJANDO EN: " + namespace);
			new Resources(namespace, true, null, null).star();
			System.out.println();
		}
	}

	static String[] namespaces = new String[] { "aseautorizaciones-test", "aseprestadores-test", "aseventas-test",
			"auditoriaterreno-test", "medifemobile-test", "sigo-test"
//			, "servicioscomunes-test"
	};

}
