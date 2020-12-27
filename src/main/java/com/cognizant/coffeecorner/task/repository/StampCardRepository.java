package com.cognizant.coffeecorner.task.repository;

import java.util.HashMap;
import java.util.Map;

public class StampCardRepository {

	private static Map<String, Integer> stampCardMap = new HashMap<>();

	public void addToCard(String name) {
		stampCardMap.put(name, stampCardMap.get(name) + 1);
	}

	public void resetDiscount(String name) {
		if (stampCardMap.containsKey(name)) {
			stampCardMap.put(name, 0);
		}
	}

	public Integer getPointsByCardId(String cardId) {
		return stampCardMap.get(cardId);
	}

	public String registerCustomer() {
		int currentSize = stampCardMap.size();
		String regName = "cc" + currentSize++;
		stampCardMap.put(regName, 0);
		return regName;
	}
}
