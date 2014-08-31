package fr.bekkers.galerie.client.jsobject;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.json.client.JSONObject;

//w00t! Generics work just fine with overlay types
public class JsArray<E extends JavaScriptObject> extends com.google.gwt.core.client.JsArray<E> {

	// Overlay types always have protected, zero argument constructors.
	protected JsArray() {
	}

	// Safely evaluate the JSON payload and create the object instance.
	public static JsArray<?> create(String json) {
		return JsonUtils.<JsArray<?>> safeEval(json);
	}

	public final String toJson() {
		return new JSONObject(this).toString();
	}


}
