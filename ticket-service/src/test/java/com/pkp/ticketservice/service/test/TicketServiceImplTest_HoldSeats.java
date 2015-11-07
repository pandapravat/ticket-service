package com.pkp.ticketservice.service.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * Test class for holding seats in ticket service
 * 
 * @author pravat
 *
 */
public class TicketServiceImplTest_HoldSeats extends TicketServiceBaseTest{
	/*
	 * tests that the seats are not availble after they are hold
	 * also checks that the seat status is changed to hold once held
	 */
	@Test
	public void testHoldSeatsInLevel() {
		SeatHold seatHold = service.findAndHoldSeats(10, Optional.empty(), Optional.empty(), "someone@somedomain.com");
		assertTrue(6240 == service.numSeatsAvailable(Optional.empty()));
		assertTrue(10 == seatHold.getSeats().size());
		
		List<Seat> seats = seatHold.getSeats();
		for (Seat aSeat : seats) {
			
			checkStatusOfSeat(aSeat.getLevel(), aSeat.getRowNum(), aSeat.getSeatNum(), Status.HOLD);
		}
	}
	
	/*
	 * tests that the seat hold ids are unique
	 */
	@Test
	public void testHoldSeatsInLevel_unique() {
		SeatHold seats = service.findAndHoldSeats(10, Optional.empty(), Optional.empty(), "someone@somedomain.com");
		SeatHold seats2 = service.findAndHoldSeats(10, Optional.empty(), Optional.empty(), "someone@somedomain.com");
		assertFalse(seats.getSeatHoldId() == seats2.getSeatHoldId());
	}

	/*
	 * tests that the seats are held then the response proves that
	 */
	@Test
	public void testHoldSeatsInLevel_validate() {
		SeatHold holdSeats = service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), "someone@somedomain.com");
		assertTrue(1 == holdSeats.getSeats().size());
		assertTrue(1 == holdSeats.getSeats().get(0).getLevel().getId());
		assertTrue(0 == holdSeats.getSeats().get(0).getRowNum());
		assertTrue(0 == holdSeats.getSeats().get(0).getSeatNum());
	}

	

	
	/*
	 * tests that if wrong inputs are given then the sysem throws exception
	 */
	@Test(expected=RuntimeException.class)
	public void testHoldSeatsInLevel_validate_1() {
		service.findAndHoldSeats(-21, Optional.of(1), Optional.of(1), "someone@somedomain.com");
	}

	/*
	 * tests that if wrong inputs are given then the sysem throws exception
	 */
	@Test(expected=RuntimeException.class)
	public void testHoldSeatsInLevel_validate_2() {
		service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), ""); // blank email
	}
	
	/*
	 * tests that the maxLevel can't be less than the min level
	 */
	@Test(expected=RuntimeException.class)
	public void testHoldSeatsInLevel_validate_3() {
		service.findAndHoldSeats(1, Optional.of(3), Optional.of(1), "someone@somedomain.com");
	}

	// tests that the seats are not possible to hold once all seats are held
	@Test(expected=RuntimeException.class)
	public void testHoldSeatsInLevel_rehold() {
		service.findAndHoldSeats(1250, Optional.of(1), Optional.of(1), "someone@somedomain.com");
		assertTrue(0 == service.numSeatsAvailable(Optional.of(1)));
		// try to hold a seat once all the seats are hold
		service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), "someone@somedomain.com");
	}

	/*
	 * Tests that 
	 * 1. if seats are not available in one level and only one level is
	 * specified, tehn it throws exception; 
	 * 2. also if exception is thrown then seats should not be held
	 */
	@Test
	public void testHoldSeatsInLevel_avalability_test1() {
		try {
			service.findAndHoldSeats(1270, Optional.of(1), Optional.of(1), "someone@somedomain.com");
			fail("Should never reach here");
		} catch(RuntimeException e) {
			// good here
		}
		assertTrue( 1250 == service.numSeatsAvailable(Optional.of(1)));
	}

	/*
	 * Tests that if enough seats are not available for a particular level and
	 * higer levels, then the system throws exception
	 */
	@Test(expected=RuntimeException.class)
	public void testHoldSeatsInLevel_avalability_test2() {
		service.findAndHoldSeats(1501, Optional.of(4), Optional.empty(), "someone@somedomain.com");
	}
	/*
	 * Tests that 
	 * 1. if seats are not available in one level then seats should be
	 * held at a upper level if max level is not specified 
	 */
	@Test
	public void testHoldSeatsInLevel_avalability_test3() {
		service.findAndHoldSeats(2050, Optional.of(2), Optional.empty(), "someone@somedomain.com");
		assertTrue(0 == service.numSeatsAvailable(Optional.of(2)));
		assertTrue(1450 == service.numSeatsAvailable(Optional.of(3)));
	}

}
