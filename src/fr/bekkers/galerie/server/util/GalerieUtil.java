package fr.bekkers.galerie.server.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;

import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

public class GalerieUtil {

	static Logger logger = Logger.getLogger(GalerieUtil.class.getName());

	public static String StackTraceAsString(Throwable caught) {
		StringWriter sw = new StringWriter();
		PrintWriter writter = new PrintWriter(sw);
		caught.printStackTrace(writter);
		return sw.toString();
	}

//	public static void reloadGalerie(ServletContext context) throws GalerieException {
//		String tempFileName = Constants.APPLICATION_FILE_PATH+Constants.GALERIE_XML_TEMP_FILE;
//		logger.info("reloading = " + Constants.GALERIE_XML_TEMP_FILE);
//		try {
//			FileWriter writer = null;
//			try {
//				writer = new FileWriter(tempFileName);
//			} catch (NullPointerException e) {
//				String msg = "problèmes pour écrire dans le fichier "+tempFileName;
//				logger.severe(msg);
//				throw new GalerieException(msg);
//			}
//			String encoding = "UTF-8";
//			InputStream is = context.getResourceAsStream(Constants.GALERIE_XML_MAIN_FILE);
//			if (is==null) {
//				writer.close();
//				String msg = "problèmes pour lire dans le fichier "+Constants.GALERIE_XML_TEMP_FILE;
//				logger.severe(msg);
//				throw new GalerieException(msg);
//			}
//			IOUtils.copy(is, writer, encoding);
//			writer.close();
//		} catch (IOException e) {
//			logger.severe("Impossible de restaurer le fichier '"+
//					tempFileName+"' : " + e.getMessage());
//			throw new GalerieException(e);
//		}
//	}

}
