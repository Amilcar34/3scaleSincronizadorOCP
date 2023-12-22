package app;

import static app.Main.ejecuteResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;
import com.google.gson.Gson;

import app.model.ReadinessProbe;
import app.model.Resource;

public class Resources {

	private static final String leftAlignFormat = "| %-27s | %-30s | %-60s | %n";

	private String KEY_3SCALE_VALUE;
	private final String namespace;
	private boolean useArtefactosDinamicos = false;
	private final Map<String, String> artefactosTags;
	private final Set<String> artefactos;

	public Resources(String namespace, boolean useArtefactosDinamicos, Map<String, String> artefacttosTags,
			Set<String> artefactos, String key3scaleValue) {

		this.namespace = namespace;
		this.useArtefactosDinamicos = useArtefactosDinamicos;
		this.artefactosTags = artefacttosTags;
		this.artefactos = artefactos;
		selectNamespaceTest();
		this.KEY_3SCALE_VALUE = getKey3scaleValue(key3scaleValue);
	}

	public void star() throws InterruptedException, IOException {

		interateProject();

		System.out.println("\n----- Incorrectos Por Recursos: " + incorrectosRecursos.size());
		if (!incorrectosRecursos.isEmpty())
			incorrectosRecursos.forEach(System.out::println);

		System.out.println("\n----- Incorrectos Por ReadinessProbe: " + incorrectosReadinessProbe.size());
		if (!incorrectosReadinessProbe.isEmpty())
			incorrectosReadinessProbe.forEach(System.out::println);

		System.out.println("\n----- Incorrectos Por LivenessProbe: " + incorrectosLivenessProbe.size());
		if (!incorrectosLivenessProbe.isEmpty())
			incorrectosLivenessProbe.forEach(System.out::println);

//		System.out.println("----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ----- ");
//		System.out.println("----- Artefactos - tags OCP4: ");
//		tagsCluster.forEach((k, v) -> System.out.println(k + " - " + v));
//		
//		System.out.println("----- Artefactos - tags Docuemnto: ");
//		artefacttosTags.forEach((k, v) -> System.out.println(k + " - " + v));

		if (this.useArtefactosDinamicos == false) {
			System.out.println("\n--- Start: Artefactos - tags DIFERENCIAS: \n");
			String format = "| %-20s | %-40s | %-40s | %n";
			System.out.format(format, "  Artefacto", "  Tag local", "  Tag local Cluster");
			artefactosTags.forEach((artefacto, imageLocal) -> {
				sleep();
				String imageCluster = tagsCluster.get(artefacto);
				if (!imageCluster.equals(imageLocal))
					System.err.format(format, artefacto, imageLocal, imageCluster);
			});
			System.out.println("\n----- FIN: Artefactos - tags DIFERENCIAS \n");
		}

		System.out.println("\n----- Artefactos que usan rutas publicas: " + rutasPublicas.size());
		if (!rutasPublicas.isEmpty()) {
			
			System.out.println("- - - Start: Artefactos que usan rutas publicas - - - \n");
			
			printErrApplicationKeyValue(" Deployment / Artefacto ", " Variable de entorno ", " Valor ");

			for (Cell<String, String, String> cell : rutasPublicas.cellSet())
				printApplicationKeyValue(cell.getRowKey(), cell.getColumnKey(), cell.getValue());

			System.out.println("\n- - - FIN: Artefactos que usan rutas publicas - - -");
		}
	}

	private void interateProject() {

		iterateRecursos();
		iterateReadinessProbe();
		iterateLivenessProbe();
		verifyConfigMap();
	}

	/**
	 * Este metodo verifica que todas las variables que tienen el mismo valor tengan
	 * el mismo nombre
	 * 
	 * @throws InterruptedException
	 * 
	 */
	private void verifyConfigMap() {

		Map<String, String> applicationKey_conFaltaProtocolo = new HashMap<String, String>();

		for (String aplication : getArtefactos()) {

			Map<String, String> configmapTemp = new HashMap<String, String>();
			Map<String, String> configmapHistoricoTemp = new HashMap<String, String>(configsMaps);

			Table<String, String, String> configMap3DValueApplicationTemp = newTable();

			String idConfigmapCommand = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].envFrom[0].configMapRef.name}\"";
			String idConfigmap = clean(ejecuteResponse(idConfigmapCommand));

			if (idConfigmap.length() > 3) { // se descarta si no tiene configmap
				String configMapString = "oc get configmap " + idConfigmap + " -o jsonpath=\"{['data']}\"";
				configMapString = clean(ejecuteResponse(configMapString));

				Map<String, String> configmap = new Gson().fromJson(configMapString, Map.class);

				for (Map.Entry<String, String> entry : configmap.entrySet()) {
					String v = entry.getValue();
					String k = entry.getKey();
					sleep();

//					printApplicationKeyValue(aplication, k, v);

					if (this.KEY_3SCALE_VALUE != null && v.contains(this.KEY_3SCALE_VALUE)) {
						if (!k.equals(KEY_3SCALE)) {
							System.err.println("Debe llamarse: " + KEY_3SCALE + " pero se llama: " + k);
							printErrApplicationKeyValue(aplication, k, v);
						}
					}
					if (v.toLowerCase().contains("3scale")) {
						if (!k.equals(HOST_3SCALE)) {
							System.err.println("Debe llamarse: " + HOST_3SCALE + " pero se llama: " + k);
							printErrApplicationKeyValue(aplication, k, v);
						}
					}
					if (v.contains("apps.osnoprod01.aseconecta.com.ar") || v.contains(".svc.cluster.local"))
						if (!v.contains("http"))
							applicationKey_conFaltaProtocolo.put(aplication, k);
						else if (v.contains("apps.osnoprod01.aseconecta.com.ar") && !k.equals(HOST_3SCALE))
							rutasPublicas.put(aplication, k, v);

					// Sieve para filtrar cuando hay un mismo deployment
					// con iguales valores a diferentes key (pasa con datos de DBs)
					String valueTemp = configmapTemp.put(v, k);

					String valueHistoricoTemp = configmapHistoricoTemp.put(v, k);
					configMap3DValueApplicationTemp.put(v, k, aplication);

					if (valueTemp == null && valueHistoricoTemp != null && !valueHistoricoTemp.equals(k)) {
						System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
						printErrApplicationKeyValue(aplication, k, v);
						String a = configsMaps3DValueDeployment.get(v, configsMaps.get(v));
						printErrApplicationKeyValue(a, configsMaps.get(v), v);
					}
				}
			}
			configsMaps = new HashMap<String, String>(configmapHistoricoTemp);
			configsMaps3DValueDeployment.putAll(configMap3DValueApplicationTemp);
		}

		if (!applicationKey_conFaltaProtocolo.isEmpty()) {
			System.err.println("------------------------------------------------------------------------");
			System.err.println("\n Debe contener el protocolo HTTP o HTTPS \n");
			applicationKey_conFaltaProtocolo.forEach((k, v) -> System.err.format(leftAlignFormat, k, v, ""));
			System.err.println("------------------------------------------------------------------------");
		}

	}

	private void sleep() {
		try {
			Thread.sleep(8);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void printApplicationKeyValue(String a, String k, String v) {
		System.out.format(leftAlignFormat, a, k, v);
	}

	private static void printErrApplicationKeyValue(String a, String k, String v) {
		System.err.format(leftAlignFormat, a, k, v);
	}

	private void iterateLivenessProbe() {

		System.out.println("--------------------LIVENESS PROBE -------");
		for (String aplication : getArtefactos()) {

			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].livenessProbe}\"";

			requests = clean(ejecuteResponse(requests));

			if (requests.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("\nERROR: NO POSEE LivenessProbe");
				incorrectosLivenessProbe.add(aplication);
			} else {
				ReadinessProbe resource = new Gson().fromJson(requests, ReadinessProbe.class);
				if (resource.httpGet == null || resource.httpGet.path == null) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("ERROR: NO POSEE DATOS LivenessProbe");
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.initialDelaySeconds != 120) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("initialDelaySeconds distinto de 120 : " + resource.initialDelaySeconds);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.periodSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("periodSeconds distinto de 30 : " + resource.periodSeconds);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.successThreshold != 1) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("successThreshold distinto de 1 : " + resource.successThreshold);
					incorrectosLivenessProbe.add(aplication);
				} else if (resource.timeoutSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("timeoutSeconds distinto de 30 : " + resource.timeoutSeconds);
					incorrectosLivenessProbe.add(aplication);
				}
			}
		}
		System.out.println("\n Finalizo el chequeo por LivenessProbe \n");
	}

	private void iterateReadinessProbe() {

		System.out.println("--------------------READINESS PROBE -------");
		for (String aplication : getArtefactos()) {

			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].readinessProbe}\"";

			requests = clean(ejecuteResponse(requests));
			if (requests.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("ERROR: NO POSEE readinessProbe");
				incorrectosReadinessProbe.add(aplication);
			} else {
				ReadinessProbe resource = new Gson().fromJson(requests, ReadinessProbe.class);
				if (resource.httpGet == null || resource.httpGet.path == null) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("ERROR: NO POSEE DATOS readinessProbe");
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.initialDelaySeconds != 120) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("initialDelaySeconds distinto de 120 : " + resource.initialDelaySeconds);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.periodSeconds != 60) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("periodSeconds distinto de 60 : " + resource.periodSeconds);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.successThreshold != 1) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("successThreshold distinto de 1 : " + resource.successThreshold);
					incorrectosReadinessProbe.add(aplication);
				} else if (resource.timeoutSeconds != 30) {
					System.err.print(namespace + " ");
					System.out.println(aplication);
					System.err.println("timeoutSeconds distinto de 30 : " + resource.timeoutSeconds);
					incorrectosReadinessProbe.add(aplication);
				}
			}
			sleep();
		}
		System.out.println("\n Finalizo el chequeo por ReadinessProbe \n");
	}

	private void iterateRecursos() {

		System.out.println("Arrancó el chequeo por recursos \n");
		for (String aplication : getArtefactos()) {
			String limits = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.limits}\"";
			String requests = "oc get deployments " + aplication
					+ " -o jsonpath=\"{['spec.template.spec.containers'][0].resources.requests}\"";

			setTags(aplication);
			limits = clean(ejecuteResponse(limits));
			requests = clean(ejecuteResponse(requests));

			if (requests.isBlank() || limits.isBlank()) {
				System.err.print(namespace + " ");
				System.out.println(aplication);
				System.err.println("ERROR: NO POSEE limite de recursos asignados");
				incorrectosRecursos.add(aplication);
			} else {
				Resource resource = new Gson().fromJson(requests, Resource.class);
				if (resource.getCpu().endsWith("m") && resource.getCpu().startsWith("10")) {

				} else if (resource.getCpu().endsWith("m")) {
					int intResource = extracted(resource);
					if (intResource > 10) {
						System.err.print(namespace + " ");
						System.out.println(aplication);
						System.out.println(requests);
						incorrectosRecursos.add(aplication);
					}
				}

			}
		}
		System.out.println("Finalizo el chequeo por recursos \n");
	}

	private void setTags(String aplication) {

		String image = "oc get deployments " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].image}\"";
		image = ejecuteResponse(image);
		int length = image.length();
		image = image.substring(image.lastIndexOf(":"), length);
		length = image.length();
		--length;
		image = image.substring(1, --length);
		tagsCluster.put(aplication, image);
	}

	private int extracted(Resource resource) {
		int intResource = 0;
		try {
			intResource = Integer.valueOf(resource.getCpu().replaceAll("[^-?0-9]+", ""));
		} catch (NumberFormatException e) {
			System.err.print("Error: " + resource.getCpu());
		}
		return intResource;
	}

	private void selectNamespaceTest() {
		String command = "oc project " + namespace;
		System.out.println(ejecuteResponse(command));
	}

	private String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	Set<String> getArtefactos() {

		if (useArtefactosDinamicos)
			if (artefactosDinamicos == null) {
				String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"";
				String respuesta = ejecuteResponse(command);
				String replaceAll = respuesta.replaceAll("\"", "");
				artefactosDinamicos = Set.of(replaceAll.split(" "));
				return artefactosDinamicos;
			} else
				return artefactosDinamicos;
		return artefactos;
	}

	private String getKey3scaleValue(String KEY_3SCALE_VALUE) {

		if (KEY_3SCALE_VALUE == null || KEY_3SCALE_VALUE.isBlank())
			for (String aplication : getArtefactos()) {

				String idConfigmapCommand = "oc get deployments " + aplication
						+ " -o jsonpath=\"{['spec.template.spec.containers'][0].envFrom[0].configMapRef.name}\"";

				String idConfigmap = clean(ejecuteResponse(idConfigmapCommand));

				if (idConfigmap.length() > 3) { // se descarta si no tiene configmap
					String configMapString = "oc get configmap " + idConfigmap + " -o jsonpath=\"{['data']}\"";
					configMapString = clean(ejecuteResponse(configMapString));

					Map<String, String> configmap = new Gson().fromJson(configMapString, Map.class);

					for (Map.Entry<String, String> entry : configmap.entrySet()) {
						String v = entry.getValue();
						String k = entry.getKey();
						if (k.equals(KEY_3SCALE)) {
							this.KEY_3SCALE_VALUE = v;
							return this.KEY_3SCALE_VALUE;
						}
					}
				}
			}
		else
			this.KEY_3SCALE_VALUE = KEY_3SCALE_VALUE;

		if (this.KEY_3SCALE_VALUE == null || this.KEY_3SCALE_VALUE.isBlank())
			System.err.println("No se encontró VALUE de 3scale KEY");

		return this.KEY_3SCALE_VALUE;
	}

	final static String HOST_3SCALE = "HOST_3SCALE";
	final static String KEY_3SCALE = "KEY_3SCALE";

	Set<String> artefactosDinamicos;

	Map<String, String> tagsCluster = new HashMap<String, String>();
	List<String> incorrectosRecursos = new ArrayList<String>();
	List<String> incorrectosReadinessProbe = new ArrayList<String>();
	List<String> incorrectosLivenessProbe = new ArrayList<String>();

	Map<String, String> configsMaps = new HashMap<String, String>();

	Table<String, String, String> configsMaps3DValueDeployment = newTable();
	Table<String, String, String> rutasPublicas = newTable();

	private Table<String, String, String> newTable() {
		return Tables.newCustomTable(Maps.<String, Map<String, String>>newHashMap(),
				new Supplier<Map<String, String>>() {
					public Map<String, String> get() {
						return Maps.newHashMap();
					}
				});
	}

	static {
	}

}
