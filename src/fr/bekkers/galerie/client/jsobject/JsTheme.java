package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JsonUtils;

import fr.bekkers.galerie.shared.Theme;

//this an overlay type
public class JsTheme extends JsGalerieObject implements Theme {

	// Overlay types always have protected, zero argument constructors.
	protected JsTheme() {
	}
	
	// Safely evaluate the JSON payload and create the object instance.
	public static Theme create(String json) {
		return JsonUtils.<JsTheme> safeEval(json);
	}

	// JSNI methods to get data.
	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getMapWidthForAccueil()
	 */
	@Override
	public final native String getMapWidthForAccueil() /*-{ return this.mapWidthForAccueil; }-*/;

	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getMapHeightForAccueil()
	 */
	@Override
	public final native String getMapHeightForAccueil() /*-{ return this.mapHeightForAccueil; }-*/;

	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getImageThumbnailSize()
	 */
	@Override
	public final native int getImageThumbnailSize() /*-{ return this.imageThumbnailSize; }-*/;
	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getImageMediumSize()
	 */
	@Override
	public final native int getImageMediumSize() /*-{ return this.imageMediumSize; }-*/;
	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getImageDetailSize()
	 */
	@Override
	public final native int getImageDetailSize() /*-{ return this.imageDetailSize; }-*/;

	/* (non-Javadoc)
	 * @see fr.bekkers.galerie.client.jsobject.Theme#getEditListTableWidth()
	 */
	@Override
	public final native int getEditListTableWidth() /*-{ return this.editListTableWidth; }-*/;
}
