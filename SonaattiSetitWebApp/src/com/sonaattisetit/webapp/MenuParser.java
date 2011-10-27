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
	
	public void parseRss(String feedUrl){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler(){			
				boolean item = false;
				boolean title = false;
				boolean description = false;
				ArrayList<String> servings;
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Entity menu;
				Date date = new Date();
				
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")) item = true;
					if(qName.toUpperCase().equals("TITLE")) title = true;
					if(qName.toUpperCase().equals("DESCRIPTION")){
						description = true;
						servings = new ArrayList<String>();
					}
				}			
				public void characters(char ch[], int start, int length) throws SAXException {
					if(item && title){
						menu = new Entity("Menu");
						String str = new String(ch, start, length);
						String[] strArr = str.split("\\s+");
						menu.setProperty("date",date);
						menu.setProperty("restaurant", strArr[0].toLowerCase());
					}
					if(item && description){
						String str = new String(ch, start, length);
						String[] strArr = str.split(",");
						for(int i=0;i<strArr.length;i++){
							servings.add(strArr[i].trim());
						}
						menu.setProperty("servings", servings);
					}
				}				
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if(qName.toUpperCase().equals("ITEM")){
						item = false;
						datastore.put(menu);
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
