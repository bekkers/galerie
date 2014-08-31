package fr.bekkers.galerie.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

import fr.bekkers.galerie.client.Controller;
import fr.bekkers.galerie.client.jsobject.JsAquarelle;
import fr.bekkers.galerie.client.ui.util.GaleriePanel;
import fr.bekkers.galerie.shared.Constants;

public class PageNewAquarelle extends GaleriePanel<JsAquarelle> {

	private class ConfirmationDialog extends DialogBox {

		public ConfirmationDialog(final String retour) {
			// Set the dialog box's caption.
			setText("Confirmer la création");

			// Enable animation.
			setAnimationEnabled(true);

			// Enable glass background.
			setGlassEnabled(true);

			// DialogBox is a SimplePanel, so you have to set its widget
			// property to
			// whatever you want its contents to be.
			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(10);
			setWidget(hp);

			Button Annuler = new Button("Annuler");
			Annuler.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ConfirmationDialog.this.hide();
					Controller.returnEditList();
				}
			});
			hp.add(Annuler);

			Button ok = new Button("Confirmer");
			ok.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					ConfirmationDialog.this.hide();
					PageNewAquarelle.this.confirmerCreation(retour);
				}
			});
			hp.add(ok);
		}

	}

	private static final String UPLOAD_ACTION_URL = GWT.getModuleBaseURL()
			+ "fileupload";

	protected final FlexTable table = new FlexTable();
	protected final Button boutonConfirmer = new Button("Confirmer");
	protected final Button boutonAnnuler = new Button("Annuler");
	protected TextBox nameBox;
	protected TextBox titleBox;
	protected DateBox dateBox;
	protected TextBox gps;
	protected FileUpload fileUploadWidget;

	private final FormPanel fp = new FormPanel();
	private final HorizontalPanel boutons = new HorizontalPanel();

	public PageNewAquarelle() {
		super("Saisissez une oeuvre");
		init();
	}

	public PageNewAquarelle(String titre) {
		super(titre);
		init();
	}

	private void init() {
		logger = Logger.getLogger(PageNewAquarelle.class.getName());

		nameBox = new TextBox();
		titleBox = new TextBox();
		titleBox.setStyleName("imageTitle");
		dateBox = new DateBox();
		gps = new TextBox();
		fileUploadWidget = new FileUpload();

		mainGalerieVp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		mainGalerieVp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		mainGalerieVp.add(fp);
		mainGalerieVp.add(boutons);

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		fp.setEncoding(FormPanel.ENCODING_MULTIPART);
		fp.setMethod(FormPanel.METHOD_POST);

		// Create a FileUpload widget.
		fileUploadWidget.setName("uploadFormElement");
		fp.add(table);
		fp.setAction(UPLOAD_ACTION_URL);

		// Add an event handler to the form.
		fp.addSubmitHandler(new FormPanel.SubmitHandler() {
			public void onSubmit(SubmitEvent event) {
				// This event is fired just before the form is submitted. We can
				// take this opportunity to perform validation.
				List<String> alerts = new ArrayList<String>();
				if (nameBox.getText().length() == 0) {
					alerts.add("name not be empty");
				}
				if (dateBox.getValue() == null) {
					alerts.add("date not be empty");
				}
				checkFileName(alerts);
				if (!alerts.isEmpty()) {
					StringBuffer buff = new StringBuffer();
					for (String alert : alerts) {
						buff.append(alert).append("\n");
					}
					Window.alert(buff.toString());
					// TODO bug après une alert la page semble désactivée
					event.cancel();
				}

			}

		});

		fp.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String retour = event.getResults();
				ConfirmationDialog dialog = new ConfirmationDialog(retour);
				dialog.center();
				dialog.show();
			}

		});

		boutons.setSpacing(10);
		boutons.add(boutonAnnuler);
		boutons.add(boutonConfirmer);
		boutonAnnuler.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Controller.returnEditList();
				;
			}
		});
		boutonConfirmer.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO bug la création ne marche plus
				String fileName = fileUploadWidget.getFilename();
				logger.info("Submitting, name = " + nameBox.getText()
						+ ", fileName = " + fileName);
				fp.submit();
				logger.info("Submission done, name = " + nameBox.getText());
			}
		});
		table.setWidget(0, 0, new Label("Name"));
		table.setWidget(0, 1, nameBox);
		table.setWidget(1, 0, new Label("Title"));
		table.setWidget(1, 1, titleBox);
		table.setWidget(2, 0, new Label("Date"));
		table.setWidget(2, 1, dateBox);
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat(Constants.DATE_FORMAT_STRING)));
		table.setWidget(3, 0, new Label("GPS"));
		table.setWidget(3, 1, gps);
		// TODO feature mettre en place la localisation d'une aquarelle
		table.setWidget(4, 0, new Label("Address"));
		table.setWidget(5, 0, new Label("Image"));
		table.setWidget(5, 1, fileUploadWidget);
	}

	// to be overriden by descendant class PageUpdateAquarelle
	protected void checkFileName(List<String> alerts) {
		if (fileUploadWidget.getFilename() == null
				|| fileUploadWidget.getFilename().length() == 0) {
			alerts.add("Please select an image first");
		}
	}

	@Override
	public void init(JsAquarelle aquarelle) {
		if (aquarelle == null) {
			fp.reset();
			nameBox.setText("");
			titleBox.setText("");
			// todo réinitialiser la dateBox
			gps.setText("");
		} else {
			logger.info("Initialisation des données de la page : "
					+ aquarelle.getName());

			nameBox.setText(aquarelle.getName());
			titleBox.setText(aquarelle.getTitle());
			dateBox.setValue(aquarelle.getDate());
			gps.setText(aquarelle.getGps());
			logger.info("Fin d'initialisation des données de la page : "
					+ aquarelle.getName());
		}
	}

	public void confirmerCreation(String retour) {
		JsAquarelle aquarelle = JsAquarelle.create();
		aquarelle.setName(nameBox.getText());
		aquarelle.setTitle(titleBox.getText());
		aquarelle.setDate(dateBox.getValue());
		String fileName = "";
		if (retour != null) {
			// TODO bug pour une mise à jour le nom de fichier peut être absent
			MatchResult match = RegExp.compile("<pre>ok (.*)</pre>", "m").exec(
					retour);
			fileName = match.getGroup(1);
			if (fileName == null || fileName.length() == 0) {
				logger.severe("Réponse de la servlet Upload incorrecte : "
						+ retour);
				return;
			}
		}
		logger.info("onSubmitComplete, name = "
				+ nameBox.getText()
				+ ", "
				+ (fileName.length() == 0 ? "no new fileName" : "fileName = "
						+ fileName));
		serverAction(aquarelle, fileName);
	}

	// to be overridden by descendant class PageUpdateAquarelle
	public void serverAction(JsAquarelle aquarelle, String fileName) {
		Controller.createAquarelle(aquarelle, fileName);
	}

}
