package app;

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

	public static String login = "oc login --token=sha256~wRro1CQl7dstP-NUsdSHZzOkfAPBiaK2x4wtWHNdZUY --server=https://api.osnoprod01.aseconecta.com.ar:6443";

	public static void main(String[] args) {

		String asString = "2023-11-03T18:00:53Z";
		Instant jose = Instant.parse(asString);
		LocalDateTime ofInstant = LocalDateTime.ofInstant(Instant.parse(asString), ZoneId.systemDefault());
		System.out.println(ofInstant);
		String pepe = asString;
		System.out.println(LocalDateTime.parse(pepe));
	}

	public static Gson getGsonCondition() {
		return new GsonBuilder()
		.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
			@Override
			public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return LocalDateTime.ofInstant(Instant.parse(asString), ZoneId.systemDefault());
			}
		}).registerTypeAdapter(Status.class, new JsonDeserializer<Status>() {
			@Override
			public Status deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return Status.valueOf(asString);
			}
		}).registerTypeAdapter(app.model.Type.class, new JsonDeserializer<app.model.Type>() {
			@Override
			public app.model.Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				String asString = json.getAsJsonPrimitive().getAsString();
				return app.model.Type.valueOf(asString);				}
		})
		.create();
	}

	
}
