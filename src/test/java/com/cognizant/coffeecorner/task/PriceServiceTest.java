package com.cognizant.coffeecorner.task;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PriceService;
import com.cognizant.coffeecorner.task.service.PriceServiceImpl;

public class PriceServiceTest {

	private PriceService priceService;
	private StampCardRepository stampCardRepository;
	private Map<Double, Queue<Integer>> savedCustomerChoices;

	@Before
	public void init() {
		this.savedCustomerChoices = new HashMap<Double, Queue<Integer>>();
		this.stampCardRepository = new StampCardRepository();
		this.priceService = new PriceServiceImpl(stampCardRepository, new Scanner(System.in));
		
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, 4), 2.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 2, 4, 4), 3.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 3, 4, 4), 3.5);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 4), 2.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 1, 4, 4), 2.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 2, 4, 4), 3.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 2, 2, 4, 4), 3.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 3, 4, 4), 3.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 3, 3, 4, 4), 3.4);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 3, 4, 4), 3.7);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 1, 4, 2, 4), 7.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(1, 1, 4, 2, 4), 7.0);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 3, 3, 3, 4), 15.8);
		this.addTestParams(savedCustomerChoices, Arrays.asList(3, 3, 3, 3, 3, 4), 15.8);
	}

	private void addTestParams(Map<Double, Queue<Integer>> savedCustomerChoices, List<Integer> steps,
			Double expectedPrice) {
		Queue<Integer> queue = new LinkedList<>();
		queue.addAll(steps);
		savedCustomerChoices.put(expectedPrice, queue);
	}

	@Test
	public void testGetFinalPrice() {
		for (Double expectedPrice : savedCustomerChoices.keySet()) {
			Double price = priceService.getFinalPrice("TestCustomer", savedCustomerChoices.get(expectedPrice));
			assertTrue(expectedPrice.equals(price));
		}

	}
}
