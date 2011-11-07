package com.sonaattisetit.webapp;

import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class MenuParser {
	private ArrayList<Menu> menus;
		
	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public static class MenusFeedHandler extends DefaultHandler {
		boolean item = false;
		boolean title = false;
		boolean description = false;
		ArrayList<Menu> menus = new ArrayList<Menu>();
		ArrayList<String> dishes;
		Menu menu;
		
		public MenusFeedHandler(ArrayList<Menu> menus) {
			this.menus = menus;
		}
				
		public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
			if(qName.toUpperCase().equals("ITEM")) item = true;
			if(qName.toUpperCase().equals("TITLE")){
				this.menu = new Menu();
				title = true;
			}
			if(qName.toUpperCase().equals("DESCRIPTION")){
				description = true;
			}
		}			
		public void characters(char ch[], int start, int length) throws SAXException {
			if(item && title){
				String str = new String(ch, start, length);
				String[] strArr = str.split("\\s+");
				this.menu.setRestaurant(strArr[0].toLowerCase().trim());
			}
			if(item && description){
				String str = new String(ch, start, length);
				String[] strArr = str.split(",");
				this.dishes = new ArrayList<String>();
				for(int i=0;i<strArr.length;i++){
					this.dishes.add(strArr[i].trim());
				}
			}
		}			
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if(qName.toUpperCase().equals("ITEM")){
				item = false;
				this.menu.setDishes(dishes);
				this.menus.add(this.menu);
			}
			if(qName.toUpperCase().equals("TITLE")) title = false;
			if(qName.toUpperCase().equals("DESCRIPTION")) description = false;
		}
	}
	
	public void parseRss(String feedUrl){
		try {
			this.menus = new ArrayList<Menu>();
			SAXParserFactory sf = SAXParserFactory.newInstance();
			sf.setNamespaceAware(true);
			SAXParser sp = sf.newSAXParser();
			sp.parse(feedUrl, new MenusFeedHandler(this.menus));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
}
