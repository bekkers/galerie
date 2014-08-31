package fr.bekkers.galerie.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import fr.bekkers.galerie.shared.GalerieException;
import fr.bekkers.galerie.shared.Theme;
import fr.bekkers.galerie.shared.ThemeName;

@SuppressWarnings("serial")
public class ServerTheme extends Properties implements Theme {

	private static Logger logger = Logger
			.getLogger(ServerTheme.class.getName());

	private final static Map<String, ServerTheme> themes = new HashMap<String, ServerTheme>();

	private final ThemeName themeName;

	public ServerTheme(ThemeName themeName1) {
		this.themeName = themeName1;
	}

	public static Map<String, ServerTheme> getThemes() {
		return themes;
	}

	public static Map<String, ServerTheme> load(HttpServletRequest request)
			throws GalerieException {

		Map<String, InputStream> inputs = new HashMap<String, InputStream>();
		try {
			// load properties files

			for (ThemeName themeName : ThemeName.values()) {

				String fileName = themeName.name() + ".properties";

				
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

				InputStream input =classLoader.getResourceAsStream(fileName);
				if (input == null) {
					String msg = "Impossible d'initialiser l'inputStream "
							+ fileName;
					logger.severe(msg);
					throw new GalerieException(msg);
				}
				inputs.put(themeName.name(), input);
				ServerTheme serverTheme = new ServerTheme(themeName);
				themes.put(themeName.name(), serverTheme);
				serverTheme.load(input);
			}

		} catch (IOException e) {
			String msg = "Coté serveur : chargement de theme impossible : "
					+ e.getMessage();
			logger.severe(msg);
			throw new GalerieException(msg, e);
		} finally {
			try {
				for (InputStream input : inputs.values()) {
					if (input != null) {
						input.close();
					}
				}
			} catch (IOException e) {
				String msg = "Coté serveur : chargement de theme, impossible de fermer un fichier inputStream : "
						+ e.getMessage();
				logger.severe(msg);
				throw new GalerieException(msg, e);
			}
		}
		return themes;
	}

	@Override
	public String getMapWidthForAccueil() {
		return getProperty("mapWidthForAccueil");
	}

	@Override
	public String getMapHeightForAccueil() {
		return getProperty("mapHeightForAccueil");
	}

	@Override
	public int getImageThumbnailSize() {
		return Integer.parseInt(getProperty("imageThumbnailSize"));
	}

	@Override
	public int getImageMediumSize() {
		return Integer.parseInt(getProperty("imageMediumSize"));
	}

	@Override
	public int getImageDetailSize() {
		return Integer.parseInt(getProperty("imageDetailSize"));
	}

	@Override
	public int getEditListTableWidth() {
		return Integer.parseInt(getProperty("editListTableWidth"));
	}

	public ThemeName getThemeName() {
		return themeName;
	}

}
