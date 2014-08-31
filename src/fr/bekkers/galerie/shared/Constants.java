package fr.bekkers.galerie.shared;



public class Constants {
	public static final String DATE_FORMAT_STRING = "yyyy-MM-dd";
	public static final String GALERIE_XML_MAIN_FILE = "galerie.xml";
	public static final String GALERIE_XML_TEMP_FILE = 
			"/WEB-INF/xml/"+GALERIE_XML_MAIN_FILE;
	// TODO bug jaxb context
	public static final String GALERIE_JAXB_CONTEXT = "fr.bekkers.galerie.server.domain";
	public static final String LISTE_DES_OEUVRES = "listeDesOeuvres";
	public static final String RETOUR_RCP_EN_ERREUR = "Retour RCP en erreur";
	
	public static String APPLICATION_FILE_PATH;

	
	public static final String TEMP_PATH = "WEB-INF"+"/temp";
	public static final String IMAGE_PATH = "images";
	public static final String IMAGE_FULL_SIZE_PATH = IMAGE_PATH+"/fullSize";
	public static final String IMAGE_PHOTO_PATH = IMAGE_PATH+"/photo";
	
	public static final int SLIDE_SHOW_DELAY = 4000;
	public static final double MEDIUM_COUNTRY_ZOOM = 6;
	public static final String SPACE = " ";
	public static final String VIRG = ",";
	

}
