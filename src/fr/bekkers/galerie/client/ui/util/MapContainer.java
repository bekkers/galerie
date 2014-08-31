package fr.bekkers.galerie.client.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.GoogleMap.TilesLoadedHandler;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerImage;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.OverviewMapControlOptions;

import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

public class MapContainer extends SimplePanel {

	private static Logger logger = Logger.getLogger(MapContainer.class
			.getName());

	private GoogleMap googleMap;
	private List<Marker> markerList = new ArrayList<Marker>();

	private MapOptions options;

	// TODO essayer d'ouvrir une fenêtre avec ces URL
	// private static final String URL1 =
	// "<iframe src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d2663.4431692814455!2d-1.665246!3d48.120976399999996!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x480ede44da944de9%3A0x9c93d7a2ae4cba18!2s135+Rue+de+Foug%C3%A8res%2C+35700+Rennes!5e0!3m2!1sfr!2sfr!4v1407705237671\" width=\"400\" height=\"300\" frameborder=\"0\" style=\"border:0\"></iframe>";
	// private static final String URL2 =
	// "https://www.google.fr/maps/place/135+Rue+de+Foug%C3%A8res,+35700+Rennes/@48.1209764,-1.665246,17z/data=!3m1!4b1!4m2!3m1!1s0x480ede44da944de9:0x9c93d7a2ae4cba18";

	public MapContainer(String largeur, String hauteur) {
		this(largeur, hauteur, false);
	}

	public MapContainer(String largeur, String hauteur, MapOptions options) {
		init(largeur, hauteur, options);
	}

	public MapContainer(String largeur, String hauteur, Boolean overview) {
		options = MapOptions.create();

		options.setMapTypeId(MapTypeId.ROADMAP);
		options.setDraggable(true);
		options.setMapTypeControl(true);
		options.setScaleControl(true);
		options.setScrollwheel(true);
		options.setStreetViewControl(true);

		if (overview == true) {
			options.setOverviewMapControl(true);
			OverviewMapControlOptions overviewMapControlOptions = OverviewMapControlOptions
					.create();
			overviewMapControlOptions.setOpened(true);
			options.setOverviewMapControlOptions(overviewMapControlOptions);
		}
		init(largeur, hauteur, options);
	}

	private void init(String largeur, String hauteur, MapOptions options) {
		this.setSize(largeur, hauteur);

		// On ajoute la carte dans le simple panel
		googleMap = GoogleMap.create(this.getElement(), options);
		logger.info("map created");
	}

	public void setMarkers(String[] gps) throws GalerieException {
		setMarkers(gps, false);
	}

	public void setMarkers(String[] gps, boolean changeIcons)
			throws GalerieException {
		final GoogleMap noMap = null;
		for (Marker marker : markerList) {
			marker.setVisible(false);
			marker.setMap(noMap);
		}
		markerList.clear();

		// on pose les markers
		if (!(gps.length == 0)) {
			final HashMap<String, LatLng> markers = createLatLng(gps);

			final LatLngBounds latLngBounds = LatLngBounds.create();

			for (String key : markers.keySet()) {
				LatLng latLng = markers.get(key);
				final Marker marker = putMakerOnMap(latLng, key);

				// TODO bug google map Le titre du marker n'apparaît que lorsque
				// l'on vient de rentrer dans la vue google map
				marker.setTitle(key);

				if (changeIcons) {
					String imageURL = Galerie.getProps().getContextUrl()
							+ "/thumbnail?name=" + key + "&size=" + 40;
					marker.setIcon(MarkerImage.create(imageURL));
				}

				latLngBounds.extend(marker.getPosition());
			}

			final int length = gps.length;
			if (length == 1) {
				googleMap.setZoom(15);
			} else {
				googleMap.fitBounds(latLngBounds);
			}
			googleMap.setCenter(latLngBounds.getCenter());

			googleMap.addTilesLoadedListenerOnce(new TilesLoadedHandler() {
				// ceci est un hack pour corriger les pbs de centrage incorrect
				// (center appears wrongly on top/left) et de chargement
				// incomplet des tiles d'une googleMap
				@Override
				public void handle() {
					googleMap.triggerResize();
					if (length == 1) {
						googleMap.setZoom(15);
						googleMap.setCenter(latLngBounds.getCenter());
					} else {
						googleMap.setZoom(googleMap.getZoom());
						googleMap.fitBounds(latLngBounds);
					}
				}
			});

		}
	}

	private HashMap<String, LatLng> createLatLng(String[] gps)
			throws GalerieException {
		HashMap<String, LatLng> markers = new HashMap<String, LatLng>();
		for (String mark : gps) {

			LatLng position = null;
			try {
				logger.info("gps = " + Arrays.toString(gps));
				String[] marker = mark.split(Constants.SPACE);
				if (marker == null || marker.length != 2) {
					String msg = "marker gps incorrect : " + gps;
					logger.warning(msg);
					throw new GalerieException(msg);
				}
				String title = marker[1];
				String[] latLng = marker[0].split(Constants.VIRG);
				if (latLng == null || latLng.length != 2) {
					String msg = "position gps incorrecte : " + gps;
					logger.warning(msg);
					throw new GalerieException(msg);
				}
				double lattitude = Double.parseDouble(latLng[0]);
				double longitude = Double.parseDouble(latLng[1]);
				logger.info("parsed lattitude = " + Double.toString(lattitude)
						+ ", longitude = " + Double.toString(longitude));

				position = LatLng.create(lattitude, longitude);
				logger.info("created");
				markers.put(title, position);
			} catch (NumberFormatException e) {
				String msg = "position gps incorrecte : " + gps;
				logger.warning(msg);
				throw new GalerieException(msg);
			}
		}
		return markers;
	}

	private Marker putMakerOnMap(LatLng latLng, String title) {
		Marker marker = putMakerOnMap(latLng);
		marker.setTitle(title);
		return marker;
	}

	private Marker putMakerOnMap(LatLng latLng) {
		final MarkerOptions markerOptions = MarkerOptions.create();
		markerOptions.setPosition(latLng);
		markerOptions.setMap(googleMap);
		Marker marker = Marker.create(markerOptions);
		markerList.add(marker);

		return marker;
	}

}
