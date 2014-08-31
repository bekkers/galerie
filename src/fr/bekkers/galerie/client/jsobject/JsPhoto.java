package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JsonUtils;

public class JsPhoto extends JsGalerieObject {


	// Overlay types always have protected, zero argument constructors.
	protected JsPhoto() {
	}

	// Safely evaluate the JSON payload and create the object instance.
	public static JsPhoto create(String json) {
		return JsonUtils.<JsPhoto> safeEval(json);
	}


	// JSNI methods to get (and set) data.
	public final native String getName() /*-{
		return this.name;
	}-*/;

	public native final void setName(String name)/*-{
		this.name = name;
	}-*/;


}
