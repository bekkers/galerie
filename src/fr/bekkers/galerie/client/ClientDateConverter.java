package fr.bekkers.galerie.client;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

import fr.bekkers.galerie.shared.Constants;

public class ClientDateConverter {

	public static final DateTimeFormat dtf = DateTimeFormat
			.getFormat(Constants.DATE_FORMAT_STRING);

	public static Date read(String d) {
		return new Date(dtf.parse(d).getTime());
	}
	
	public static String write(Date date) {
		return dtf.format(date);
	}

}
