package com.cognizant.coffeecorner.task.repository;

import java.util.HashMap;
import java.util.Map;

public class StampCardRepository {

	private static Map<String, Integer> stampCardMap = new HashMap<>();

	public void addToCard(String name) {
		if (stampCardMap.containsKey(name)) {
			stampCardMap.put(name, stampCardMap.get(name) + 1);
		} else {
			stampCardMap.put(name, 1);
		}
	}

	public void resetDiscount(String name) {
		if (stampCardMap.containsKey(name)) {
			stampCardMap.put(name, 0);
		}
	}

	public int getStampByCustomerName(String name) {
		return stampCardMap.containsKey(name) ? stampCardMap.get(name) : 0;
	}
}
