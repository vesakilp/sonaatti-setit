package com.sonaattisetit.webapp;

import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class MenuParser {
	private ArrayList<Menu> menus;
	
	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public void parseRss(String feedUrl){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler(){			
				boolean item = false;
				boolean title = false;
				boolean description = false;
				ArrayList<Menu> menus = new ArrayList<Menu>();
				Menu menu;
				//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				//Entity menu;
				//Date date = new Date();
				
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")) item = true;
					if(qName.toUpperCase().equals("TITLE")) title = true;
					if(qName.toUpperCase().equals("DESCRIPTION")){
						description = true;
						menu = new Menu();
						//servings = new ArrayList<String>();
					}
				}			
				public void characters(char ch[], int start, int length) throws SAXException {
					if(item && title){
						//menu = new Entity("Menu");
						String str = new String(ch, start, length);
						String[] strArr = str.split("\\s+");
						//menu.setProperty("date",date);
						//menu.setProperty("restaurant", strArr[0].toLowerCase());
						this.menu.setRestaurant(strArr[0].toLowerCase().trim());
					}
					if(item && description){
						String str = new String(ch, start, length);
						String[] strArr = str.split(",");
						for(int i=0;i<strArr.length;i++){
							menu.getDishes().add(strArr[i].trim());
						}
						//menu.setProperty("servings", servings);
					}
					System.out.println(new String(ch, start, length));
				}				
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")){
						item = false;
						menus.add(menu);
						//datastore.put(menu);
					}
					if(qName.toUpperCase().equals("TITLE")) title = false;
					if(qName.toUpperCase().equals("DESCRIPTION")) description = false;
				}	
			};		
			saxParser.parse(feedUrl, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
