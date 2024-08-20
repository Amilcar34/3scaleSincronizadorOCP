package ocp4.checkPipeline;

import app.CheckStepPipeline;
import app.Helper;

public class SigoTest {

	static String namespace = "sigo-test";

	public static void main(String[] args) {

		Helper.loginOCP4();
		Helper.selectNamespace(namespace);
		CheckStepPipeline app = new CheckStepPipeline(namespace);
		app.star();
	}
}
