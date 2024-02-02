package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import app.model.Status;

public class Helper {

	private static String tokenOCP4 = "iePGmgMiGpGiGEDW-cf5dSMDv_3zO-kFbvY7i2dRoaU";

	private static String tokenOCP3 = "_B-wPhHe-c-6-QLcx0swn4n2cyVXV836ovVQwSV6TFY";
	
	public static void loginOCP3() {
		System.out.println(loginOCP3);
		ejecute(loginOCP3);
	}

	public static String loginOCP4() {
		System.out.println(loginOCP4);
		return ejecuteResponse(loginOCP4);
	}

	public static String ejecuteResponse(String command) {
//		System.out.println(command);
		StringBuffer response = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);

			BufferedReader readerInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader readerError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			response = new StringBuffer(readerInput.read());
//			response = new StringBuffer(readerError.read());

			String line = "";
			while ((line = readerInput.readLine()) != null) {
				response.append(line + "\n");
			}
			proc.waitFor();

		} catch (IOException | InterruptedException e) {
			System.err.println("Error al ejecutar");
			System.err.println(command);
			e.printStackTrace();
		}
		return response.toString();
	}

	public static String getTag(String aplication) {
		String image = "oc get deployments " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].image}\"";
		image = ejecuteResponse(image);
		int length = image.length();
		image = image.substring(image.lastIndexOf(":"), length);
		length = image.length();
		--length;
		image = image.substring(1, --length);
		return image;
	}

	public static Set<String> getArtefactos() {
		// valido para OCP 4, en OCP 3 usar 'deployments' -> 'dc' /DeploimentConfig
		String command = "oc get deployments -o jsonpath=\"{.items[*]['metadata.name']}\"";
		String respuesta = ejecuteResponse(command);
		String replaceAll = respuesta.replaceAll("\"", "").replace("\n", "");
		return Set.of(replaceAll.split(" "));
	}

	public static void selectNamespace(String namespace) {
		String command = "oc project " + namespace;
		String ejecuteResponse = ejecuteResponse(command);
//		System.err.println(ejecuteResponse);
	}

	public static String getIdConfigMap(String aplication) {
		String idConfigmapCommand = "oc get deployments " + aplication
				+ " -o jsonpath=\"{['spec.template.spec.containers'][0].envFrom[0].configMapRef.name}\"";
		String idConfigmap = clean(ejecuteResponse(idConfigmapCommand));
		return idConfigmap;
	}

	public static Map<String, String> getConfigMapById(String idConfigmap) {

		String configMapString = "oc get configmap " + idConfigmap + " -o jsonpath=\"{['data']}\"";
		configMapString = clean(ejecuteResponse(configMapString));
		return configMapString.isBlank() ? Map.of() : new Gson().fromJson(configMapString, Map.class);
	}

	public static Map<String, String> getConfigMapByAplication(String aplication) {
		String idConfigmap = getIdConfigMap(aplication);
		// se descarta si no tiene configMap
		return idConfigmap.length() > 3 ? getConfigMapById(idConfigmap) : Map.of();
	}

	public static Set<String> getIdsConfigsMapsByNamespace(String namespace) {

		Set<String> IdsConfigsMaps = new HashSet<String>();
		selectNamespace(namespace);
		Set<String> artefactos = getArtefactos();
		for (String aplication : artefactos) {
			String idConfigmap = getIdConfigMap(aplication);
			IdsConfigsMaps.add(idConfigmap);
		}
		return IdsConfigsMaps;
	}

	public static Set<String> getIdsConfigsMaps() {

		Set<String> IdsConfigsMaps = new HashSet<String>();
		Set<String> artefactos = getArtefactos();
		for (String aplication : artefactos) {
			String idConfigmap = getIdConfigMap(aplication);
			IdsConfigsMaps.add(idConfigmap);
		}
		return IdsConfigsMaps;
	}

	public static String clean(String ejecute) {
		int lengt = ejecute.length();
		--lengt;
		return ejecute.substring(0, --lengt);
	}

	public static void ejecute(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
//			print(process.getInputStream());
			print(process.getErrorStream());
			process.waitFor();
			int exitStatus = process.exitValue();
			System.out.println("exit status: " + exitStatus);
		} catch (IOException | InterruptedException e) {
			System.err.println("Error al ejecutar");
			System.err.println(command);
			e.printStackTrace();
		}
	}

	public static void print(InputStream input) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				BufferedReader bf = new BufferedReader(new InputStreamReader(input));
				String line = null;
				try {
					while ((line = bf.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					System.out.println("IOException");
				}
			}
		}).start();
	}

	public static Gson getGsonCondition() {

		return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type type,
					JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return LocalDateTime.ofInstant(Instant.parse(asString), ZoneId.systemDefault());
			}
		}).registerTypeAdapter(Status.class, new JsonDeserializer<Status>() {
			@Override
			public Status deserialize(JsonElement json, Type type,
					JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return Status.valueOf(asString);
			}
		}).registerTypeAdapter(app.model.Type.class, new JsonDeserializer<app.model.Type>() {
			@Override
			public app.model.Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return app.model.Type.valueOf(asString);
			}
		}).create();
	}

	public static Table<String, String, String> newTable() {
		return Tables.newCustomTable(Maps.<String, Map<String, String>>newHashMap(),
				new Supplier<Map<String, String>>() {
					public Map<String, String> get() {
						return Maps.newHashMap();
					}
				});
	}

	static String loginOCP4 = "oc login --token=sha256~" + tokenOCP4
			+ " --server=https://api.osnoprod01.aseconecta.com.ar:6443";
	static String loginOCP3 = "oc login https://openshift.ase.local:443 --token=" + tokenOCP3;
}
