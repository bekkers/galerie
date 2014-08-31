package fr.bekkers.galerie.client.ui;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.client.ui.util.GaleriePanel;
import fr.bekkers.galerie.shared.AquarelleLight;

public class PageListeAquarelle extends GaleriePanel<AquarelleLight[]> {

	private AquarelleLight[] listAquarelles;
	private final FlexTable table = new FlexTable();
	private final Anchor anchor = new Anchor("Mode édition");



	public PageListeAquarelle() {
		super("Liste des Oeuvres");
		logger = Logger
				.getLogger(PageListeAquarelle.class.getName());
		mainGalerieVp.add(table);
		anchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.showEditList(listAquarelles);
			}
		});
		addAnchor(anchor);
	}

	private class ShowAquarelleHandler implements ClickHandler {
		int id;

		ShowAquarelleHandler(int id1) {
			super();
			this.id = id1;
		}

		@Override
		public void onClick(ClickEvent event) {
			Controller.showAquarelle(id);

		}
	}

	public void init(AquarelleLight[] result) {
		listAquarelles = result;
		table.clear();
		
		for (int i = 0; i < result.length; i++) {
			AquarelleLight aquarelle = result[i];
			String imageURL = Galerie.getProps().getContextUrl()+"/thumbnail?name="+ aquarelle.getName()+"&size="+Galerie.getProps().getTheme().getImageMediumSize();
			logger.info("URL = "+imageURL);
			Image image = new Image(imageURL);
			image.addClickHandler(new ShowAquarelleHandler(aquarelle
					.getId()));

			FlexTable aquaTable = new FlexTable();
			int numCol = i % Galerie.getProps().getTheme().getEditListTableWidth();
			int numLine = i / Galerie.getProps().getTheme().getEditListTableWidth();

			table.setWidget(numLine, numCol, aquaTable);
			aquaTable.setWidget(0, 0, image);
			
			FlexTable infoTable = new FlexTable();
			infoTable.setText(0, 0, ""+aquarelle.getId());
			infoTable.setText(1, 0, aquarelle.getName());
			infoTable.setText(2, 0, aquarelle.getDate());
			aquaTable.setWidget(0, 1, infoTable);
		}
	}
}
