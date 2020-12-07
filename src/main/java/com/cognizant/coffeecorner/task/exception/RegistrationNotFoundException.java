package com.cognizant.coffeecorner.task.exception;

public class RegistrationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RegistrationNotFoundException(String message) {
		super(message);
	}
}
