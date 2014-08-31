package fr.bekkers.galerie.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import com.google.gson.JsonSyntaxException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.bekkers.galerie.client.GalerieService;
import fr.bekkers.galerie.server.domain.Aquarelle;
import fr.bekkers.galerie.server.domain.Galerie;
import fr.bekkers.galerie.server.domain.ObjectConverter;
import fr.bekkers.galerie.server.util.GalerieUtil;
import fr.bekkers.galerie.server.util.JsonUtil;
import fr.bekkers.galerie.server.util.ServerProps;
import fr.bekkers.galerie.server.util.ServerTheme;
import fr.bekkers.galerie.server.util.XmlUtil;
import fr.bekkers.galerie.shared.AquarelleLight;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

@SuppressWarnings("serial")
public class GalerieServiceImpl extends RemoteServiceServlet implements
		GalerieService {

	private static Logger logger = Logger.getLogger(GalerieServiceImpl.class
			.getName());

	private ServletContext getApplication() {
		return this.getThreadLocalRequest().getSession().getServletContext();
	}

	@Override
	public AquarelleLight[] getLightList() throws GalerieException {
		ServletContext application = getApplication();
		synchronized (application) {
			Galerie galerie = GalerieBD.getGalerie(application);
			return buildLightList(galerie);
		}
	}

	private AquarelleLight[] buildLightList(Galerie galerie) {
		List<Aquarelle> aquarelles = galerie.getAquarelle();
		int size = aquarelles.size();
		AquarelleLight[] result = new AquarelleLight[size];
		for (int i = 0; i < size; i++) {
			result[i] = ObjectConverter.toAquarelleLight(aquarelles.get(i));
		}
		return result;
	}

	@Override
	public String getAquarelle(int id) throws GalerieException {
		ServletContext application = getApplication();
		synchronized (application) {
			Aquarelle foundAquarelle = GalerieBD.getAquarelleById(id,
					application);
			return JsonUtil.toJson(foundAquarelle);
		}
	}

	@Override
	public AquarelleLight[] createAquarelle(String aquarelleAsJson,
			String tempFileName) throws GalerieException {
		ServletContext application = getApplication();
		try {
			logger.info("creating aquarelleAsJson = " + aquarelleAsJson
					+ "\n fileName = " + tempFileName);
			Aquarelle aquarelle = JsonUtil
					.readAquarelleFromString(aquarelleAsJson);
			saveImage(tempFileName, aquarelle.getName());
			GalerieBD.createAquarelle(getApplication(), aquarelle);
			Galerie galerie = GalerieBD.getGalerie(application);
			return buildLightList(galerie);
		} catch (JsonSyntaxException e) {
			String mess = "json incorrect pour une Aquarelle : "
					+ aquarelleAsJson + " error : " + e.getMessage();
			logger.warning(mess);
			throw new GalerieException(mess);
		}
	}

	@Override
	public AquarelleLight[] deleteAquarelle(int id) throws GalerieException {
		// TODO feature Faut-il effacer les images correspondant à une oeuvre effacée ?
		Galerie galerie = GalerieBD.deleteAquarelle(getApplication(), id);
		return buildLightList(galerie);
	}

	@Override
	public AquarelleLight[] updateAquarelle(String aquarelleAsJson, String tempFileName)
			throws GalerieException {
		logger.info("updating aquarelleAsJson = " + aquarelleAsJson);
		Aquarelle aquarelle = JsonUtil.readAquarelleFromString(aquarelleAsJson);
		if (tempFileName.length()!=0) {
			saveImage(tempFileName, aquarelle.getName());
		}
		Galerie galerie = GalerieBD.saveAquarelle(getApplication(), aquarelle);
		return buildLightList(galerie);
	}

	@Override
	public AquarelleLight[] reloadGalerie() throws GalerieException {
		try {
			XmlUtil.reloadGalerie();
		} catch (JAXBException e) {
			String msg = "impossible de recharger la galerie";
			logger.severe(msg);
			throw new GalerieException(msg, e);
		}
		ServletContext application = getApplication();
		Galerie galerie = GalerieBD.getGalerie(application, true);
		return buildLightList(galerie);
	}

	private void saveImage(String tempFileName, String name)
			throws GalerieException {
		logger.info("Application file path = "
				+ Constants.APPLICATION_FILE_PATH);
		String tempFilePath = Constants.APPLICATION_FILE_PATH
				+ Constants.TEMP_PATH + "/" + tempFileName;
		File tempFile = new File(tempFilePath);
		String fileName = name + ".jpg";
		File fullSizeFile = new File(Constants.APPLICATION_FILE_PATH
				+ Constants.IMAGE_FULL_SIZE_PATH + "/" + fileName);
		try {
			Files.copy(tempFile.toPath(), fullSizeFile.toPath(),
					StandardCopyOption.REPLACE_EXISTING);
			if (!tempFile.delete()) {
				String msg = "can't delete file '" + tempFilePath + "'";
				logger.severe(msg);
				throw new GalerieException(msg);
			}
		} catch (IOException e) {
			String msg = "IOException can't copy file '" + tempFilePath + "'";
			logger.severe(msg+" : "+e.getMessage());
			throw new GalerieException(msg, e);
		}
	}
	
	private final String PROPS = "props";
	private String CONTEXT_URL;

	@Override
	public String start(String themeName) throws GalerieException {
		final String GALERIE_SERVICE_REG_EXPR = "/galerie/galerieService";
		HttpServletRequest request = this.getThreadLocalRequest();
		CONTEXT_URL = request.getRequestURL()
				.toString().replaceFirst(GALERIE_SERVICE_REG_EXPR, "");
		logger.info("WEB api URL = " + CONTEXT_URL);
		ServerProps props = getProps(themeName, request);
		return JsonUtil.toJson(props);
	}

	private ServerProps getProps(String themeName, HttpServletRequest request)
			throws GalerieException {
		loadProps(request);
		HttpSession session = request.getSession();
		ServerProps props = (ServerProps) session.getAttribute(PROPS);
		if (props==null) {
			props = new ServerProps(CONTEXT_URL, themeName);
			session.setAttribute(PROPS, props);
		}
		return props;
	}

	private final String THEME = "theme";

	private Map<String, ServerTheme> loadProps(HttpServletRequest request)
			throws GalerieException {
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();
		Map<String, ServerTheme> themes = (Map<String, ServerTheme>) ctx.getAttribute(THEME);
		if (themes==null) {
			// chargement initial de properties
			themes = ServerTheme.load(request);
			ctx.setAttribute(THEME, themes);
		}
		for (String key : themes.keySet()) {
		logger.info(key+" : imageDetailSize = "+themes.get(key).getImageDetailSize());
		}
		return themes;
	}

	@Override
	public String[] getGps() throws GalerieException {
		ServletContext application = getApplication();
		synchronized (application) {
			Galerie galerie = GalerieBD.getGalerie(application);
			return buildGps(galerie);
		}
	}

	private String[] buildGps(Galerie galerie) {
		List<Aquarelle> aquarelles = galerie.getAquarelle();
		List<String> gpsList = new ArrayList<String>();
		int size = aquarelles.size();
		for (int i = 0; i < size; i++) {
			Aquarelle aquarelle = aquarelles.get(i);
			String gps = aquarelle.getGps();
			if (gps!=null && gps.length()!=0) {
				gpsList.add(gps+Constants.SPACE+aquarelle.getName());
			}
		}
		return gpsList.toArray(new String[gpsList.size()]);
	}

}
