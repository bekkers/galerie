package fr.bekkers.galerie.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.bekkers.galerie.shared.AquarelleLight;

public interface GalerieServiceAsync {

	void getLightList(AsyncCallback<AquarelleLight[]> callback);

	void getAquarelle(int id, AsyncCallback<String> callback);

	void deleteAquarelle(int id, AsyncCallback<AquarelleLight[]> callback);

	void updateAquarelle(String aquarelleAsJson,
			String fileName, AsyncCallback<AquarelleLight[]> callback);

	void createAquarelle(String aquarelleAsJson, String tempFile,
			AsyncCallback<AquarelleLight[]> callback);

	void reloadGalerie(AsyncCallback<AquarelleLight[]> callback);

	void start(String theme, AsyncCallback<String> callback);

	void getGps(AsyncCallback<String[]> callback);

}
