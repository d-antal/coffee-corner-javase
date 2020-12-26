package com.cognizant.coffeecorner.task.service;

import java.util.InputMismatchException;
import java.util.Queue;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;

public interface PayService {
	
	/**
	 * Calculates the price based on the selected product(s):
	 * 1. Input (manual selection) 
	 *     Registers a new customer or validates and uses a valid registration for the stamp card discounts
	 * 2. Saved customer choices (automatic)
	 *     Calculates the final price based on the saved input (savedCustomerChoices queue)
	 * @param registrationId  registrationId
	 * @param savedCustomerChoices savedChoices (products)
	 * @param useSavedChoices  if true, savedCustomerChoices is used to calculate price
	 * @return calculated price/purchase
	 * @throws RegistrationNotFoundException registrationId does not exist
	 * @throws InputMismatchException  wrong input
	 * @throws Exception 
	 */
	Double purchase(String registrationId, Queue<Object> savedCustomerChoices, boolean useSavedChoices) throws RegistrationNotFoundException, InputMismatchException, Exception;;
}
