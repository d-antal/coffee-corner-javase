package com.cognizant.coffeecorner.task;

import java.util.Scanner;

import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PriceService;
import com.cognizant.coffeecorner.task.service.PriceServiceImpl;

public class CoffeeCornerApplication {

	private PriceService priceService;

	public CoffeeCornerApplication(PriceService priceService) {
		this.priceService = priceService;
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		CoffeeCornerApplication application = new CoffeeCornerApplication(
				new PriceServiceImpl(new StampCardRepository(), new Scanner(System.in)));

		while (true) {
			System.out.println("============================");
			System.out.println("Customer (stamp card) name :");
			System.out.println("============================");

			Double finalPrice = application.priceService.getFinalPrice(scanner.nextLine(), null);

			System.out.println(finalPrice);
		}
	}

}
