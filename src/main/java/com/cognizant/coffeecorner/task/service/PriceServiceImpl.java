package com.cognizant.coffeecorner.task.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;
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

	private int selectProduct(Queue<Object> savedCustomerChoices) throws InputMismatchException {
		if (savedCustomerChoices != null && savedCustomerChoices.peek() instanceof Integer == false) {
			throw new InputMismatchException();
		}
		return savedCustomerChoices == null ? scanner.nextInt() : (int) savedCustomerChoices.poll();
	}

	public String registerCustomer() {
		return stampCardRepository.registerCustomer();
	}

	private Integer validateRegistration(String regName) throws RegistrationNotFoundException {
		if (stampCardRepository.getPointsByCardName(regName) == null) {
			throw new RegistrationNotFoundException(PriceConstants.REGISTRATION_NOT_FOUND);
		}
		return stampCardRepository.getPointsByCardName(regName);

	}

	@Override
	public Double getFinalPrice(String regName, Queue<Object> savedCustomerChoices, boolean useSaveChoices)
			throws RegistrationNotFoundException, InputMismatchException {
		Double finalPrice = 0.0;
		boolean beverageSelected = false;
		boolean snackSelected = false;
		Double lastExtraSelected = 0.0;
		int productSelected = 0;
		validateRegistration(regName);
		while (productSelected != 4) {
			try {
				createHeader(PriceConstants.PRODUCT, Offering.STANDARD_SMALL, Offering.BACON_ROLL, Offering.ORANGE_JUICE);
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
					stampCardRepository.addToCard(regName);
					finalPrice += getJuicePrice(regName);
					break;
				case 4:
					break;
				case 5:
					printMessage(PriceConstants.EXIT_MESSAGE);
					return -1.0;
				default:
					printMessage(PriceConstants.SELECT_MAX_4_MESSAGE);
				}
			} catch (InputMismatchException e) {
				if (savedCustomerChoices != null && useSaveChoices) {
					throw new InputMismatchException();
				}
				String wrongInput = scanner.next();
				printMessage(PriceConstants.WRONG_INPUT_MESSAGE + wrongInput);
				continue;
			}
		}

		if ((beverageSelected && snackSelected) && lastExtraSelected > 0) {
			finalPrice = finalPrice - lastExtraSelected;
			printMessage(PriceConstants.DISCOUT_MESSAGE_FREE_EXTRA);
		}

		finalPrice = (Math.floor(finalPrice * 100) / 100);
		printMessage(PriceConstants.CUSTOMER + regName + "  Final price : " + finalPrice + PriceConstants.CURRENCY);
		return finalPrice;
	}

	private List<Double> getCoffePrice(Queue<Object> savedCustomerChoices) {
		Double price = 0.0;
		createHeader(PriceConstants.COFFEE, Offering.COFFEE_SMALL, Offering.COFFEE_MEDIUM, Offering.COFFEE_LARGE);
		int productSelected = 0;
		while (productSelected < 1 || productSelected > 3) {
			productSelected = selectProduct(savedCustomerChoices);
			Double selectedPrice = productSelected == 1 ? Offering.COFFEE_SMALL.getPrice()
					             : productSelected == 2 ? Offering.COFFEE_MEDIUM.getPrice() 
					             : productSelected == 3 ? Offering.COFFEE_LARGE.getPrice() : 0;
			price += selectedPrice;
			printMessage(selectedPrice == 0 ? PriceConstants.SELECT_MAX_3_MESSAGE : null);
		}
		return getExtra(price, savedCustomerChoices);
	}

	private List<Double> getExtra(Double price, Queue<Object> savedCustomerChoices) {
		int extraSelected = 0;
		List<String> selectedList = new ArrayList<>();
		List<Double> priceList = Arrays.asList(price, 0.0);
		while (extraSelected != 4 && !(selectedList.contains(PriceConstants.MILK) && selectedList.contains(PriceConstants.ROASTED))) {
			createHeader(PriceConstants.EXTRA, Offering.EXTRA_MILK, Offering.FOAMED_MILK, Offering.SPECIAL_ROAST_COFFEE);
			extraSelected = selectProduct(savedCustomerChoices);

			printMessage(extraSelected == 1	? calculatePrices(selectedList, priceList, PriceConstants.MILK, Offering.EXTRA_MILK.getPrice(), PriceConstants.MILK_ALREADY_SELECTED)
					   : extraSelected == 2 ? calculatePrices(selectedList, priceList, PriceConstants.MILK, Offering.FOAMED_MILK.getPrice(), PriceConstants.MILK_ALREADY_SELECTED)
					   : extraSelected == 3	? calculatePrices(selectedList, priceList, PriceConstants.ROASTED, Offering.SPECIAL_ROAST_COFFEE.getPrice(), PriceConstants.ROAST_ALREADY_SELECTED)
					   : extraSelected == 4 ? null : PriceConstants.SELECT_MAX_4_MESSAGE);
		}
		return priceList;
	}

	private String calculatePrices(List<String> selectedList, List<Double> priceList, String selected, Double extraPrice, String errorMessage) {
		if (!selectedList.contains(selected)) {
			selectedList.add(selected);
			priceList.set(0, priceList.get(0) + extraPrice);
			priceList.set(1, extraPrice);
		} else {
			return errorMessage;
		}
		return " ";
	}

	private Double getJuicePrice(String name) {
		return checkDiscount(name) ? 0.0 : Offering.ORANGE_JUICE.getPrice();
	}

	private boolean checkDiscount(String name) {
		if (stampCardRepository.getPointsByCardName(name) == 5) {
			stampCardRepository.resetDiscount(name);
			printMessage(PriceConstants.DISCOUT_MESSAGE_JUICES);
			return true;
		}
		return false;
	}

	private void createHeader(String selected, Offering firstProduct, Offering secondProduct, Offering thirdProduct) {
		StringBuilder header = new StringBuilder();
		String line = ("============================\n");
		String space = "  ";
		String newLine = "\n";
		String finishOrder = !selected.equals(PriceConstants.COFFEE) ? selected.equals(PriceConstants.EXTRA) ? "4. Finish" : "4. Pay" : " ";
		header.append(line);
		createLine(header, Arrays.asList(selected, newLine));
		header.append(line);
		createLine(header, Arrays.asList("1. ", firstProduct.getProductName(), space, firstProduct.getPrice().toString(), PriceConstants.CURRENCY, newLine));
		createLine(header, Arrays.asList("2. ", secondProduct.getProductName(), space, secondProduct.getPrice().toString(), PriceConstants.CURRENCY, newLine));
		createLine(header, Arrays.asList("3. ", thirdProduct.getProductName(), space, thirdProduct.getPrice().toString(), PriceConstants.CURRENCY, newLine));
		header.append(line);
		createLine(header, Arrays.asList(finishOrder, newLine));
		header.append(line);
		if (selected.equals(PriceConstants.PRODUCT)) {
			createLine(header, Arrays.asList("5. ", "Exit", newLine));
		}
		printMessage(header.toString());
	}

	private void createLine(StringBuilder header, List<String> inputs) {
		inputs.forEach(input -> header.append(input));
	}

	private void printMessage(String message) {
		if (null != message) {
			System.out.println(message);
		}
	}

}
