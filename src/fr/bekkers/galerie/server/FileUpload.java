package fr.bekkers.galerie.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fr.bekkers.galerie.shared.Constants;

public class FileUpload extends HttpServlet {

	private static Logger logger = Logger.getLogger(FileUpload.class.getName());

	private static final long serialVersionUID = 8305367618713715640L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			logger.info("param = " + names.nextElement());
		}
		response.setContentType("text/plain");

		FileItem uploadItem = getFileItem(request);
		if (uploadItem == null) {
			response.getWriter().write("NO-SCRIPT-DATA");
			return;
		}
		
		String name = Long.toHexString(new Date().getTime())+".jpg";

		byte[] fileContents = uploadItem.get();
		FileOutputStream fos = new FileOutputStream(Constants.TEMP_PATH+"/"+name);
		fos.write(fileContents);
		fos.close();
		response.getWriter().write("ok "+name);
	}

	private FileItem getFileItem(HttpServletRequest request) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> it = items.iterator();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()
						&& "uploadFormElement".equals(item.getFieldName())) {
					return item;
				}
			}
		} catch (FileUploadException e) {
			return null;
		}
		return null;
	}
}