package com.imaginea.castlight.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imaginea.castlight.beans.Item;
import com.imaginea.castlight.beans.Restaurant;

public class FoodProcessor {

	public Map<Integer, Restaurant> getAvailableItems(List<String> foodItem, Map<Integer, Restaurant> resturantList) {
		Map<Integer, Restaurant> matchedRestaurant = new HashMap<Integer, Restaurant>();

		for (Map.Entry<Integer, Restaurant> res : resturantList.entrySet()) {

			if (findFood(foodItem, res.getValue().getItemsList())) {
				matchedRestaurant.put(res.getKey(), res.getValue());
			}

		}

		return matchedRestaurant;
	}

	public boolean findFood(List<String> foodItem, List<Item> items) {
		Set<String> foundItem = new HashSet<>();

		for (int i = 0; i < items.size(); i++) {

			for (int j = 0; j < foodItem.size(); j++) {

				if (items.get(i).getItemName().getListOfFood().contains(foodItem.get(j))) {
					foundItem.add(foodItem.get(j));
				}
			}
		}
		return foundItem.size() == foodItem.size();
	}

	public boolean compareList(List<String> items, String foodItem) {

		return false;

	}

}
