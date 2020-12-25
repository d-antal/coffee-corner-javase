package com.cognizant.coffeecorner.task;

import java.util.Scanner;

import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PayService;
import com.cognizant.coffeecorner.task.service.PayServiceImpl;

public class CoffeeCornerApplication {

	private PayService priceService;

	public CoffeeCornerApplication(PayService priceService) {
		this.priceService = priceService;
	}

	public static void main(String[] args) {

		CoffeeCornerApplication application = new CoffeeCornerApplication(new PayServiceImpl(new StampCardRepository(), new Scanner(System.in)));

		boolean stopApp = false;
		while (!stopApp) {
			try {
				application.priceService.payPurchase();
			} catch (Exception e) {
				stopApp = true;
			}
		}
	}

}
