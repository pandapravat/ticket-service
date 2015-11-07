package com.pkp.ticketservice.service.test;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;

/**
 * Test class for searching ticket service seats
 * @author pravat
 *
 */
public class TicketServiceImplTest_Search extends TicketServiceBaseTest{

	// Search seats
	@Test
	public void testNumSeatsAvailable() {
		assertTrue(6250 == service.numSeatsAvailable(Optional.empty())); // total 6250 seats
		assertTrue(1250 == service.numSeatsAvailable(Optional.of(1))); // 1250 seats in Orchestra level
		assertTrue(2000 == service.numSeatsAvailable(Optional.of(2))); // 2000 seats in Main level
		assertTrue(1500 == service.numSeatsAvailable(Optional.of(3))); // 1500 seats in Balcony 1 level
		assertTrue(1500 == service.numSeatsAvailable(Optional.of(4))); // 1500 seats in Balcony 2 level
	}

	@Test(expected=RuntimeException.class)
	public void testNumSeatsAvailable_invalid_level() {
		service.numSeatsAvailable(Optional.of(5)); 
	}

	@Test(expected=RuntimeException.class)
	public void testNumSeatsAvailable_invalid_level2() {
		service.numSeatsAvailable(Optional.of(0)); 
	}

}
