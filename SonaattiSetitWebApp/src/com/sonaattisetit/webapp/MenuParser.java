package com.sonaattisetit.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class MenuParser {

	private ArrayList<String> restaurantNames;
	private HashMap<String, ArrayList<String>> menus;
	
	public HashMap<String, ArrayList<String>> parseRss(String feedUrl){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		restaurantNames = new ArrayList<String>();
		menus = new HashMap<String, ArrayList<String>>();
		restaurantNames.add("PIATO");
		restaurantNames.add("LOZZI");
		
		try {
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler(){			
				boolean item = false;
				boolean title = false;
				boolean description = false;
				String currentRestaurant;
				ArrayList<String> servings;
				
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")) item = true;
					if(qName.toUpperCase().equals("TITLE")) title = true;
					if(qName.toUpperCase().equals("DESCRIPTION")){
						description = true;
						servings = new ArrayList<String>();
					}
				}				
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")) item = false;					
					if(qName.toUpperCase().equals("TITLE")) title = false;
					if(qName.toUpperCase().equals("DESCRIPTION")){
						description = false;
						menus.put(currentRestaurant, servings);
					}
				}				
				public void characters(char ch[], int start, int length) throws SAXException {
					if(item && title){
						String str = new String(ch, start, length);
						String[] strArr = str.split("\\s+");
						if(restaurantNames.contains(strArr[0].toUpperCase())){
							currentRestaurant = strArr[0].toUpperCase();
						}
					}
					if(item && description){
						String str = new String(ch, start, length);
						String[] strArr = str.split(",");
						for(int i=0;i<strArr.length;i++){
							servings.add(strArr[i].trim());
						}
					}
				}
			};
			saxParser.parse(feedUrl, handler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menus; 
	}
	
}
