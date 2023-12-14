package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import app.model.Status;

public class Main {

	private static String token =  "sha256~CuCyqY1gXaQGTgVN3brheNNQJVKerQtzN5mrdGy98is";
	public static String login = "oc login --token=" + token + " --server=https://api.osnoprod01.aseconecta.com.ar:6443";

	public static void main(String[] args) {
		File file = new File("./3scale");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		String[] files = file.list();
		for (String string : files) {
			System.out.println(string);
		}
	}
	
	public static String login() {
		return ejecuteResponse(login);
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

}
