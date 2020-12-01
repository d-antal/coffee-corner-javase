package com.cognizant.coffeecorner.task.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.cognizant.coffeecorner.task.model.Offering;
import com.cognizant.coffeecorner.task.model.PriceConstants;
import com.cognizant.coffeecorner.task.repository.StampCardRepository;

public class PriceServiceImpl implements PriceService {

	private StampCardRepository stampCardRepository;
	private Scanner scanner;

	public PriceServiceImpl(StampCardRepository stampCardRepository, Scanner scanner) {
		this.stampCardRepository = stampCardRepository;
		this.scanner = scanner;
	}

	private int selectProduct(Queue<Integer> savedCustomerChoices) {
		return savedCustomerChoices == null ? scanner.nextInt() : savedCustomerChoices.poll();
	}

	@Override
	public Double getFinalPrice(String name, Queue<Integer> savedCustomerChoices) {
		Double finalPrice = 0.0;
		boolean beverageSelected = false;
		boolean snackSelected = false;
		Double lastExtraSelected = 0.0;
		int productSelected = 0;
		
		while (productSelected != 4) {
			selectProductHeader();
			productSelected = selectProduct(savedCustomerChoices);
			switch (productSelected) {
			case 1:
				beverageSelected = true;
				List<Double> prices = getCoffePrice(savedCustomerChoices);
				finalPrice += prices.get(0);
				lastExtraSelected = prices.get(1);
				break;
			case 2:
				snackSelected = true;
				finalPrice += Offering.BACON_ROLL.getPrice();
				break;
			case 3:
				beverageSelected = true;
				stampCardRepository.addToCard(name);
				finalPrice += getJuicePrice(name);
				break;
			case 4:
				break;
			default:
				System.out.println(PriceConstants.SELECT_MAX_4_MESSAGE);
			}
		}
		if ((beverageSelected && snackSelected) && lastExtraSelected > 0) {
			finalPrice = finalPrice - lastExtraSelected;
			System.out.println(PriceConstants.DISCOUT_MESSAGE_FREE_EXTRA);
		}
		return (Math.floor(finalPrice * 100) / 100);
	}

	private List<Double> getCoffePrice(Queue<Integer> savedCustomerChoices) {
		Double price = 0.0;
		selectCoffeeHeader();
		int productSelected = 0;
		while (productSelected < 1 || productSelected > 3) {
			productSelected = selectProduct(savedCustomerChoices);
			switch (productSelected) {
			case 1:
				price += Offering.COFFEE_SMALL.getPrice();
				break;
			case 2:
				price += Offering.COFFEE_MEDIUM.getPrice();
				break;
			case 3:
				price += Offering.COFFEE_LARGE.getPrice();
				break;
			default:
				System.out.println(PriceConstants.SELECT_MAX_3_MESSAGE);
			}
		}
		return getExtra(price, savedCustomerChoices);
	}

	private List<Double> getExtra(Double price, Queue<Integer> savedCustomerChoices) {
		int extraSelected = 0;
		List<String> selectedList = new ArrayList<>();
		List<Double> priceList = Arrays.asList(price, 0.0);
		while (extraSelected != 4
				&& !(selectedList.contains(PriceConstants.MILK) && selectedList.contains(PriceConstants.ROASTED))) {
			selectExtraHeader();
			extraSelected = selectProduct(savedCustomerChoices);

			switch (extraSelected) {
			case 1:
				calculatePrices(selectedList, priceList, PriceConstants.MILK, Offering.EXTRA_MILK.getPrice(),
						PriceConstants.MILK_ALREADY_SELECTED);
				break;
			case 2:
				calculatePrices(selectedList, priceList, PriceConstants.MILK, Offering.FOAMED_MILK.getPrice(),
						PriceConstants.MILK_ALREADY_SELECTED);
				break;

			case 3:
				calculatePrices(selectedList, priceList, PriceConstants.ROASTED,
						Offering.SPECIAL_ROAST_COFFEE.getPrice(), PriceConstants.ROAST_ALREADY_SELECTED);
			case 4:
				break;
			default:
				System.out.println(PriceConstants.SELECT_MAX_4_MESSAGE);
			}
		}
		return priceList;
	}

	private void calculatePrices(List<String> selectedList, List<Double> priceList, String selected, Double extraPrice,
			String errorMessage) {
		if (!selectedList.contains(selected)) {
			selectedList.add(selected);
			priceList.set(0, priceList.get(0) + extraPrice);
			priceList.set(1, extraPrice);
		} else {
			System.out.println(errorMessage);
		}
	}

	private Double getJuicePrice(String name) {
		return checkDiscount(name) ? 0.0 : Offering.ORANGE_JUICE.getPrice();
	}

	private boolean checkDiscount(String name) {
		if (stampCardRepository.getStampByCustomerName(name) == 5) {
			stampCardRepository.resetDiscount(name);
			System.out.println(PriceConstants.DISCOUT_MESSAGE_JUICES);
			return true;
		}
		return false;
	}

	private void selectExtraHeader() {
		System.out.println("====================================");
		System.out.println("|          Select Extra            |");
		System.out.println("====================================");
		System.out.println("| 1. Extra milk 0.30 CHF           |");
		System.out.println("| 2. Foamed milk 0.50 CHF          |");
		System.out.println("| 3. Special roast coffee 0.90 CHF |");
		System.out.println("====================================");
		System.out.println("| 4. Finish                        |");
		System.out.println("====================================");
	}

	private void selectCoffeeHeader() {
		System.out.println("============================");
		System.out.println("|     Select Coffee         |");
		System.out.println("============================");
		System.out.println("| 1. Small  2.50 CHF  |");
		System.out.println("| 2. Medium 3.00 CHF  |");
		System.out.println("| 3. Large  3.50 CHF  |");
		System.out.println("============================");
	}

	private void selectProductHeader() {
		System.out.println("============================");
		System.out.println("|       Select Product     |");
		System.out.println("============================");
		System.out.println("| Products:                |");
		System.out.println("|        1. Coffee         |");
		System.out.println("|        2. Bacon Roll     |");
		System.out.println("|        3. Juice          |");
		System.out.println("============================");
		System.out.println("|        4. Finish order   |");
		System.out.println("============================");
	}

}
