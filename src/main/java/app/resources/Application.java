package app.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.Main;

public class Application {


	public static String ejecute(String command) {
//		System.out.println(command);
		StringBuffer response = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);

			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			response = new StringBuffer(reader.read());
			String line = "";
			while ((line = reader.readLine()) != null) {
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
}
