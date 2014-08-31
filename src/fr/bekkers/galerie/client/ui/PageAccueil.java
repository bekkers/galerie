package fr.bekkers.galerie.client.ui;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.client.ui.util.MapContainer;
import fr.bekkers.galerie.shared.GalerieException;

public class PageAccueil extends Composite {

	private static Logger logger = Logger.getLogger(PageAccueil.class.getName());

	private final Button go = new Button("Consulter");
	private final Button edit = new Button("Editer");
	private final MapContainer map;

	public PageAccueil() {
		super();
		VerticalPanel mainVp = new VerticalPanel();
		this.initWidget(mainVp);
		
		mainVp.setWidth("100%");
		mainVp.setHeight("100%");
		mainVp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainVp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		mainVp.add(new HTML("<h1>Bienvenu sur la galerie d'Yves Bekkers</h1>"));
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		mainVp.add(vp);
			map = new MapContainer(
					Galerie.getProps().getTheme().getMapWidthForAccueil(),
					Galerie.getProps().getTheme().getMapHeightForAccueil());
		vp.add(map);
		HorizontalPanel hp = new HorizontalPanel();
		vp.add(hp);
		hp.setSpacing(15);
		hp.add(go);
		go.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.showList(Pages.LISTE_AQUARELLES);
			}
		});
		hp.add(edit);
		edit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.showEditList();
			}
		});
		Controller.getGps();
	}

	public void setMarkers(String[] gps) throws GalerieException {
		map.setMarkers(gps);
	}
}