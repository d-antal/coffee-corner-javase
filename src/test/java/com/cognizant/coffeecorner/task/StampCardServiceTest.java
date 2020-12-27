package com.cognizant.coffeecorner.task;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.cognizant.coffeecorner.task.repository.StampCardRepository;
import com.cognizant.coffeecorner.task.service.StampCardService;
import com.cognizant.coffeecorner.task.service.StampCardServiceImpl;

public class StampCardServiceTest {

	private StampCardService stampCardService;
	private final static String ID_PREFIX = "cc";

	@Before
	public void init() {
		this.stampCardService = new StampCardServiceImpl(new StampCardRepository());
	}

	@Test
	public void testAddToCard() {
		String regID = stampCardService.saveNewId();
		stampCardService.addToCard(regID);
		Integer points = stampCardService.getPointsByCardId(regID);

		assertTrue(1 == points);
	}

	@Test
	public void testRegisterCustomer() {
		int registartions = 5;
		for (int i = 0; i < registartions; i++) {
			this.stampCardService.saveNewId();
		}

		String expectedId = ID_PREFIX + registartions++;
		String createdId = this.stampCardService.saveNewId();

		assertTrue(expectedId.equals(createdId));
	}

	@Test
	public void testResetDiscount() {
		String regID = stampCardService.saveNewId();
		stampCardService.addToCard(regID);
		Integer currentPoints = stampCardService.getPointsByCardId(regID);

		assertTrue(currentPoints > 0);

		stampCardService.resetDiscount(regID);

		Integer pointsAfterReset = stampCardService.getPointsByCardId(regID);

		assertTrue(0 == pointsAfterReset);
	}

	@Test
	public void testGetPointsByCardName() {
		String createdId = this.stampCardService.saveNewId();
		stampCardService.addToCard(createdId);

		assertTrue(1 == stampCardService.getPointsByCardId(createdId));
	}

}
