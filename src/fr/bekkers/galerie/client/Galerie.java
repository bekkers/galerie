package fr.bekkers.galerie.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.RootPanel;

import fr.bekkers.galerie.client.jsobject.JsProps;
import fr.bekkers.galerie.shared.ThemeName;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Galerie implements EntryPoint {

	private static Logger logger = Logger.getLogger(Galerie.class.getName());

	private static final GalerieServiceAsync galerieService = GWT
			.create(GalerieService.class);
	
	private static JsProps props;

	static public DeckPanel deckPanel = new DeckPanel();
		
	@Override
	public void onModuleLoad() {
		ServiceDefTarget serviceDef = (ServiceDefTarget) galerieService;
		serviceDef.setServiceEntryPoint(GWT.getModuleBaseURL()
				+ "galerieService");
		final int CLIENT_HEIGHT = Window.getClientHeight();
		final int CLIENT_WIDTH = Window.getClientWidth();
		ThemeName themeName;
		if (CLIENT_WIDTH > 1000) {
			themeName = ThemeName.largeTheme;
		} else if (CLIENT_WIDTH > 500) {
			themeName = ThemeName.mediumTheme;
		} else {
			themeName = ThemeName.smallTheme;
		}
		logger.info("client theme = "+themeName.name()+", screen size = "+CLIENT_HEIGHT+", "+CLIENT_WIDTH);
		RootPanel.get().add(deckPanel);
		deckPanel.setWidth("100%");
		deckPanel.setHeight("100%");
		logger.log(Level.INFO, "on demarre");
		Controller.startGalerie(themeName);
	}

	public static GalerieServiceAsync getGalerieService() {
		return galerieService;
	}

	public static void addPage(Composite panel) {
		deckPanel.add(panel);
	}

	public static JsProps getProps() {
		return props;
	}

	public static void setProps(JsProps props) {
		
		Galerie.props = props;
		logger.info("props = "+props.toJson());
	}

}
