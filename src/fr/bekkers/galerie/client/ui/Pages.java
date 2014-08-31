package fr.bekkers.galerie.client.ui;

import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;

import fr.bekkers.galerie.client.Galerie;

public enum Pages {
	ACCUEIL(new PageAccueil()), LISTE_AQUARELLES(new PageListeAquarelle()), NEW_AQUARELLE(
			new PageNewAquarelle()), UPDATE_AQUARELLE(
			new PageUpdateAquarelle()), DETAIL_AQUARELLE(
			new PageDetailAquarelle()), EDIT_LISTE_AQUARELLE(
			new PageEditListeAquarelle()), LOGIN(new PageLogin());

	private Logger logger = Logger.getLogger(Pages.class.getName());

	private Composite panel;

	private Pages(Composite panel1) {
		this.panel = panel1;
		logger.finest("on va initialiser la page "
				+ panel1.getClass().getName());
		Galerie.addPage(panel1);
		logger.info("on vient d'initialiser la page "
				+ panel1.getClass().getName());
	}

	public Composite getPanel() {
		return panel;
	}

}
