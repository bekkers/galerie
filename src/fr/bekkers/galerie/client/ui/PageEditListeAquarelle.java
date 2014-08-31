package fr.bekkers.galerie.client.ui;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.client.ui.util.AquarelleButton;
import fr.bekkers.galerie.client.ui.util.GaleriePanel;
import fr.bekkers.galerie.shared.AquarelleLight;

public class PageEditListeAquarelle extends GaleriePanel<AquarelleLight[]> {

	private AquarelleLight[] listAquarelles;

	private final FlexTable table = new FlexTable();
	private final Anchor anchor = new Anchor("Mode consultation");
	private static final Button addButton = new Button("New Aquarelle");
	private static final Button reloadButton = new Button("Reload Galerie");
	private static final HorizontalPanel hp = new HorizontalPanel();

	private enum COL {
		ID_COLUMN, THUMBNAIL_COLUMN, NAME_COLUMN, EDIT_BUTTON, DELETE_BUTTON;
	}

	public PageEditListeAquarelle() {
		super("Editer les aquarelles");
		logger = Logger
				.getLogger(PageEditListeAquarelle.class.getName());
		hp.setSpacing(10);
		hp.add(addButton);
		hp.add(reloadButton);
		mainGalerieVp.add(hp);
		mainGalerieVp.add(table);
		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.newAquarelle();
			}
		});
		reloadButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Controller.reloadGalerie();
			}
		});
		anchor.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				((PageListeAquarelle) Pages.LISTE_AQUARELLES.getPanel())
						.init(listAquarelles);
				Controller.show(Pages.LISTE_AQUARELLES);
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
			Controller.editAquarelleById(id);
		}
	}

	public void init(AquarelleLight[] result) {
		listAquarelles = result;
		table.clear(true);
		logger.info("result.length =" + result.length);
		for (int i = 0; i < result.length; i++) {
			AquarelleLight aquarelle = result[i];
			Image image = new Image(Galerie.getProps().getContextUrl()+"/thumbnail?name="+ 
					aquarelle.getName()+"&size="+
					Galerie.getProps().getTheme().getImageThumbnailSize());
			image.addClickHandler(new ShowAquarelleHandler(aquarelle
					.getId()));
			int lineNumber = i;
			table.setWidget(lineNumber, COL.THUMBNAIL_COLUMN.ordinal(), image);
			table.setText(lineNumber, COL.ID_COLUMN.ordinal(),
					aquarelle.getId()+"");
			table.setText(lineNumber, COL.NAME_COLUMN.ordinal(),
					aquarelle.getName());
			table.setWidget(lineNumber, COL.EDIT_BUTTON.ordinal(),
					new AquarelleButton(aquarelle, "Edit"));
			table.setWidget(lineNumber, COL.DELETE_BUTTON.ordinal(),
					new AquarelleButton(aquarelle, "Delete"));
		}
	}
}
