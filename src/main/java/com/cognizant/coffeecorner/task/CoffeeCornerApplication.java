package com.cognizant.coffeecorner.task;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.cognizant.coffeecorner.task.exception.RegistrationNotFoundException;
import com.cognizant.coffeecorner.task.model.PriceConstants;
import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.PayService;
import com.cognizant.coffeecorner.task.service.PayServiceImpl;
import com.cognizant.coffeecorner.task.service.StampCardServiceImpl;

public class CoffeeCornerApplication {

	private PayService priceService;

	public CoffeeCornerApplication(PayService priceService) {
		this.priceService = priceService;
	}

	public static void main(String[] args) {
		purchaseDemo();
	}

	/**
	 * Demo (demonstrates a controller/service call) allows customer to choose again
	 * in case of InputMismatchException or RegistrationNotFoundException, stops
	 * process in case of "server error" Currently the saved choices option
	 * (automatic) is used for the tests, manual selection is available.
	 */
	private static void purchaseDemo() {
		Scanner scanner = new Scanner(System.in);
	
		CoffeeCornerApplication application = new CoffeeCornerApplication(new PayServiceImpl(new StampCardServiceImpl(new StampCardRepository()), scanner));

		boolean stopApp = false;
		while (!stopApp) {
			try {
				application.priceService.purchase(null, null, false);
			} catch (RegistrationNotFoundException e) {
				printMessage(e.getMessage());
			} catch (InputMismatchException e) {
				String wrongInput = scanner.next();
				printMessage(PriceConstants.WRONG_INPUT_MESSAGE + wrongInput);
			} catch (Exception e) {
				printMessage(PriceConstants.SERVER_ERROR_MESSAGE);
			}
		}
	}

	private static void printMessage(String message) {
		System.out.println(message);
	}
}
