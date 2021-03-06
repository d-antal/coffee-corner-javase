package com.cognizant.coffeecorner.task;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;
import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PayService;
import com.cognizant.coffeecorner.task.service.PayServiceImpl;
import com.cognizant.coffeecorner.task.service.StampCardService;
import com.cognizant.coffeecorner.task.service.StampCardServiceImpl;

public class PayServiceTest {

	private PayService priceService;
	private StampCardService stampCardService;
	private Map<Double, Queue<Object>> savedCustomerChoices;
	private final static String TEXT_INPUT = "abc";
	private final static String ID_PREFIX = "cc";

	@Before
	public void init() {
		this.savedCustomerChoices = new HashMap<Double, Queue<Object>>();
		this.stampCardService = new StampCardServiceImpl(new StampCardRepository());
		this.priceService = new PayServiceImpl(this.stampCardService, new Scanner(System.in));

		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, 4), 2.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 4, 4), 3.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 4, 4), 3.50);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 4), 2.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 1, 4, 4), 2.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 2, 4, 4), 2.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 1, 4, 4), 3.3);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 1, 1, 4, 4), 3.3);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 1, 2, 4, 4), 3.3);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 1, 4, 4), 3.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 1, 1, 4, 4), 3.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 1, 2, 4, 4), 3.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 3, 4), 3.7);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 1, 3, 4), 4.2);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 1, 3, 4), 4.7);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 2, 4, 4), 3.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 2, 4, 4), 3.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 2, 4, 4), 4.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 3, 4, 4), 3.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 2, 4, 4), 4.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 3, 3, 4, 4), 3.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 3, 4, 4), 3.9);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 3, 3, 4, 4), 3.9);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 3, 4, 4), 4.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 3, 3, 4, 4), 4.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(2, 4), 4.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 4), 3.95);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 3, 3, 3, 3, 4), 15.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, 2, 4), 7.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 2, 4), 7.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 4, 2, 4), 7.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 1, 4, 2, 4), 7.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 4, 2, 4), 8.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 1, 4, 2, 4), 8.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 2, 3, 4), 10.95);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, 2, 3, 4), 10.95);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 1, 1, 1, 4, 4), 5.6);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 4), 11.2);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 3, 3, 3, 1, 1, 1, 4, 4), 15.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 4, 3, 4), 11.2);
	}

	private void addTestParams(Map<Double, Queue<Object>> savedCustomerChoices, List<Object> steps, Double expectedPrice) {
		Queue<Object> queue = new LinkedList<>();
		queue.addAll(steps);
		savedCustomerChoices.put(expectedPrice, queue);
	}

	@Test
	public void testPurchase() throws Exception {
		for (Double expectedPrice : savedCustomerChoices.keySet()) {
			Double price = priceService.purchase(this.stampCardService.saveNewId(), savedCustomerChoices.get(expectedPrice), true);

			assertTrue(expectedPrice.equals(price));
		}
	}

	@Test(expected = RegistrationNotFoundException.class)
	public void testPurchaseWhenRegistartionDoesNotExist() throws Exception {
		for (Double expectedPrice : savedCustomerChoices.keySet()) {
			priceService.purchase("cc101", savedCustomerChoices.get(expectedPrice), true);
		}
	}

	@Test(expected = InputMismatchException.class)
	public void testPurchaseWhenInputIsWrong() throws Exception {
		this.savedCustomerChoices = new HashMap<Double, Queue<Object>>();
		this.addTestParams(savedCustomerChoices, Arrays.asList(TEXT_INPUT, 1, 4, 4), 2.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, TEXT_INPUT, 4, 4), 2.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, TEXT_INPUT, 4), 2.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, TEXT_INPUT), 2.5);

		String testRegistrationId = this.stampCardService.saveNewId();
		for (Double expectedPrice : savedCustomerChoices.keySet()) {
			priceService.purchase(testRegistrationId, savedCustomerChoices.get(expectedPrice), true);
		}
	}
}

