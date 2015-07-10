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

	//	System.out.println("size = " + resturantList.size());

		for (Map.Entry<Integer, Restaurant> res : resturantList.entrySet()) {

			if (findFood(foodItem, res.getValue().getItemsList())) {
			//	System.out.println("found");
				matchedRestaurant.put(res.getKey(), res.getValue());
			}

		}

		return matchedRestaurant;
	}

	public boolean findFood(List<String> foodItem, List<Item> items) {

		// System.out.println(foodItem);
		// System.out.println(items.size());
		// boolean found = false;
		Set<String> foundItem = new HashSet<>();

		int count = 0;
		for (int i = 0; i < items.size(); i++) {

			for (int j = 0; j < foodItem.size(); j++) {

				if (items.get(i).getItemName().getListOfFood().contains(foodItem.get(j))) {
					count++;
					// found = true;
					foundItem.add(foodItem.get(j));
				//	System.out.println(" " + items.get(i).getItemName().getListOfFood() + " === " + foodItem.get(j));
				}
			}
		}
		//System.out.println("count = " + foundItem.size() + "food Item Size = " + foodItem.size());
		return foundItem.size() == foodItem.size();
	}

	public boolean compareList(List<String> items, String foodItem) {

		return false;

	}

}
