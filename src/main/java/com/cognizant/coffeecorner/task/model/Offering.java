package com.cognizant.coffeecorner.task.model;

public enum Offering {
	COFFEE_SMALL(2.50), COFFEE_MEDIUM(3.00), BACON_ROLL(4.50), COFFEE_LARGE(3.50), ORANGE_JUICE(3.95), EXTRA_MILK(
			0.30), FOAMED_MILK(0.50), SPECIAL_ROAST_COFFEE(0.90);

	private final Double price;

	private Offering(Double price) {
		this.price = price;
	}

	public Double getPrice() {
		return this.price;
	}
}
