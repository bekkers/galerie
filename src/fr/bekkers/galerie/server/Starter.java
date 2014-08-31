package fr.bekkers.galerie.server;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.xml.bind.JAXBException;

import fr.bekkers.galerie.server.util.GalerieUtil;
import fr.bekkers.galerie.server.util.XmlUtil;
import fr.bekkers.galerie.shared.Constants;
import fr.bekkers.galerie.shared.GalerieException;

@SuppressWarnings("serial")
public class Starter extends HttpServlet {

	static Logger logger = Logger.getLogger(Starter.class.getName());

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext servletContext = getServletContext();
		
		Constants.APPLICATION_FILE_PATH = servletContext.getRealPath("/");

		String restart = servletContext.getInitParameter("galerie.xml.reload");
		if (restart.equals("true")) {
			try {
				XmlUtil.reloadGalerie();
			} catch (JAXBException | GalerieException e) {
				String msg = "impossible de recharger la galerie : "+e.getMessage();
				logger.severe(msg);
				throw new ServletException(msg, e);
			}
		}

	}

}
