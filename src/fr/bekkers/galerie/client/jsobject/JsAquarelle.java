package fr.bekkers.galerie.client.jsobject;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.JsonUtils;

import fr.bekkers.galerie.client.ClientDateConverter;

public class JsAquarelle extends JsGalerieObject {

	private static Logger logger = Logger
			.getLogger(JsAquarelle.class.getName());


	// Overlay types always have protected, zero argument constructors.
	protected JsAquarelle() {
	}

	// Safely evaluate the JSON payload and create the object instance.
	public static JsAquarelle create(String json) {
		logger.log(Level.INFO, "json=\n" + json);
		return JsonUtils.<JsAquarelle> safeEval(json);
	}

	public static native final JsAquarelle create()/*-{
													return {};
													}-*/;

	// JSNI methods to get (and set) data.
	public final native String getName() /*-{
											return this.name;
											}-*/;

	public native final void setName(String name)/*-{
													this.name = name;
													}-*/;

	public final native int getId() /*-{
										return this.id;
										}-*/;

	public final native String getTitle() /*-{
											return this.title;
											}-*/;

	public native final void setTitle(String title)/*-{
													this.title = title;
													}-*/;

	public final Date getDate() {
		return ClientDateConverter.read(getDateAsString());
	}

	public final native String getDateAsString() /*-{
													return this.date;
													}-*/;

	public final void setDate(Date date) {
		setDateAsString(ClientDateConverter.write(date));
	};

	public final native void setDateAsString(String s) /*-{
		this.date = s;
	}-*/;

	public final native String getGps() /*-{
										return this.gps;
										}-*/;

	public native final void setGps(String gps)/*-{
												this.gps = gps;
												}-*/;

	public final native JsAdresse getAdresse() /*-{
												return this.adresse;
												}-*/;

	public native final void setAdresse(JsAdresse adresse)/*-{
															this.adresse = adresse;
															}-*/;
	
	// Return the whole JSON array, as is
	public final native JsArray<JsPhoto> getPhoto() /*-{
	    return this.photo;
	  }-*/;

	public final native void setPhoto(JsArray<JsPhoto>  photos) /*-{
	    this.photo = photos;
	  }-*/;

}
