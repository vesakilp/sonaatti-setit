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
		/*MenuParser parser = new MenuParser();
		parser.parseRss("http://www.sonaatti.fi/info/RSS_fin.php");
		ArrayList<Menu> menus = parser.getMenus();
		if(menus != null){
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			//Entity dbMenus = new Entity("Menus");
			//datastore.put(dbMenus);
			Iterator<Menu> iter = menus.iterator();
			while(iter.hasNext()){
				Menu menu = (Menu)iter.next();
				Entity dbMenu = new Entity("Menu",menu.getRestaurant());
				dbMenu.setProperty("restaurant", menu.getRestaurant());
				dbMenu.setProperty("dishes", menu.getDishes());
				datastore.put(dbMenu);
				//TODO tähän entityjen luonti
				System.out.println(menu.getRestaurant()+": "+menu.getDishes().toString());
			}
		}
		else{
			//TODO jotakin virheilmoa jos ei ole saatavilla ruokalistoja, onko sit ohjelmavirhe vai eikö oikeesti oo ruokaa tarjolla eri käsittelyt
		}*/
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Menu");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			Menu menu = new Menu();
			menu.setRestaurant((String)result.getProperty("restaurant"));
			//menu.setDishes((ArrayList<String>)result.getProperty("restaurant"));
			System.out.println(menu.getRestaurant());
			//System.out.println(menu.getDishes().toString());
		}
	}
}
