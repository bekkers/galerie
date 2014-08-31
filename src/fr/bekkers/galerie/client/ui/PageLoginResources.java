package fr.bekkers.galerie.client.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface PageLoginResources extends ClientBundle {
   /**
   * Sample CssResource.
   */
   public interface MyCss extends CssResource {
      String blackText();

      String redText();

      String loginButton();

      String box();

      String background();
   }

   @Source("PageLogin.css")
   MyCss style();
}