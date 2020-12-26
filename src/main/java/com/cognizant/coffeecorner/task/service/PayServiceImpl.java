package com.cognizant.coffeecorner.task.service;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;
import com.cognizant.coffeecorner.task.model.Offering;
import com.cognizant.coffeecorner.task.model.PriceConstants;
import com.cognizant.coffeecorner.task.model.Purchase;
import com.cognizant.coffeecorner.task.repository.StampCardRepository;

public class PayServiceImpl implements PayService {

	private StampCardRepository stampCardRepository;
	private Scanner scanner;

	public PayServiceImpl(StampCardRepository stampCardRepository, Scanner scanner) {
		this.stampCardRepository = stampCardRepository;
		this.scanner = scanner;
	}

	private int selectProduct(Queue<Object> savedCustomerChoices) throws InputMismatchException {
		if (savedCustomerChoices != null && savedCustomerChoices.peek() instanceof Integer == false) {
			throw new InputMismatchException();
		}
		return savedCustomerChoices == null ? scanner.nextInt() : (int) savedCustomerChoices.poll();
	}

	private Integer validateRegistration(String regId) throws RegistrationNotFoundException {
		if (stampCardRepository.getPointsByCardName(regId) == null) {
			throw new RegistrationNotFoundException(PriceConstants.REGISTRATION_NOT_FOUND);
		}
		return stampCardRepository.getPointsByCardName(regId);
	}

	private Double calculatePrice(String regId, Queue<Object> savedCustomerChoices, boolean useSaveChoices) throws RegistrationNotFoundException, InputMismatchException {
		int productSelected = 0;
		validateRegistration(regId);
		Purchase purchase = new Purchase();
		while (productSelected != 4) {
			try {
				createHeader(PriceConstants.PRODUCT, Offering.DEFAULT_COFFEE, Offering.BACON_ROLL, Offering.ORANGE_JUICE);
				productSelected = selectProduct(savedCustomerChoices);

				if (productSelected == 4) {
					break;
				}
				if (productSelected == 5) {
					printMessage(PriceConstants.EXIT_MESSAGE);
					return -1.0;
				}
				if (productSelected < 1 || productSelected > 5) {
					printMessage(PriceConstants.SELECT_MAX_4_MESSAGE);
					continue;
				}
				purchase = productSelected == 1 ? addCoffeeToPurchase(savedCustomerChoices, purchase, regId)
						 : productSelected == 2 ? addToPurchase(purchase, 2, regId) : addToPurchase(purchase, 3, regId);
			} catch (InputMismatchException e) {
				if (savedCustomerChoices != null && useSaveChoices) {
					throw e;
				}
				String wrongInput = scanner.next();
				printMessage(PriceConstants.WRONG_INPUT_MESSAGE + wrongInput);
				continue;
			}
		}
		handleDiscounts(purchase, regId);
		return purchase.getFinalPrice();
	}

	private void handleDiscounts(Purchase purchase, String regName) {
		if ((purchase.isBeverageSelected() && purchase.isSnackSelected()) && purchase.getLastExtraSelected() > 0) {
			purchase.setFinalPrice(purchase.getFinalPrice() - purchase.getLastExtraSelected());
			printMessage(PriceConstants.DISCOUT_MESSAGE_FREE_EXTRA);
		} else if (purchase.isBeverageDiscount()) {
			printMessage(PriceConstants.BEVERAGES_DISCOUNT);
		}
		printMessage(PriceConstants.CUSTOMER + regName + "  Final price : " + purchase.getFinalPrice() + PriceConstants.CURRENCY);
	}

	private Purchase addCoffeeToPurchase(Queue<Object> savedCustomerChoices, Purchase purchase, String regName) {
		createHeader(PriceConstants.COFFEE, Offering.COFFEE_SMALL, Offering.COFFEE_MEDIUM, Offering.COFFEE_LARGE);
		purchase.setBeverageSelected(true);
		int productSelected = 0;
		boolean freeCoffee = false;
		while (productSelected < 1 || productSelected > 3) {
			productSelected = selectProduct(savedCustomerChoices);
			Double selectedPrice = productSelected == 1 ? Offering.COFFEE_SMALL.getPrice()
								 : productSelected == 2 ? Offering.COFFEE_MEDIUM.getPrice() 
								 : productSelected == 3 ? Offering.COFFEE_LARGE.getPrice() : 0;
			if (productSelected > 0) {
				stampCardRepository.addToCard(regName);
				freeCoffee = freeDrinkDiscount(regName, purchase);
				if (!freeCoffee) {
					purchase.setFinalPrice(purchase.getFinalPrice() + selectedPrice);
				}
			}
			printMessage(selectedPrice == 0 ? PriceConstants.SELECT_MAX_3_MESSAGE : PriceConstants.COFFEE_TYPE_SELECTED + productSelected);
		}
		addExtraToCoffee(purchase, savedCustomerChoices, freeCoffee);
		return purchase;
	}

	private Purchase addToPurchase(Purchase purchase, int selected, String regName) {
		if (selected == 2) {
			purchase.setSnackSelected(true);
			purchase.setFinalPrice(purchase.getFinalPrice() + Offering.BACON_ROLL.getPrice());
		} else {
			purchase.setBeverageSelected(true);
			stampCardRepository.addToCard(regName);
			if (!freeDrinkDiscount(regName, purchase)) {
				purchase.setFinalPrice(purchase.getFinalPrice() + Offering.ORANGE_JUICE.getPrice());
			}
		}
		return purchase;
	}

	private void addExtraToCoffee(Purchase purchase, Queue<Object> savedCustomerChoices, boolean freeCoffee) {
		int extraSelected = 0;
		while (extraSelected != 4 && !(purchase.isMilkSelected() && purchase.isRoastedSelected())) {
			createHeader(PriceConstants.EXTRA, Offering.EXTRA_MILK, Offering.FOAMED_MILK, Offering.SPECIAL_ROAST_COFFEE);
			extraSelected = selectProduct(savedCustomerChoices);
			printMessage(extraSelected == 1 ? addExtraPrice(purchase, PriceConstants.MILK, Offering.EXTRA_MILK.getPrice(), PriceConstants.MILK_ALREADY_SELECTED, freeCoffee)
					   : extraSelected == 2 ? addExtraPrice(purchase, PriceConstants.MILK, Offering.FOAMED_MILK.getPrice(), PriceConstants.MILK_ALREADY_SELECTED, freeCoffee)
					   : extraSelected == 3	? addExtraPrice(purchase, PriceConstants.ROASTED, Offering.SPECIAL_ROAST_COFFEE.getPrice(), PriceConstants.ROAST_ALREADY_SELECTED, freeCoffee)
					   : extraSelected == 4 ? PriceConstants.COFFEE_ORDER_FINISHED : PriceConstants.SELECT_MAX_4_MESSAGE);
		}
		purchase.setMilkSelected(false);
		purchase.setRoastedSelected(false);
	}

	private String addExtraPrice(Purchase purchase, String selected, Double extraPrice, String errorMessage, boolean freeCoffee) {
		if (PriceConstants.MILK.equals(selected) && !purchase.isMilkSelected() || PriceConstants.ROASTED.equals(selected) && !purchase.isRoastedSelected()) {
			if (PriceConstants.MILK.equals(selected)) {
				purchase.setMilkSelected(true);
			} else {
				purchase.setRoastedSelected(true);
			}
			if (!freeCoffee) {
				purchase.setFinalPrice(purchase.getFinalPrice() + extraPrice);
				purchase.setLastExtraSelected(extraPrice);
			}
			return PriceConstants.EXTRA_SELECTED + selected;
		} else {
			return errorMessage;
		}
	}

	private boolean freeDrinkDiscount(String name, Purchase purchase) {
		if (stampCardRepository.getPointsByCardName(name) == 5) {
			stampCardRepository.resetDiscount(name);
			purchase.setBeverageDiscount(true);
			return true;
		}
		return false;
	}

	@Override
	public Double purchase(String registrationName, Queue<Object> savedCustomerChoices, boolean useSavedChoices)
			throws Exception, RegistrationNotFoundException, InputMismatchException {
		double finalPrice = -1;
		boolean finishPurchase = false;
		while (!finishPurchase) {
			if (finalPrice == -1) {
				registrationHeader();
				int selected = useSavedChoices ? 1 : scanner.nextInt();
				if (selected != 1 && selected != 2) {
					printMessage(PriceConstants.WRONG_NUMBER_SELECTED);
					continue;
				}
				if (selected == 1) {
					registeredCustomerHeader();
					registrationName = useSavedChoices ? registrationName : new Scanner(System.in).nextLine();
				} else {
					registrationName = stampCardRepository.registerCustomer();
				}
			}
			finalPrice = calculatePrice(registrationName, savedCustomerChoices, useSavedChoices);
			finishPurchase = true;
		}
		return finalPrice;
	}

	private void registeredCustomerHeader() {
		StringBuilder header = new StringBuilder();
		header = new StringBuilder();
		header.append("===================================\n");
		header.append("Please enter your registration id :\n");
		header.append("===================================\n");
		printMessage(header.toString());
	}

	private void registrationHeader() {
		StringBuilder header = new StringBuilder();
		header.append("============================\n");
		header.append("        Coffee Corner       \n ");
		header.append("============================\n");
		header.append("1. Registered customer\n");
		header.append("2. New customer\n");
		header.append("============================\n");
		printMessage(header.toString());
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
		System.out.println(message);
	}
}
