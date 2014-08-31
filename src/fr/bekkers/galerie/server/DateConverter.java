package fr.bekkers.galerie.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.bekkers.galerie.shared.Constants;

// converter for XML serialization
public class DateConverter {
	
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_STRING);


	public static Date read(String d) {
		Date date = null;

		// parse String datetime to Date
		try {
			date = DATE_FORMAT.parse(d);
			System.out.println("time entered: " + date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String write(Date date) {
		// format the Date object then assigns to String
		return DATE_FORMAT.format(date);
	}
}
