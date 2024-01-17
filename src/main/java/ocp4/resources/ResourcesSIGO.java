package ocp4.resources;

import java.io.IOException;

import app.Helper;
import app.Resources;

public class ResourcesSIGO {

	static String namespace = "sigo-test";

	public static void main(String[] data) throws InterruptedException, IOException {

		Helper.loginOCP4();
		new Resources(namespace, false, null, null, "").star();
	}

}
