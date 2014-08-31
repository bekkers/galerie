package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JsonUtils;

//this an overlay type
public class JsProps extends JsGalerieObject {

	// Overlay types always have protected, zero argument constructors.
	protected JsProps() {
	}

	// Safely evaluate the JSON payload and create the object instance.
	public static JsProps create(String json) {
		return JsonUtils.<JsProps> safeEval(json);
	}

	// JSNI methods to get data.
	public final native JsTheme getTheme() /*-{ return this.theme; }-*/;

	public final native String getContextUrl() /*-{ return this.CONTEXT_URL; }-*/;


	public final String getFullImageUrl(String name) {
		String fullImageURL = getContextUrl() + "/images/fullSize/"
				+ name + ".jpg";
		return fullImageURL;
	}
}
