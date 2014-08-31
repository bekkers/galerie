package fr.bekkers.galerie.shared;

public interface Theme {

	// JSNI methods to get data.
	public abstract String getMapWidthForAccueil() /*-{ return this.mapWidthForAccueil; }-*/;

	public abstract String getMapHeightForAccueil() /*-{ return this.mapHeightForAccueil; }-*/;

	public abstract int getImageThumbnailSize() /*-{ return this.imageThumbnailSize; }-*/;

	public abstract int getImageMediumSize() /*-{ return this.imageMediumSize; }-*/;

	public abstract int getImageDetailSize() /*-{ return this.imageDetailSize; }-*/;

	public abstract int getEditListTableWidth() /*-{ return this.editListTableWidth; }-*/;

}