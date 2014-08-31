package fr.bekkers.galerie.client.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.Galerie;
import fr.bekkers.galerie.client.jsobject.JsAquarelle;

public class PageUpdateAquarelle extends PageNewAquarelle {

	private final TextBox idBox = new TextBox();
	private final TextBox fileNameBox = new TextBox();

	public PageUpdateAquarelle() {
		super("Mise à jour d'une aquarelle");
		logger = Logger.getLogger(PageUpdateAquarelle.class
				.getName());
		table.insertRow(0);
		table.setWidget(0, 0, new Label("Id"));
		table.setWidget(0, 1, idBox);
		idBox.setEnabled(false);
		boutonConfirmer.setHTML("Confirmer mise à jour");
		// TODO feature Permettre de changer le nom de l'aquarelle
		// C'est compliqué coté serveur
		nameBox.setEnabled(false);
		
		VerticalPanel vp = new VerticalPanel();
		fileNameBox.setEnabled(false);
		fileNameBox.setStyleName("fileName");
		vp.add(fileNameBox);
		
		vp.add(fileUploadWidget);
		table.setWidget(6, 1, vp);
		fileUploadWidget.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				fileNameBox.setText("");				
			}
		});
	}

	@Override
	public void init(JsAquarelle aquarelle) {
		super.init(aquarelle);
		idBox.setText(""+aquarelle.getId());
		fileNameBox.setText(Galerie.getProps().getFullImageUrl(aquarelle.getName()));
	}
	
	@Override
	protected void checkFileName(List<String> alerts) {
	}

	@Override
	public void serverAction(JsAquarelle aquarelle, String fileName) {
		Controller.updateAquarelle(aquarelle, fileName);
	}

}
