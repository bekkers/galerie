package fr.bekkers.galerie.client.ui;

import java.util.logging.Logger;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.client.jsobject.JsAdresse;
import fr.bekkers.galerie.client.jsobject.JsAquarelle;
import fr.bekkers.galerie.client.jsobject.JsArray;
import fr.bekkers.galerie.client.jsobject.JsPhoto;
import fr.bekkers.galerie.client.ui.util.GaleriePanel;
import fr.bekkers.galerie.client.ui.util.MapContainer;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

public class PageDetailAquarelle extends GaleriePanel<JsAquarelle> {

	// TODO gérer la taille des photos en % de la taille de la page

	private class SileShow implements RepeatingCommand {

		public SileShow(int size) {
			nbImages = size;
		}

		private boolean go = true;
		private int current = 0;
		private int nbImages;

		@Override
		public boolean execute() {
			if (go) {
				current = (current + 1) % nbImages;
				table.setWidget(0, 1, images[current]);
			}
			return go;
		}

		public void stop() {
			go = false;
		}
	}

	private static SileShow slideShow;
	private final Anchor anchor = new Anchor("Retour au mode consultation");
	private FlexTable table = new FlexTable();
	private HTML address = new HTML("<em>... adresse ici ... </em>");
	private Image[] images;
	private MapContainer mainMapContainer;
//	private OverviewMapContainer overviewMapContainer;
	private int slideCol;

	public PageDetailAquarelle() {
		super("no Title");
		logger = Logger.getLogger(PageDetailAquarelle.class.getName());
		mainGalerieVp.add(address);
		mainGalerieVp.add(table);
		int largeur = Galerie.getProps().getTheme().getImageDetailSize()*2;
		mainMapContainer = new MapContainer(largeur+"px", Galerie.getProps().getTheme().getImageDetailSize()+"px", true);
//		overviewMapContainer = new OverviewMapContainer("500px", "300px");
		mainGalerieVp.add(mainMapContainer);

		anchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.show(Pages.LISTE_AQUARELLES);
			}
		});
		addAnchor(anchor);
	}

	public void init(final JsAquarelle aquarelle) throws GalerieException {
		mainMapContainer.setVisible(false);
		logger.info("init PageDetail : " + aquarelle.toJson());
		slideCol = 1;

		table.clear();
		if (slideShow != null) {
			slideShow.stop();
		}

		setDescription(aquarelle.getTitle());
		JsAdresse adressejs = aquarelle.getAdresse();
		logger.info("adresse = " + adressejs);
		if (adressejs == null) {
			address.setHTML("<em>No address for this one</em>");
		} else {
			String villeName = adressejs.getVille();
			String codePostal = adressejs.getCodePostal();
			// le code postal n'est pas obligatoire
			String ville = !(codePostal == null || codePostal.length() == 0 || codePostal
					.equals("undefined")) ? codePostal + ", " + villeName
					: villeName;

			address.setHTML("<em>" + adressejs.getVoie() + ", " + ville + ", "
					+ adressejs.getPays() + "</em>");
		}
		String imageURL = Galerie.getProps().getContextUrl() + "/thumbnail?name="
				+ aquarelle.getName() + "&size=" + Galerie.getProps().getTheme().getImageDetailSize();
		logger.info("imageURL = " + imageURL);
		Image image = new Image(imageURL);
		image.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String name = aquarelle.getName();
				String fullImageURL = Galerie.getProps().getFullImageUrl(name);
				logger.info("fullImageURL = " + fullImageURL);
//				popupImage(fullImageURL);
				getURL(fullImageURL);
			}

		});
		table.setWidget(0, 0, image);
		showPhotos(aquarelle.getPhoto(), aquarelle.getName());

		String gps = aquarelle.getGps();
		if (gps != null && gps.length() != 0 && !gps.equals("undefined")) {
			showGps(gps + Constants.SPACE + aquarelle.getName());
		}
	}
	
	private static native String getURL(String url)/*-{
	return $wnd.open(url,
	'target=_blank')
	}-*/;
	
//    private void popupImage(String url) {
//        final PopupPanel popupImage = new PopupPanel();
//        popupImage.setAutoHideEnabled(true);
//        popupImage.setStyleName("popupImage"); /* Make image fill 90% of screen */
//
//        final Image image = new Image(url);
//		image.addLoadHandler(new LoadHandler() {
//		    @Override
//		    public void onLoad(LoadEvent event) {
//		    	logger.info("chargé");
//		        // since the image has been loaded, the dimensions are known
//		        popupImage.center(); 
//		        // only now show the image
//		        popupImage.setVisible(true);
//		    }
//		 });
//
//		 popupImage.add(image);
//		 // hide the image until it has been fetched
//		 popupImage.setVisible(false);
//		 // this causes the image to be loaded into the DOM
//		 popupImage.show();
//	}

	public void showGps(String gps) throws GalerieException {
		logger.info("gps = " + gps);
		if (gps != null && gps.length() != 0 && !gps.equals("undefined")) {
			String[] gpsArray = { gps };
			mainMapContainer.setVisible(true);
			mainMapContainer.setMarkers(gpsArray);
		}
	}

	private void showPhotos(JsArray<JsPhoto> photos, String name) {
		if (!(photos == null)) {
			final int size = photos.length();
			if (size != 0) {
				slideShow = new SileShow(size);

				images = new Image[size];
				for (int i = 0; i < images.length; i++) {
					String imageURL = Galerie.getProps().getContextUrl()
							+ "/thumbnail?name=" + name + "&photo="
							+ photos.get(i).getName() + "&size="
							+ Galerie.getProps().getTheme().getImageDetailSize();
					images[i] = new Image(imageURL);
				}
				table.setWidget(0, slideCol, images[0]);
				Scheduler.get().scheduleFixedDelay(slideShow,
						Constants.SLIDE_SHOW_DELAY);
			}
		}
	}
}
