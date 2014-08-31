package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

public abstract class JsGalerieObject  extends JavaScriptObject {
	

	// Overlay types always have protected, zero argument constructors.
	protected JsGalerieObject() {
	}
	

	public final String toJson() {
		return new JSONObject(this).toString();
	}
	
}
