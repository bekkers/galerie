package fr.bekkers.galerie.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class GalerieException extends Exception implements IsSerializable {

	public GalerieException() {
	}

	public GalerieException(String message) {
		super(message);
	}

	public GalerieException(Exception e) {
		super(e);
	}

	public GalerieException(String message, Exception e) {
		super(message, e);
	}

}
