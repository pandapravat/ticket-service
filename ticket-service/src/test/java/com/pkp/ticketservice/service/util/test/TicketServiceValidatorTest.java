package com.pkp.ticketservice.service.util.test;

import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.pkp.ticketservice.domain.SeatHoldRequest;
import com.pkp.ticketservice.domain.SeatReserveRequest;
import com.pkp.ticketservice.domain.SeatSearchRequest;
import com.pkp.ticketservice.service.util.TicketServiceValidator;

public class TicketServiceValidatorTest {

	private static TicketServiceValidator validator;
	@BeforeClass
	public static void beforeTest() {
		validator = new TicketServiceValidator();
	}

	@Test
	public void validateSeatSearchRequest() {
		validator.validateSeatSearchRequest(new SeatSearchRequest(Optional.of(4)));
		try {
			validator.validateSeatSearchRequest(new SeatSearchRequest(Optional.of(5)));
			fail("Should have thrown exception");
		} catch(Exception e){}

		try {
			validator.validateSeatSearchRequest(new SeatSearchRequest(Optional.of(-1)));
			fail("Should have thrown exception");
		} catch(Exception e){}
	}

	@Test
	public void validateSeatHoldRequest() {
		//valid request
		validator.validateSeatHoldRequest(new SeatHoldRequest(1, Optional.of(1), Optional.of(1), "someone@somedomain.com"));
		
		// invalid number of seats
		try { 
			validator.validateSeatHoldRequest(new SeatHoldRequest(-1, Optional.of(1), Optional.of(1), "someone@somedomain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}
		// negative level id
		try {
			validator.validateSeatHoldRequest(new SeatHoldRequest(1, Optional.of(-1), Optional.of(1), "someone@somedomain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}

		// invalid level id
		try {
			validator.validateSeatHoldRequest(new SeatHoldRequest(1, Optional.of(5), Optional.of(1), "someone@somedomain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}

		// empty email
		try {
			validator.validateSeatHoldRequest(new SeatHoldRequest(1, Optional.of(1), Optional.of(1), ""));
			fail("Should have thrown exception");
		} catch(Exception e){}
		
		// invalid email
		try {
			validator.validateSeatHoldRequest(new SeatHoldRequest(1, Optional.of(1), Optional.of(1), "someone_somedomain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}

	}
	
	@Test
	public void validateReserveSeatRequest() {
		//valid request
		validator.validateReserveSeatRequest(new SeatReserveRequest(2, "someone@somedomain.com"));
		
		// invalid number of seats
		try { 
			validator.validateReserveSeatRequest(new SeatReserveRequest(-12, "someone@somedomain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}
		
		// null email
		try {
			validator.validateReserveSeatRequest(new SeatReserveRequest(12, null));
			fail("Should have thrown exception");
		} catch(Exception e){}

		// empty email
		try {
			validator.validateReserveSeatRequest(new SeatReserveRequest(12, ""));
			fail("Should have thrown exception");
		} catch(Exception e){}


		// invalid email
		try {
			validator.validateReserveSeatRequest(new SeatReserveRequest(12, "sdsf f@domain.com"));
			fail("Should have thrown exception");
		} catch(Exception e){}

	}
}
