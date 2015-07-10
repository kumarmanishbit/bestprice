package com.imaginea.castlight.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imaginea.castlight.beans.Food;
import com.imaginea.castlight.beans.Item;
import com.imaginea.castlight.beans.Restaurant;
import com.imaginea.castlight.utils.FoodProcessor;

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

			br = new BufferedReader(new FileReader("sample_data.csv"));

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

		List<String> foodToBeOrdered = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			foodToBeOrdered.add(args[i].trim());
		}

		FoodProcessor processor = new FoodProcessor();
		Map<Integer, Restaurant> rest = processor.getAvailableItems(foodToBeOrdered, resturantList);

		Map<Integer, Double> finalPrice = new HashMap<Integer, Double>();

		System.out.println("Number of Restaurant found "+ rest.size());
		
		for(int i=0;i<rest.get(2).getItemsList().size();i++)
		System.out.println(rest.get(1).getItemsList().get(i).getItemName().getListOfFood());
		
		double tmpPrice = 0.00d;
		double minPrice = Double.MAX_VALUE;
		for (Map.Entry<Integer, Restaurant> res : rest.entrySet()) {

			tmpPrice = 0.00d;
			for (Item temp : res.getValue().getItemsList()) {

				minPrice = Double.MAX_VALUE;

				if (temp.getItemName().getListOfFood().containsAll(foodToBeOrdered)) {
					tmpPrice += temp.getItemPrice();
					
				}
				System.out.println(temp.getItemName().getListOfFood() + "  " +temp.getItemPrice());
				if (minPrice >= tmpPrice) {
					minPrice = tmpPrice;
				}
			}
			finalPrice.put(res.getKey(), minPrice);
		}
		if (rest.size() == 0) {
			System.out.println("Nil");
			System.exit(0);
		}

		Integer minKey = null;
		Double minValue = Double.MAX_VALUE;
		for (Map.Entry<Integer, Double> entry : finalPrice.entrySet()) {
			Double value = entry.getValue();
			if (value < minValue) {
				minKey = entry.getKey();
				minValue = value;
			}
		}

		System.out.println("minKey = " + minKey + " minValue=" + minValue);

	}

}
