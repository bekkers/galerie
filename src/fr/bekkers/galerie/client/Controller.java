package fr.bekkers.galerie.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.bekkers.galerie.client.jsobject.JsAquarelle;
import fr.bekkers.galerie.client.jsobject.JsProps;
import fr.bekkers.galerie.client.ui.PageAccueil;
import fr.bekkers.galerie.client.ui.PageDetailAquarelle;
import fr.bekkers.galerie.client.ui.PageEditListeAquarelle;
import fr.bekkers.galerie.client.ui.PageNewAquarelle;
import fr.bekkers.galerie.client.ui.PageUpdateAquarelle;
import fr.bekkers.galerie.client.ui.Pages;
import fr.bekkers.galerie.client.ui.util.GaleriePanel;
import fr.bekkers.galerie.shared.AquarelleLight;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;
import fr.bekkers.galerie.shared.ThemeName;

public class Controller {

	private static Logger logger = Logger.getLogger(Controller.class.getName());

	private static boolean isLoggedIn = false;

	// TODO feature gérer l'historique du navigateur

	public static void show(Pages page) {
		logger.info("Show page : " + page.name());
		Galerie.deckPanel.showWidget(page.ordinal());
	}

	public static void getGps() {
		Galerie.getGalerieService().getGps(new AsyncCallback<String[]>() {

			@Override
			public void onSuccess(String[] result) {
				try {
					((PageAccueil) Pages.ACCUEIL.getPanel()).setMarkers(result);
				} catch (GalerieException e) {
					String mess = Constants.RETOUR_RCP_EN_ERREUR
							+ ", impossible d'obtenir les markers : "
							+ e.getMessage();
					logger.severe(mess);
					Window.alert(mess);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				String mess = Constants.RETOUR_RCP_EN_ERREUR + " : "
						+ caught.getMessage();
				logger.severe(mess);
				Window.alert(mess);
			}
		});
	}

	public static void startGalerie(ThemeName theme) {
		Galerie.getGalerieService().start(theme.name(), new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				Galerie.setProps((JsProps) JsonUtils
						.safeEval(result));
				logger.info("galerie started "+result);
				Controller.show(Pages.ACCUEIL);
			}

			@Override
			public void onFailure(Throwable caught) {
				String mess = Constants.RETOUR_RCP_EN_ERREUR + " : "
						+ caught.getMessage();
				logger.severe(mess);
				Window.alert(mess);
			}
		});
	}

	public static void reloadGalerie() {
		Galerie.getGalerieService().reloadGalerie(
				new AsyncCallback<AquarelleLight[]>() {

					@Override
					public void onSuccess(AquarelleLight[] result) {
						logger.info("Galerie has been reloaded");
						showEditList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}

	public static void showList(final Pages pageType) {
		logger.finest("sending asynchronous call 'getListLight()'");
		Galerie.getGalerieService().getLightList(
				new AsyncCallback<AquarelleLight[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(AquarelleLight[] result) {
						@SuppressWarnings("unchecked")
						GaleriePanel<AquarelleLight[]> page = (GaleriePanel<AquarelleLight[]>) pageType
								.getPanel();
						logger.fine("Retour getListLight() : result.size ="
								+ result.length);

						try {
							page.init(result);
							show(pageType);
						} catch (GalerieException e) {
							String msg = "can't show page : " + e.getMessage();
							logger.warning(msg);
						}
					}

				});
	}

	public static void showAquarelle(final int id) {
		logger.finest("sending asynchronous call 'getAquarelle(" + id + ")'");
		Galerie.getGalerieService().getAquarelle(id,
				new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						PageDetailAquarelle page = (PageDetailAquarelle) Pages.DETAIL_AQUARELLE
								.getPanel();
						logger.fine("Retour getAquarelle(" + id
								+ ") aquarelle '" + result + "'");
						JsAquarelle aquarelle = (JsAquarelle) JsonUtils
								.safeEval(result);
						try {
							page.init(aquarelle);
							show(Pages.DETAIL_AQUARELLE);
						} catch (GalerieException e) {
							String msg = "can't show page : " + e.getMessage();
							logger.warning(msg);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}

	public static void editAquarelleById(int id) {
		logger.finest("sending asynchronous call 'getAquarelle(" + id + ")'");
		Galerie.getGalerieService().getAquarelle(id,
				new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						logger.log(Level.INFO, "showing aquarelle '" + result
								+ "'");
						JsAquarelle aquarelle = (JsAquarelle) JsonUtils
								.safeEval(result);
							((PageUpdateAquarelle) Pages.UPDATE_AQUARELLE
									.getPanel()).init(aquarelle);
							show(Pages.UPDATE_AQUARELLE);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}
				});
	}

	public static void updateAquarelle(JsAquarelle aquarelle, String fileName) {
		String json = aquarelle.toJson();
		logger.finest("sending asynchronous call 'updateAquarelle(" + json
				+ ")'");
		Galerie.getGalerieService().updateAquarelle(json, fileName,
				new AsyncCallback<AquarelleLight[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(AquarelleLight[] result) {
						Controller.showEditList(result);
					}
				});
	}

	public static void deleteAquarelle(int id) {
		logger.finest("sending asynchronous call 'deleteAquarelle(" + id + ")'");
		// TODO feature create dialog to delete files
		Galerie.getGalerieService().deleteAquarelle(id,
				new AsyncCallback<AquarelleLight[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(AquarelleLight[] result) {
						Controller.showEditList(result);
					}
				});
	}

	public static void showEditList() {
		if (isLoggedIn) {
			Controller.showList(Pages.EDIT_LISTE_AQUARELLE);
		} else {
			show(Pages.LOGIN);
		}

	}

	public static void showEditList(AquarelleLight[] result) {
		PageEditListeAquarelle page = (PageEditListeAquarelle) Pages.EDIT_LISTE_AQUARELLE
				.getPanel();
		logger.log(Level.INFO, "result.size =" + result.length);
		page.init(result);
		if (!isLoggedIn) {
			show(Pages.LOGIN);
		} else {
			show(Pages.EDIT_LISTE_AQUARELLE);
		}
	}

	public static void returnEditList() {
		show(Pages.EDIT_LISTE_AQUARELLE);
	}

	public static void createAquarelle(final JsAquarelle aquarelle,
			final String imageTemp) {
		String json = aquarelle.toJson();
		logger.finest("sending asynchronous call 'createAquarelle(" + json
				+ ")'");
		Galerie.getGalerieService().createAquarelle(json, imageTemp,
				new AsyncCallback<AquarelleLight[]>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(AquarelleLight[] result) {
						logger.fine("Retour createAquarelle("
								+ aquarelle.getName() + ") : Created");
						showEditList(result);
					}
				});

	}

	public static void login(String user, String password) {
		// TODO feature gérer les utilisateurs
		logger.info("user = " + user);
		isLoggedIn = true;
		showList(Pages.EDIT_LISTE_AQUARELLE);
	}

	public static void newAquarelle() {
		((PageNewAquarelle) Pages.NEW_AQUARELLE
				.getPanel()).init(null);
		Controller.show(Pages.NEW_AQUARELLE);
	}
}
