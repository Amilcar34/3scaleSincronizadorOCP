package ocp4.checkPipeline;

import app.CheckStepPipeline;
import app.Helper;

public class ADTTest {

	static String namespace = "auditoriaterreno-test";

	public static void main(String[] args) {

		Helper.loginOCP4();
		Helper.selectNamespace(namespace);
		CheckStepPipeline app = new CheckStepPipeline(namespace);
		app.star();
	}
}
