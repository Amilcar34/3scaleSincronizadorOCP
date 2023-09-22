package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {

	static String command = "oc login --token=sha256~u-3vty1p7FmsiSpFbuuaYMKmJrJqAFjnQrGMr1x1HPU --server=https://api.osnoprod01.aseconecta.com.ar:6443";

	public static void main(String[] data) throws InterruptedException, IOException {
		
		login();
		printPods();
	}

	private static void printPods() throws InterruptedException, IOException {

		Process proc = Runtime.getRuntime().exec("oc get all -o json");

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
        }

        proc.waitFor(); 
		
	}

	private static void login() throws InterruptedException, IOException {

        Process proc = Runtime.getRuntime().exec(command);

        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
        }

        proc.waitFor(); 
		
	}
}
