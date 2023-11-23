package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

	//static String login = "oc login --token=sha256~u-3vty1p7FmsiSpFbuuaYMKmJrJqAFjnQrGMr1x1HPU --server=https://api.osnoprod01.aseconecta.com.ar:6443";

	static String login = "oc login --token=sha256~5GY29gFrwPC9_nuE9r5hqjOWnW0N0fbuIgCd--OC1m8 --server=https://api.osnoprod01.aseconecta.com.ar:6443";
	
	public static void main(String[] data) throws InterruptedException, IOException {

		login();
//		printPods();
		select3Scale(true);
		productsVentasIn3scale(true);
		backendsVentasIn3scale(true, "ventas-test");
		//oc get products aseventas -o json

	}

	private static String backendsVentasIn3scale(boolean print, String grep) {
		
		String command = "oc get backends";
		String respuesta = "";
		try {
			respuesta = ejecute(command, grep);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		if(print)
			System.out.println(respuesta);
		return respuesta;
		
	}
	
	private static String productsVentasIn3scale(boolean print) {
		
		String command = "oc get products aseventas";
		String respuesta = "";
		try {
			respuesta = ejecute(command);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		if(print)
			System.out.println(respuesta);
		return respuesta;
		
	}

	private static String select3Scale(boolean print) {
		
		String command = "oc project 3scale";
		String respuesta = "";
		try {
			respuesta = ejecute(command);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		if(print)
			System.out.println(respuesta);
		return respuesta;
	}

	private static String printPods() {

		String command = "oc get all -o json";
		String respuesta = "";
		try {
			respuesta = ejecute(command);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
		return respuesta;
	}

	private static void login() {
		try {
			ejecute(login);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	private static String ejecute(String command) throws InterruptedException, IOException {

		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		StringBuffer response = new StringBuffer(reader.read());
		String line = "";
		while ((line = reader.readLine()) != null) {
			response.append(line + "\n");
		}
		proc.waitFor();
		return response.toString();
	}
	
	private static String ejecute(String command, String grep) throws InterruptedException, IOException {

		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

		StringBuffer response = new StringBuffer(reader.read());
		String line = "";
		while ((line = reader.readLine()) != null) {
			if(line.contains(grep))
				response.append(line + "\n");
		}
		proc.waitFor();
		return response.toString();
	}

}
