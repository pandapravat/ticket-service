package com.pkp.ticketservice.service.util;

import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.routines.EmailValidator;

import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.SeatHoldRequest;
import com.pkp.ticketservice.domain.SeatReserveRequest;
import com.pkp.ticketservice.domain.SeatSearchRequest;
import com.pkp.ticketservice.domain.Status;
import com.pkp.ticketservice.service.TicketService;

/**
 * A validator service for the ticket service framework.
 * Throws Runtime exception if any validation errors are found
 * @author pravat
 *
 */
public class TicketServiceValidator {
	
	private static Log LOGGER = LogFactory.getLog(TicketService.class);

	// validate reserve seat request
	public void validateReserveSeatRequest(SeatReserveRequest request) {

		validatePositive(request.getSeatHoldId(), "SeatHoldId");
		validateEmail(request.getCustomerEmail());
	}

	// validate if the value is positive
	private void validatePositive(int seatHoldId, String entity) {
		if(seatHoldId <= 0) throw new RuntimeException("Invalid " + entity + ", Should be greater than zero");

	}

	// validate email
	private void validateEmail(String customerEmail) {
		
		if(!EmailValidator.getInstance().isValid(customerEmail))  throw new RuntimeException("Email id is invalid");

	}
	// validates the seathold request
	public void validateSeatHoldRequest(SeatHoldRequest request) {
		
		validateEmail(request.getEmail());
		validatePositive(request.getNumSeats(), "Number of seats");
		Integer minLevel = request.getMinLevel().isPresent() ? request.getMinLevel().get() : null;
		Integer maxLevel = request.getMaxLevel().isPresent() ? request.getMaxLevel().get() : null;

		validateLevel(request.getMinLevel(), "Min Level");
		validateLevel(request.getMaxLevel(), "Max Level");

		if(null != minLevel && null != maxLevel && minLevel > maxLevel) {
			LOGGER.error("Min level can't be greater than max level");
			throw new RuntimeException("Min level can't be greater than max level");
		}
	}

	// validates the seat search request
	public void validateSeatSearchRequest(SeatSearchRequest seatSearchRequest) {
		validateLevel(seatSearchRequest.getLevel(), "");
	}

	// validates the level
	private void validateLevel(Optional<Integer> level, String message) {
		if(level.isPresent()) {
			Integer levelVal = level.get();
			if(levelVal < Level.ORCHESTRA.getId() || levelVal > Level.BALCONY_2.getId() ) {
				LOGGER.error("Invalid level id." + message);
				throw new RuntimeException("Invalid level id." + message);
			}
		}
	}

	// validates a transaction
	public void validateTransaction(Transaction transaction, String customerEmail) {
		if(null == transaction) {
			LOGGER.error("Seat holdId not found.. Customenr email:" + customerEmail);
			throw new RuntimeException("Seat holdId not found..");
		} else if(Status.BOOKED.equals(transaction.getTransacionStatus())) {
			LOGGER.error("Seats already booked for the holdId. Customenr email:" + customerEmail);
			throw new RuntimeException("Seats already booked for the holdId");
		} else if(!customerEmail.equals(transaction.getCustomerEmail())) {
			LOGGER.error("Seat hold id and customer email does not match. Email: " + customerEmail);
			throw new RuntimeException("Seat hold id and customer email does not match");
		}
		
	}

}
