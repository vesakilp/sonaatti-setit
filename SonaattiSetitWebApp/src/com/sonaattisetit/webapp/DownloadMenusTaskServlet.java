package com.sonaattisetit.webapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class DownloadMenusTaskServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		MenuParser parser = new MenuParser();
		parser.parseRss("http://www.sonaatti.fi/info/RSS_fin.php");
		ArrayList<Menu> menus = parser.getMenus();
		if(menus != null){
			Iterator<Menu> iter = menus.iterator();
			while(iter.hasNext()){
				Menu menu = (Menu)iter.next();
				System.out.println(menu.getRestaurant());
			}
		}
		else{
			//TODO jotakin virheilmoa jos ei ole saatavilla ruokalistoja, onko sit ohjelmavirhe vai eikö oikeesti oo ruokaa tarjolla eri käsittelyt
		}
		/*DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Menu");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			String restaurant = (String) result.getProperty("restaurant");
			System.out.println(restaurant);*/
			/*String firstName = (String) result.getProperty("firstName");
			String lastName = (String) result.getProperty("lastName");
			Long height = (Long) result.getProperty("height");
			System.out.println(lastName + " " + firstName + ", " + height.toString() + " inches tall");

		}*/
	}
}
