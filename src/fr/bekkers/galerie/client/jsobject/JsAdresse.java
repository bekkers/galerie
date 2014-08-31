package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JsonUtils;

// this an overlay type
public class JsAdresse extends JsGalerieObject {


	// Overlay types always have protected, zero argument constructors.
	protected JsAdresse() {
	}

	// Safely evaluate the JSON payload and create the object instance.
	public static JsAdresse create(String json) {
		return JsonUtils.<JsAdresse> safeEval(json);
	}

	// JSNI methods to get data.
	public final native String getCodePostal() /*-{ return this.codePostal; }-*/;

	public final native String getVille() /*-{ return this.ville; }-*/;

	public final native String getVoie() /*-{ return this.voie; }-*/;

	public final native String getPays() /*-{ return this.pays; }-*/;

}
