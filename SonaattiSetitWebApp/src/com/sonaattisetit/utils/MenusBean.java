package com.sonaattisetit.utils;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.sonaattisetit.webapp.Menu;

public class MenusBean {
	
	private ArrayList<Menu> menus;
	
	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public MenusBean(){
		this.menus = new ArrayList<Menu>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Menu");
		PreparedQuery pq = datastore.prepare(q);
		for (Entity result : pq.asIterable()) {
			Menu menu = new Menu();
			menu.setRestaurant((String)result.getProperty("restaurant"));
			menu.setDishes((ArrayList<String>)result.getProperty("dishes"));
			this.menus.add(menu);
		}
	}
}
