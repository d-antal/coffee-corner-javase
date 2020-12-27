package com.cognizant.coffeecorner.task.service;

public interface StampCardService {

	/**
	 * Adds a StampCard point to the registration
	 * @param regId registration ID
	 */
	void addToCard(String regId);

	/**
	 * Resets StampCard points to 0 
	 * @param regId registration ID
	 */
	void resetDiscount(String regId);

	/**
	 * Finds collected StampCard points by registration ID
	 * @param regId registration ID
	 * @return
	 */
	Integer getPointsByCardId(String regId);

	/**
	 * Creates a new registration
	 * @return saved registration ID
	 */
	String saveNewId();
}
