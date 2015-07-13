package com.imaginea.castlight.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imaginea.castlight.beans.Food;
import com.imaginea.castlight.beans.Item;
import com.imaginea.castlight.beans.Restaurant;
import com.imaginea.castlight.utils.FoodProcessor;
import com.imaginea.castlight.utils.PowerSet;

public class JurgensvilleAdminManager {

	public static void main(String[] args) {

		Item item;

		Restaurant resturant;

		Map<Integer, Restaurant> resturantList = new HashMap<Integer, Restaurant>();

		String itemDetails[];

		List<String> itemName = null;

		BufferedReader br = null;

		Food foodItem = null;

		try {
			String line;

			br = new BufferedReader(new FileReader(args[0].trim()));

			List<Item> items = null;
			List<Item> tmp = null;

			while ((line = br.readLine()) != null) {

				items = new ArrayList<Item>();
				tmp = new ArrayList<Item>();
				foodItem = new Food();
				resturant = new Restaurant();
				item = new Item();
				itemName = new ArrayList<String>();
				itemDetails = line.split(",");
				resturant.setResturantId(Integer.parseInt(itemDetails[0]));
				item.setItemPrice(Double.parseDouble(itemDetails[1]));
				for (int i = 2; i < itemDetails.length; i++) {
					itemName.add(itemDetails[i].trim());
				}
				foodItem.setListOfFood(itemName);
				item.setItemName(foodItem);

				if (resturantList.containsKey(resturant.getResturantId())) {

					tmp = resturantList.get(resturant.getResturantId()).getItemsList();
					tmp.add(item);

					resturant.setItemsList(tmp);

					resturantList.put(resturant.getResturantId(), resturant);

				} else {
					items.add(item);
					resturant.setItemsList(items);
					resturantList.put(Integer.parseInt(itemDetails[0]), resturant);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * 
		 * Processing starts here.
		 * 
		 */

		List<String> foodToBeOrdered = new ArrayList<String>();

		for (int i = 1; i < args.length; i++) {
			foodToBeOrdered.add(args[i].trim());
		}

		FoodProcessor processor = new FoodProcessor();
		Map<Integer, Restaurant> rest = processor.getAvailableItems(foodToBeOrdered, resturantList);
		
		
		if (rest.size() == 0) {
			System.out.println("Nil");
			System.exit(0);
		}

		Iterator<Restaurant> foundRestaurant = rest.values().iterator();

		int numberOfFoodToBeOrdered = foodToBeOrdered.size();

		List<Set<Item>> maniItems = new ArrayList<>();

		Map<Integer, Double> minPriceRestaurant = new HashMap<Integer, Double>();
		
		Set<Double> allItemPrice = new HashSet<Double>();
		
		while (foundRestaurant.hasNext()) {

			maniItems = new ArrayList<>();
			
			int restID=foundRestaurant.next().getResturantId();
			
			for (int i = numberOfFoodToBeOrdered; i > 0; i--) {

				maniItems.addAll(PowerSet.getSubsets(rest.get(restID).getItemsList(),
						i));

			}
			for (int j = 0; j < maniItems.size(); j++) {

				allItemPrice.add(findFood(maniItems.get(j), foodToBeOrdered));
				

			}
			minPriceRestaurant.put(restID, Collections.min(allItemPrice));

		}

		/**
		 * Finding minimum amount
		 */

		double minPrice = Double.MAX_VALUE;
		
		Integer restaurantID = null;
		for (Map.Entry<Integer, Double> entry : minPriceRestaurant.entrySet()) {
			Double value = entry.getValue();
			if (value < minPrice) {
				restaurantID = entry.getKey();
				minPrice = value;
			}
		}
		System.out.println( restaurantID + ", " + minPrice);
	}

	public static Double findFood(Set<Item> items, List<String> foodToBeOrdered) {
		Iterator<Item> iterator;
		List<String> item = new ArrayList<String>();
		Set<String> foundItem = new HashSet<>();
		iterator = items.iterator();

		Set<Double> allItemPrice = new HashSet<Double>();
		Item tmpItem;
		double price = 0.00d;


		while (iterator.hasNext()) {

			tmpItem = iterator.next();

			
			item = tmpItem.getItemName().getListOfFood();

			price += tmpItem.getItemPrice();

			List<String> common = new ArrayList<String>(item);
			common.retainAll(foodToBeOrdered);
			foundItem.addAll(common);

	
		}

		if (foundItem.size() == foodToBeOrdered.size()) {
			allItemPrice.add(price);
			return Collections.min(allItemPrice);
		}

		return Double.MAX_VALUE;

	}
}
