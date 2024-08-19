package ocp4.checkPipeline;

import app.CheckPipeline;
import app.Helper;

public class AseAutorizacionesTest {

	static String namespace = "aseautorizaciones-test";

	public static void main(String[] args) {

		Helper.loginOCP4();
		Helper.selectNamespace(namespace);
		CheckPipeline app = new CheckPipeline(namespace);
		app.star();
	}
}
