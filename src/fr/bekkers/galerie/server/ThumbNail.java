package fr.bekkers.galerie.server;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import fr.bekkers.galerie.shared.Constants;

@SuppressWarnings("serial")
public class ThumbNail extends HttpServlet {
	private static Logger logger = Logger.getLogger(ThumbNail.class.getName());

	private static final String NAME = "name";
	private static final String PHOTO = "photo";
	private static final String SIZE = "size";

	// TODO mettre un cash des photos thumbizée
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpg");
		String contextPath = this.getServletContext().getRealPath("/") + "/";

		// get parameter name
		String name = request.getParameter(NAME);
		String fileName = null;
		File file = null;
		if (name == null || name.length() == 0) {
			String msg = "Pas de paramètre '" + NAME + "'";
			logger.warning(msg);
			throw new ServletException(msg);
		}

		String photo = request.getParameter(PHOTO);
		if (photo == null) {
			fileName = contextPath + Constants.IMAGE_FULL_SIZE_PATH + "/"+name + ".jpg";
			file = new File(fileName);
		} else {
			fileName = contextPath + Constants.IMAGE_PHOTO_PATH + "/"+name+"/"
					+ photo;
			file = new File(fileName);
		}
		if (!file.exists()) {
			String msg = "Pas de fichier '" + fileName + "'";
			logger.warning(msg);
			throw new ServletException(msg);
		}

		// get parameter size
		String sizeParam = request.getParameter(SIZE);
		if (sizeParam == null || sizeParam.length() == 0) {
			String msg = "Pas de paramètre '" + SIZE + "'";
			logger.warning(msg);
			throw new ServletException(msg);
		}
		int size = 0;
		try {
			size = Integer.parseInt(sizeParam);
		} catch (NumberFormatException e) {
			String msg = "Le paramètre '" + SIZE + "' n'est pas un entier";
			logger.warning(msg);
			throw new ServletException(msg, e);
		}

		synchronized (this) {
			Thumbnails.of(file).size(size, size)
					.toOutputStream(response.getOutputStream());
		}

	}


}
