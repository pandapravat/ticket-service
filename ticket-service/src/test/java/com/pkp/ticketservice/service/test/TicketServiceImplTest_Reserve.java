package com.pkp.ticketservice.service.test;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * Tests the reserve seat functionality of ticket service
 * @author pravat
 *
 */
public class TicketServiceImplTest_Reserve extends TicketServiceBaseTest{

	/*
	 * Tests that 
	 * 1. seats can be reserved after they are held
	 */
	@Test
	public void testReserveSeatsInLevel() {
		SeatHold holdSeats = service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), "someone@somedomain.com");
		String confirmation = service.reserveSeats(holdSeats.getSeatHoldId(), holdSeats.getCustomerEmail());
		Seat seat = holdSeats.getSeats().get(0);
		checkStatusOfSeat(seat.getLevel(), seat.getRowNum(), seat.getSeatNum(), Status.BOOKED);
		assertNotNull(confirmation);
	}
	
	/*
	 * Tests that reservation can only be done with a valid seatholdid
	 */
	@Test(expected=RuntimeException.class)
	public void testReserveSeatsInLevel_invalid_seatholdId() {
		SeatHold holdSeats = service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), "someone@somedomain.com");
		service.reserveSeats(3466, holdSeats.getCustomerEmail());
	}

	/*
	 * Tests that reservation can only be done the correct seathold id and email combination
	 */
	@Test(expected=RuntimeException.class)
	public void testReserveSeatsInLevel_combination() {
		SeatHold holdSeats = service.findAndHoldSeats(1, Optional.of(1), Optional.of(1), "someone@somedomain.com");
		service.reserveSeats(holdSeats.getSeatHoldId(), "someother@somedomain.com");
	}

	/*
	 * Tests that all seats are reserved for the seat hold
	 */
	@Test
	public void testReserveSeatsInLevel_check_all_status() {
		SeatHold holdSeats = service.findAndHoldSeats(13, Optional.of(4), Optional.empty(), "someone@somedomain.com");
		service.reserveSeats(holdSeats.getSeatHoldId(), holdSeats.getCustomerEmail());
		
		List<Seat> seats = holdSeats.getSeats();
		for (Seat seat : seats) {
			checkStatusOfSeat(seat.getLevel(), seat.getRowNum(), seat.getSeatNum(), Status.BOOKED);
		}
	}

}
