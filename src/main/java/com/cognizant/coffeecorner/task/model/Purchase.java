package com.cognizant.coffeecorner.task.model;

public class Purchase {

	private Double finalPrice;
	private Double lastExtraSelected;
	private boolean beverageSelected;
	private boolean snackSelected;
	private boolean milkSelected;
	private boolean roastedSelected;
	private boolean beverageDiscount;

	public Purchase(Double finalPrice, Double lastExtraSelected, boolean beverageSelected, boolean snackSelected, boolean milkSelected, boolean roastedSelected, boolean beverageDiscount ) {
		this.finalPrice = finalPrice;
		this.lastExtraSelected = lastExtraSelected;
		this.beverageSelected = beverageSelected;
		this.snackSelected = snackSelected;
		this.milkSelected = milkSelected;
		this.roastedSelected = roastedSelected;
		this.beverageDiscount= beverageDiscount;
	}

	public Double getFinalPrice() {
		return Math.floor(finalPrice * 100) / 100;
	}

	public void setFinalPrice(Double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Double getLastExtraSelected() {
		return lastExtraSelected;
	}

	public void setLastExtraSelected(Double lastExtraSelected) {
		this.lastExtraSelected = lastExtraSelected;
	}

	public boolean isBeverageSelected() {
		return beverageSelected;
	}

	public void setBeverageSelected(boolean beverageSelected) {
		this.beverageSelected = beverageSelected;
	}

	public boolean isSnackSelected() {
		return snackSelected;
	}

	public void setSnackSelected(boolean snackSelected) {
		this.snackSelected = snackSelected;
	}

	public boolean isMilkSelected() {
		return milkSelected;
	}

	public void setMilkSelected(boolean milkSelected) {
		this.milkSelected = milkSelected;
	}

	public boolean isRoastedSelected() {
		return roastedSelected;
	}

	public void setRoastedSelected(boolean roastedSelected) {
		this.roastedSelected = roastedSelected;
	}

	public boolean isBeverageDiscount() {
		return beverageDiscount;
	}

	public void setBeverageDiscount(boolean beverageDiscount) {
		this.beverageDiscount = beverageDiscount;
	}		
}

