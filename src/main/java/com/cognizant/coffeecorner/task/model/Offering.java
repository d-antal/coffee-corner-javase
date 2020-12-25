package com.cognizant.coffeecorner.task.model;

public enum Offering {
	DEFAULT_COFFEE("Coffee from", 2.50), 
	COFFEE_SMALL("Small coffee", 2.50), 
	COFFEE_MEDIUM("Medium coffee", 3.00), 
	COFFEE_LARGE("Large coffee", 3.50),
	BACON_ROLL("Bacon roll", 4.50), 
	ORANGE_JUICE("Orange juice", 3.95), 
	EXTRA_MILK("Extra milk", 0.30), 
	FOAMED_MILK("Extra foamed milk", 0.50),
	SPECIAL_ROAST_COFFEE("Extra special roast", 0.90);

	private final Double price;
	private final String productName;

	private Offering(String productName, Double price) {
		this.price = price;
		this.productName = productName;
	}

	public Double getPrice() {
		return this.price;
	}

	public String getProductName() {
		return this.productName;
	}
}
