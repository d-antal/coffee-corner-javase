package com.cognizant.coffeecorner.task.service;

import com.cognizant.coffeecorner.task.repository.StampCardRepository;

public class StampCardServiceImpl implements StampCardService {

	private StampCardRepository stampCardRepository;

	public StampCardServiceImpl(StampCardRepository stampCardRepository) {
		this.stampCardRepository = stampCardRepository;
	}

	@Override
	public void addToCard(String name) {
		stampCardRepository.addToCard(name);
	}

	@Override
	public void resetDiscount(String name) {
		stampCardRepository.resetDiscount(name);
	}

	@Override
	public Integer getPointsByCardId(String cardId) {
		return stampCardRepository.getPointsByCardName(cardId);
	}

	@Override
	public String saveNewId() {
		return stampCardRepository.registerCustomer();
	}
}

