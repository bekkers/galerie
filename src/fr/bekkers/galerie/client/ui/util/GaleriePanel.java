package fr.bekkers.galerie.client.ui.util;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.ui.Pages;
import fr.bekkers.galerie.shared.GalerieException;

public abstract class GaleriePanel<T> extends Composite {

	protected Logger logger;

	private final HorizontalPanel hp = new HorizontalPanel();
	private final Anchor anchorHome = new Anchor("Home");
	private HTML description;

	protected VerticalPanel mainGalerieVp;

	protected GaleriePanel(String title1) {
		super();
		description = new HTML(title1);
		mainGalerieVp = new VerticalPanel();
		mainGalerieVp.setWidth("100%");
		mainGalerieVp.setHeight("100%");
		mainGalerieVp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainGalerieVp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		this.initWidget(mainGalerieVp);
		mainGalerieVp.add(description);
		mainGalerieVp.add(hp);
		hp.setStylePrimaryName("topRight");
		anchorHome.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.show(Pages.ACCUEIL);
			}
		});
		hp.setSpacing(20);
		hp.add(anchorHome);
	}

	public abstract void init(T listAquarelles) throws GalerieException;

	public void addAnchor(Anchor anchor) {
		hp.add(anchor);
	}
	
	protected void setDescription(String title1) {
		description.setHTML(title1);
	}

}
