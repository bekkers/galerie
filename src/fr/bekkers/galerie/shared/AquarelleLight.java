package fr.bekkers.galerie.shared;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class AquarelleLight implements Serializable, IsSerializable {
	private String name;
	private String gps;
	private String date;
	private int id;
	private String title;

	public AquarelleLight() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date1) {
		this.date = date1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
