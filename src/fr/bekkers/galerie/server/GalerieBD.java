package fr.bekkers.galerie.server;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import fr.bekkers.galerie.server.domain.Aquarelle;
import fr.bekkers.galerie.server.domain.Galerie;
import fr.bekkers.galerie.server.util.JsonUtil;
import fr.bekkers.galerie.server.util.XmlUtil;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

public class GalerieBD {

	private static Logger logger = Logger.getLogger(GalerieBD.class.getName());

	public static Galerie getGalerie(ServletContext context)
			throws GalerieException {
		Galerie galerie = null;
		galerie = (Galerie) context
				.getAttribute(Constants.LISTE_DES_OEUVRES);
		String fname = Constants.GALERIE_XML_TEMP_FILE;
		if (galerie == null) {
			try {
				logger.info("chargement de la galerie1");
				galerie = XmlUtil.read(context.getResourceAsStream(fname));
				logger.info("chargement de la galerie nbOeuvres = "
						+ (galerie == null ? "null" : galerie.getAquarelle()
								.size()));
				logger.info(JsonUtil.toJson(galerie));
				context.setAttribute(Constants.LISTE_DES_OEUVRES, galerie);
			} catch (JAXBException e) {
				String mess = "Chargement de " + fname + " impossible : "
						+ e.getMessage();
				logger.warning(mess);
				throw new GalerieException(e);
			}

		}
		return galerie;
	}

	public static void replaceAquarelle(ServletContext application,
			Aquarelle newAquarelle) throws GalerieException {
		Galerie galerie = null;
		synchronized (application) {
			galerie = getGalerie(application);
			List<Aquarelle> aquarelles = galerie.getAquarelle();
			int id = newAquarelle.getId();
			Aquarelle aquarelle = getAquarelleById(id, application);
			aquarelles.remove(aquarelle);
			aquarelles.add(newAquarelle);
			application.setAttribute(Constants.LISTE_DES_OEUVRES,
					galerie);
		}
	}

	public static void createAquarelle(ServletContext application,
			Aquarelle aquarelle) throws GalerieException {
		Galerie galerie = null;
		synchronized (application) {
			galerie = getGalerie(application);
			int id = galerie.getNextId();
			galerie.setNextId(id+1);
			int year = new GregorianCalendar().get(Calendar.YEAR);
			aquarelle.setId(id);
			galerie.getAquarelle().add(aquarelle);
			logger.log(Level.INFO,
					"chargement de la galerie nbOeuvres = "
							+ (galerie == null ? "null" : galerie
									.getAquarelle().size()));
			logger.info(JsonUtil.toJson(galerie));
			application.setAttribute(Constants.LISTE_DES_OEUVRES, galerie);
			try {
				File galerieFile = new File(Constants.APPLICATION_FILE_PATH+Constants.GALERIE_XML_TEMP_FILE);
				XmlUtil.write(galerie, galerieFile);
			} catch (JAXBException e) {
				throw new GalerieException(
						"impossible de sauver le document xml : "
								+ e.getMessage());
			}
		}
	}

	public static Galerie saveAquarelle(ServletContext application,
			Aquarelle aquarelle) throws GalerieException {
		Galerie galerie = null;
		synchronized (application) {
			galerie = getGalerie(application);
			galerie.getAquarelle().add(aquarelle);
			logger.info("chargement de la galerie, nbOeuvres = "
							+ (galerie == null ? "null" : galerie
									.getAquarelle().size()));
			logger.info(JsonUtil.toJson(galerie));
			application.setAttribute(Constants.LISTE_DES_OEUVRES, galerie);
			try {
				File galerieFile = new File(Constants.APPLICATION_FILE_PATH+Constants.GALERIE_XML_TEMP_FILE);
				XmlUtil.write(galerie, galerieFile);
				return galerie;
			} catch (JAXBException e) {
				StringBuffer buf = new StringBuffer("JAXBException : impossible de sauver le document xml");
				if (e.getMessage()!=null) {
					buf.append(" mess = ").append(e.getMessage());
				}
				Throwable cause = e.getCause();
				if (cause != null && cause.getMessage()!=null) {
					buf.append(" cause = ").append(cause.getMessage());
				}
				String mess = buf.toString();
				logger.severe(mess);
				throw new GalerieException(mess, e);
			}
		}
	}

	public static Galerie deleteAquarelle(ServletContext application, int id)
			throws GalerieException {
		Galerie galerie = null;
		synchronized (application) {
			galerie = getGalerie(application);

			List<Aquarelle> aquarelles = galerie.getAquarelle();
			Aquarelle aquarelle = getAquarelleById(id, application);
			aquarelles.remove(aquarelle);
			application.setAttribute(Constants.LISTE_DES_OEUVRES,
					galerie);
			return galerie;
		}
	}

	public static Galerie getGalerie(ServletContext application, boolean b)
			throws GalerieException {
		if (b == true) {
			application.setAttribute(Constants.LISTE_DES_OEUVRES, null);
		}
		return getGalerie(application);
	}

	public static Aquarelle getAquarelleById(int id, ServletContext application)
			throws GalerieException {
		Galerie galerie = GalerieBD.getGalerie(application);
		List<Aquarelle> aquarelles = galerie.getAquarelle();
		Aquarelle foundAquarelle = null;
		for (Aquarelle aquarelle : aquarelles) {
			if (aquarelle.getId()==id) {
				logger.info("retreiving aquarelle = " + aquarelle.getName());
				foundAquarelle = aquarelle;
			}
		}
		if (foundAquarelle == null) {
			String mess = "Il n'y a pas d'aquarelle d'identification : " + id;
			logger.warning(mess);
			throw new GalerieException(mess);
		}
		return foundAquarelle;
	}

}
