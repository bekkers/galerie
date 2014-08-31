package fr.bekkers.galerie.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import fr.bekkers.galerie.shared.AquarelleLight;
import fr.bekkers.galerie.shared.GalerieException;

@RemoteServiceRelativePath("galerieService")
public interface GalerieService extends RemoteService {
	
	public String start(String theme) throws GalerieException;
	
	public AquarelleLight[] getLightList() throws GalerieException;

	public AquarelleLight[] createAquarelle(String aquarelleAsJson, String tempFile)
			throws GalerieException;

	public AquarelleLight[] deleteAquarelle(int id) throws GalerieException;

	public AquarelleLight[] updateAquarelle(String aquarelleAsJson, String fileName) throws GalerieException;

	public String getAquarelle(int id) throws GalerieException;

	public AquarelleLight[] reloadGalerie() throws GalerieException;

	public String[] getGps() throws GalerieException;
	
	
}
