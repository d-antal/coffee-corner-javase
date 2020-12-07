package com.cognizant.coffeecorner.task.service;

import java.util.InputMismatchException;
import java.util.Queue;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;

public interface PriceService {

	/**
	 * @param registartionId the entered registrationId
	 * @param savedCustomerChoices savedChoices (products)
	 * @param useSavedChoices if true, savedCustomerChoices is used to calculate price
	 * @return calculated price
	 * @throws RegistrationNotFoundException if the entered registrationId does not exist
	 * @throws InputMismatchException if not number is pressed
	 */
	Double getFinalPrice(String registartionId, Queue<Object> savedCustomerChoices, boolean useSavedChoices)
			throws RegistrationNotFoundException, InputMismatchException;

	/**
	 * @return creates a new registrationId
	 */
	String registerCustomer();
}
