package com.sonaattisetit.webapp;

import java.util.ArrayList;

public class Menu {
	/*public Menu(String restaurant, ArrayList<String> dishes) {
		this.restaurant = restaurant;
		this.dishes = dishes;
	}*/

	private String restaurant;
	private ArrayList<String> dishes;
	
	public ArrayList<String> getDishes() {
		return dishes;
	}

	public void setDishes(ArrayList<String> dishes) {
		this.dishes = dishes;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}
}
