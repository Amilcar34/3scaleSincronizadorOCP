package app.resources;

import java.io.IOException;

import app.Main;
import app.Main;
import app.Resources;

public class ResourcesSIGO {

	static String namespace = "sigo-test";

	public static void main(String[] data) throws InterruptedException, IOException {

		Main.ejecute(Main.login);
		new Resources(namespace, false, null, null).star();
	}

}
