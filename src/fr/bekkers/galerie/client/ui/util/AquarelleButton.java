package fr.bekkers.galerie.client.ui.util;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.shared.AquarelleLight;

public class AquarelleButton extends Button {
	private AquarelleLight aquarelle;

	public AquarelleButton(AquarelleLight aquarelle1, final String label) {
		super(label);
		this.aquarelle = aquarelle1;
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (label.equals("Edit")) {
					Controller.editAquarelleById(aquarelle.getId());
				} else {
					Controller.deleteAquarelle(aquarelle.getId());
				}

			}
		});
	}

	public AquarelleLight getAquarelle() {
		return aquarelle;
	}

}
