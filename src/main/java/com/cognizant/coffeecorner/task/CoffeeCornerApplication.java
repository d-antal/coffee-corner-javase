package com.cognizant.coffeecorner.task;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;
import com.cognizant.coffeecorner.task.model.PriceConstants;
import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PriceService;
import com.cognizant.coffeecorner.task.service.PriceServiceImpl;

public class CoffeeCornerApplication {

	private static final String SERVER_ERROR_MESSAGE = "Unfortunately we can not process your order due to technical issues. Please try again later. Thank you!";
	private static final String WRONG_NUMBER_SELECTED = "Please select 1 or 2";
	private PriceService priceService;

	public CoffeeCornerApplication(PriceService priceService) {
		this.priceService = priceService;
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CoffeeCornerApplication application = new CoffeeCornerApplication(new PriceServiceImpl(new StampCardRepository(), new Scanner(System.in)));
		double finalPrice = -1;
		String registrationName = null;
		boolean stopApp = false;
		while (!stopApp) {
			try {
				if (finalPrice == -1) {
					registrationHeader();
					int selected = scanner.nextInt();
					if (selected != 1 && selected != 2) {
						printMessage(WRONG_NUMBER_SELECTED);
						continue;
					}				
					if (selected == 1) {
						registeredCustomerHeader();
						registrationName = new Scanner(System.in).nextLine();
					} else {
						registrationName = application.priceService.registerCustomer();
					}
				}
				finalPrice = application.priceService.getFinalPrice(registrationName, null, false);

			} catch (RegistrationNotFoundException e) {
				printMessage(e.getMessage());
				finalPrice = -1;
				application = new CoffeeCornerApplication(new PriceServiceImpl(new StampCardRepository(), new Scanner(System.in)));
			} catch (InputMismatchException e) {
				String wrongInput = scanner.nextLine();
				printMessage(PriceConstants.WRONG_INPUT_MESSAGE + wrongInput);
				finalPrice = -1;
				application = new CoffeeCornerApplication(new PriceServiceImpl(new StampCardRepository(), new Scanner(System.in)));
			} catch (Exception e) {
				printMessage(SERVER_ERROR_MESSAGE + e);
				stopApp = true;
			}
		}

	}

	private static void registeredCustomerHeader() {
		StringBuilder header = new StringBuilder();
		header = new StringBuilder();
		header.append("===================================\n");
		header.append("Please enter your registartion id :\n");
		header.append("===================================\n");
		printMessage(header.toString());
	}

	private static void registrationHeader() {
		StringBuilder header = new StringBuilder();
		header.append("============================\n");
		header.append("        Coffee Corner       \n ");
		header.append("============================\n");
		header.append("1. Registered customer\n");
		header.append("2. New customer\n");
		header.append("============================\n");
		printMessage(header.toString());
	}

	private static void printMessage(String message) {
		System.out.println(message);
	}
}
