package fr.bekkers.galerie.server.util;

import java.util.logging.Logger;

import fr.bekkers.galerie.shared.GalerieException;

public class ServerProps {

	private static Logger logger = Logger.getLogger(ServerProps.class
			.getName());

	private final ServerTheme theme;
	private final String CONTEXT_URL;
	
	public ServerProps(String CONTEXT_URL1, String themeName) throws GalerieException {
		logger.info("debut");
		this.theme = ServerTheme.getThemes().get(themeName);
		logger.info("theme = "+theme);
		if (theme == null) {
			String msg = "ServerTheme "+themeName+" is null";
			logger.severe(msg);
			throw new GalerieException(msg);
		}
		this.CONTEXT_URL = CONTEXT_URL1;
		logger.info("url = "+CONTEXT_URL+", imageMediumSize = "+theme.getImageMediumSize());
	}

	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.server.util.Props#getThemes()
	 */
	public ServerTheme getThemes() {
		return theme;
	}

	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.server.util.Props#getCONTEXT_URL()
	 */
	public String getCONTEXT_URL() {
		return CONTEXT_URL;
	}


}
