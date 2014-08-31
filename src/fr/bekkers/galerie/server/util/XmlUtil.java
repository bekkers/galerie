package fr.bekkers.galerie.server.util;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fr.bekkers.galerie.server.domain.Aquarelle;
import fr.bekkers.galerie.server.domain.Galerie;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

public class XmlUtil {

	static Logger logger = Logger.getLogger(XmlUtil.class.getName());

	public static Galerie read(File file) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Galerie.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return (Galerie) unmarshaller.unmarshal(file);
	}

	public static Galerie read(InputStream in) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Galerie.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return (Galerie) unmarshaller.unmarshal(in);
	}

	public static void reloadGalerie()
			throws GalerieException, JAXBException {
		logger.info("reloading = " + Constants.GALERIE_XML_MAIN_FILE);
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream is = classLoader
				.getResourceAsStream(Constants.GALERIE_XML_MAIN_FILE);
		if (is == null) {
			String msg = "impossible d'obtenir le fichier "
					+ Constants.GALERIE_XML_MAIN_FILE+ " à partir du classpath";
			logger.severe(msg);
			throw new GalerieException(msg);
		}
		Galerie galerie = read(is);
		logger.info("file " + Constants.GALERIE_XML_TEMP_FILE + " is reloaded");

		String tempFileName = Constants.APPLICATION_FILE_PATH
				+ Constants.GALERIE_XML_TEMP_FILE;
		write(galerie, new File(tempFileName));
	}

	// public static Galerie load(String fileName) throws JAXBException {
	// InputStream in = XmlUtil.class.getClassLoader().getResourceAsStream(
	// fileName);
	// return read(in);
	// }
	//
	//
	// public static void store(Galerie galerie, String fileName)
	// throws JAXBException, URISyntaxException, IOException {
	// URL url = XmlUtil.class.getClassLoader().getResource(fileName);
	// OutputStream out = new FileOutputStream(new File(url.toURI()));
	// write(galerie, out);
	// out.close();
	// }

	public static Aquarelle readAquarelleFromString(String xmlString)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Aquarelle.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(xmlString);
		return (Aquarelle) unmarshaller.unmarshal(reader);
	}

	public static String toString(Galerie galerie) {
		StringBuffer buff = new StringBuffer();
		for (Aquarelle aquarelle : galerie.getAquarelle()) {
			buff.append(toString(aquarelle));
			buff.append("\n");
		}
		return buff.toString();
	}

	public static String toString(Aquarelle aquarelle) {

		return aquarelle.getName();
	}

	public static void write(Galerie galerie, File galerieFile)
			throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Galerie.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.marshal(galerie, galerieFile);
	}

	// public static void main(String[] args) {
	// try {
	// Galerie galerie = load(Constants.GALERIE_XML_FILE);
	// System.out.println(XmlUtil.toString(galerie));
	// write(galerie, new File("galerie.out.xml"));
	// System.out.println(JsonUtil.toJson(galerie));
	// Aquarelle aquarelleServer = galerie.getAquarelle().get(0);
	// String aquarelleAsJsonString = JsonUtil.toJson(aquarelleServer);
	// System.out.println(aquarelleAsJsonString);
	//
	// Adresse adresseServer = aquarelleServer.getAdresse();
	// Gson gson = new Gson();
	// String adresseAsString = gson.toJson(adresseServer);
	// System.out.println(adresseAsString);
	//
	// } catch (JAXBException e) {
	// e.printStackTrace();
	// }
	// }
}
