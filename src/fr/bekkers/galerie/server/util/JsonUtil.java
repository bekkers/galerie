package fr.bekkers.galerie.server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.bekkers.galerie.server.domain.Adresse;
import fr.bekkers.galerie.server.domain.Aquarelle;
import fr.bekkers.galerie.server.domain.Galerie;
import fr.bekkers.galerie.shared.Constants;

public class JsonUtil {
	

	private final static Gson GSON = new GsonBuilder().setDateFormat(
			Constants.DATE_FORMAT_STRING).create();

	public static Aquarelle readAquarelleFromString(String jsonString) {
		return GSON.fromJson(jsonString, Aquarelle.class);
	}

	public static String toJson(Galerie galerie) {
		return GSON.toJson(galerie);
	}

	public static String toJson(Aquarelle aquarelle) {
		return GSON.toJson(aquarelle);
	}

	public static String toJson(Adresse adresse) {
		return GSON.toJson(adresse);
	}

	public static String toJson(ServerProps props) {
		return GSON.toJson(props);
	}

}
