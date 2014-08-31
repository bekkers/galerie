package fr.bekkers.galerie.server.domain;

import fr.bekkers.galerie.server.DateConverter;
import fr.bekkers.galerie.shared.AquarelleLight;

public class ObjectConverter {

	public static AquarelleLight toAquarelleLight(Aquarelle aquarelle) {
		AquarelleLight aquarelleLight = new AquarelleLight();
		aquarelleLight.setName(aquarelle.getName());
		aquarelleLight.setGps(aquarelle.getGps());
		aquarelleLight.setId(aquarelle.getId());
		aquarelleLight.setTitle(aquarelle.getTitle());
		aquarelleLight.setDate(DateConverter.write(aquarelle.getDate()));
		return aquarelleLight;
	}

}
