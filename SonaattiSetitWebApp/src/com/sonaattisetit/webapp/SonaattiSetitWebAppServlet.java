package com.sonaattisetit.webapp;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class SonaattiSetitWebAppServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, LÖR");
		MenuParser parser = new MenuParser();
		HashMap menus = parser.parseRss("http://www.sonaatti.fi/info/RSS_fin.php");
	}
}
