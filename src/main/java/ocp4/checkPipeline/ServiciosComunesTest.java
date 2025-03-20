package ocp4.checkPipeline;

import app.CheckStepPipeline;
import app.Helper;

public class ServiciosComunesTest {

	static String namespace = "servicioscomunes-test";

	public static void main(String[] args) {

		Helper.loginOCP4();
		Helper.selectNamespace(namespace);
		CheckStepPipeline app = new CheckStepPipeline(namespace);
		app.star();
	}
}
