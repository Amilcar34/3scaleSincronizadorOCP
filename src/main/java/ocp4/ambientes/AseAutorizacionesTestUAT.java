package ocp4.ambientes;

import static app.Helper.ejecuteResponse;
import static app.Helper.getArtefactos;
import static app.Helper.getConfigMapByAplication;
import static app.Helper.getTag;
import static app.Helper.loginOCP4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AseAutorizacionesTestUAT {

	private static String namespaceOrigin = "aseautorizaciones-test";
	private static String namespaceTarget = "aseautorizaciones-uat";

	public static void main(String... args) {

		loginOCP4();

		// Cargo la referencia
		pullDeploymentTarget();

		// Cargo la lo que voy a comparar
		pullDeploymentOrigin();

		// los empiezo a comparar
		compareTags_Origin_Target();

		pullConfigMap(namespaceTarget, artefactosTarget, configsMapsTarget);
		pullConfigMap(namespaceOrigin, artefactosOrigin, configsMapsOrigin);

		compareKeys_Origin_Target();

		System.out.println("-------FIN-------");
	}

	private static void compareKeys_Origin_Target() {

		System.out.println("Diferencias en base a " + namespaceTarget);
		configsMapsTarget.forEach((k, v) -> {

			Set<String> deploymentOrigin = configsMapsOrigin.get(k);

			if (!v.containsAll(deploymentOrigin)) {
				System.out.print("Se encontraron diferencias en ");
				System.err.println(k);
				System.out.format(leftAlignFormat, " Artefacto", " Key", "");
				deploymentOrigin.forEach(dO -> {
					if (!v.contains(dO))
						System.err.format(leftAlignFormat, k, dO, "");
				});
				System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
			}
		});

		System.err.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		System.out.println("Diferencias en base a " + namespaceOrigin);
		System.out.format(leftAlignFormat, " Artefacto", " Key", "");
		configsMapsOrigin.forEach((k, v) -> {

			Set<String> deploymentTarget = configsMapsTarget.get(k);

			if (deploymentTarget != null && !v.containsAll(deploymentTarget)) {
				System.out.print("Se encontraron diferencias en " + k + "\n");
				System.out.format(leftAlignFormat, " Artefacto", " Key", "");
				deploymentTarget.forEach(dT -> {
					if (!v.contains(dT))
						System.err.format(leftAlignFormat, k, dT, "");
				});
				System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
			}
		});

	}

	private static void pullConfigMap(String namespace, Set<String> artefactos, Map<String, Set<String>> configsMaps) {
		String command = "oc project " + namespace;
		System.out.println(ejecuteResponse(command));
		artefactos.forEach(artefacto -> {
//			System.out.println(artefacto);
			Map<String, String> configMapByAplication = getConfigMapByAplication(artefacto);
			Set<String> keySet = configMapByAplication.keySet();
			configsMaps.put(artefacto, keySet);
		});
	}

	private static void pullDeploymentOrigin() {
		String command = "oc project " + namespaceOrigin;
		System.out.println(ejecuteResponse(command));
		artefactosOrigin = getArtefactos();
		Set<String> artefactosOriginTemp = getArtefactos();
		artefactosOrigin = artefactosOriginTemp.parallelStream().filter(a -> !a.contains("apicast"))
				.collect(Collectors.toSet());
		pullDeployment(namespaceOrigin, deploymentOrigin, artefactosOrigin);
	}

	private static void pullDeploymentTarget() {
		String command = "oc project " + namespaceTarget;
		System.out.println(ejecuteResponse(command));
		Set<String> artefactosTargetTemp = getArtefactos();
		artefactosTarget = artefactosTargetTemp.parallelStream().filter(a -> !a.contains("apicast"))
				.collect(Collectors.toSet());
		pullDeployment(namespaceTarget, deploymentTarget, artefactosTarget);
	}

	private static void compareTags_Origin_Target() {
		System.out.println("Diferencias por deployment");
		System.out.format(leftAlignFormat, " Namespace", " Artefacto", " Tag");
		deploymentTarget.forEach((k, v) -> {
			String deployment = deploymentOrigin.get(k);

			if (!deploymentOrigin.get(k).equals(v)) {
				System.err.format(leftAlignFormat, namespaceOrigin, k, deploymentOrigin.get(k));
				System.err.format(leftAlignFormat, namespaceTarget, k, v);
				System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
			}
		});
	}

	private static void pullDeployment(String namespaceOrigin, Map<String, String> deploymentOrigin,
			Iterable<String> artefactos) {

		artefactos.forEach(aplication -> {
			String image = getTag(aplication);
//				System.err.format(leftAlignFormat, aplication, image);
			deploymentOrigin.put(aplication, image);
		});
	}

	private static final String leftAlignFormat = "| %-25s | %-20s | %-40s | %n";

	private static Map<String, String> deploymentOrigin = new HashMap<String, String>();
	private static Map<String, String> deploymentTarget = new HashMap<String, String>();

	private static Set<String> artefactosOrigin = new HashSet<String>();
	private static Set<String> artefactosTarget = new HashSet<String>();

	private static Map<String, Set<String>> configsMapsOrigin = new HashMap<>();
	private static Map<String, Set<String>> configsMapsTarget = new HashMap<>();

}