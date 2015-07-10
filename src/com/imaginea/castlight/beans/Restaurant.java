package com.imaginea.castlight.beans;

import java.util.List;


public class Restaurant {

	private int resturantId;
	
	private List<Item> itemsList;
	
	public Integer getResturantId() {
		return resturantId;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<Item> itemsList) {
		this.itemsList = itemsList;
	}

	public void setResturantId(int resturantId) {
		this.resturantId = resturantId;
	}

}
