package com.pkp.ticketservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.pkp.ticketservice.dao.TicketServiceDao;
import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.SeatHoldRequest;
import com.pkp.ticketservice.domain.SeatReserveRequest;
import com.pkp.ticketservice.domain.SeatSearchRequest;
import com.pkp.ticketservice.domain.Status;
import com.pkp.ticketservice.error.TicketServiceException;
import com.pkp.ticketservice.service.TicketService;
import com.pkp.ticketservice.service.util.SeatHoldIdGenerator;
import com.pkp.ticketservice.service.util.TicketServiceDaoFactory;
import com.pkp.ticketservice.service.util.TicketServiceValidator;

/**
 * An implementation for the {@link TicketService} . 
 * It is the main interfacing class to ticket service. It validates the
 * requests to ticket service and serves the requests.
 * 
 * @author pravat
 *
 */
public class TicketServiceImpl implements TicketService{
	Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);
	
	private TicketServiceDao dao;

	private TicketServiceValidator validator;
	public TicketServiceImpl() {
		dao = TicketServiceDaoFactory.getTicketServiceDaoInstance();
		validator = new TicketServiceValidator();
	}

	@Override
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		
		SeatSearchRequest seatSearchRequest = new SeatSearchRequest(venueLevel);
		LOGGER.debug("Received seat search request :" + seatSearchRequest);
		int availableCount = 0;
		try {
			// validate the input
			validator.validateSeatSearchRequest(seatSearchRequest);

			Level level = seatSearchRequest.getLevel().isPresent() ? Level.forId(seatSearchRequest.getLevel().get()) : null;

			if(null != level) {
				// find the available seats in a level
				availableCount = dao.getAvailableInLevel(level);
			} else {
				// find the available seats in all levels
				for (Level eachLevel : Level.values()) {
					availableCount += dao.getAvailableInLevel(eachLevel);
				}
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new TicketServiceException(e.getMessage());
		}

		return availableCount;
	}


	@Override
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		SeatHoldRequest request = new SeatHoldRequest(numSeats, minLevel, maxLevel, customerEmail);
		LOGGER.debug("Received seat hold request :" + request);

		SeatHold seatHold = null;
		try {
			// validate the input
			validator.validateSeatHoldRequest(request);

			Level minLevelVal = request.getMinLevel().isPresent() ? Level.forId(request.getMinLevel().get()) : Level.ORCHESTRA;
			Level maxLevelVal = request.getMaxLevel().isPresent() ? Level.forId(request.getMaxLevel().get()) : Level.BALCONY_2;

			// lock the database, then find the seats and reserve them and then unlock the DB
			List<Seat> seats = null;
			try {
				dao.ensureLock();
				seats = dao.getSeats(minLevelVal, maxLevelVal, numSeats);
				dao.holdSeats(seats);
			} finally {
				dao.releaseLock();
			}

			seatHold = new SeatHold(seats, SeatHoldIdGenerator.getInstance().generate(), request.getEmail());
			// create a hold transaction
			dao.createTransaction(seatHold);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new TicketServiceException(e.getMessage());
		}
		return seatHold;
	}

	@Override
	public String reserveSeats(int seatHoldId, String customerEmail) {

		SeatReserveRequest request = new SeatReserveRequest(seatHoldId, customerEmail);

		LOGGER.debug("Received reserveSeats request :" + request);
		String reservationCode = null;
		try {
			// validate the input
			validator.validateReserveSeatRequest(request);

			// Get the hold transaction
			Transaction holdTransaction = dao.getTransaction(request.getSeatHoldId());

			// validate the hold transaction
			validator.validateTransaction(holdTransaction, customerEmail);

			// lock the database and reserve the transaction
			reservationCode = UUID.randomUUID().toString();
			try {
				dao.ensureLock();
				dao.reserveSeats(holdTransaction.getSeats());
				dao.removeHoldTransaction(holdTransaction.getHoldId());
				dao.markTransaction(holdTransaction, reservationCode, Status.BOOKED);
			} finally {
				dao.releaseLock();
			}
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new TicketServiceException(e.getMessage());
		}
		return reservationCode;
	}

	public void setDao(TicketServiceDao dao) {
		this.dao = dao;
	}
}
