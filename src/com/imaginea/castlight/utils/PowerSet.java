package com.imaginea.castlight.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.imaginea.castlight.beans.Item;

public class PowerSet {

	
	
	private static void getSubsets(List<Item> superSet, int k, int idx, Set<Item>  current,
			List<Set<Item>> solution) {
		// successful stop clause
		if (current.size() == k) {
			solution.add(new HashSet<>(current));
			return;
		}
		// unseccessful stop clause
		if (idx == superSet.size())
			return;
		Item x = superSet.get(idx);
		current.add(x);
		// "guess" x is in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
		current.remove(x);
		// "guess" x is not in the subset
		getSubsets(superSet, k, idx + 1, current, solution);
	}

	public static List<Set<Item>> getSubsets(List<Item> superSet, int k) {
		List<Set<Item>> res = new ArrayList<>();
		getSubsets(superSet, k, 0, new HashSet<Item>(), res);
		return res;
	}

}
