package com.cognizant.coffeecorner.task.service;

import java.util.Queue;

public interface PriceService {

	Double getFinalPrice(String name, Queue<Integer> savedCustomerChoices );
}
