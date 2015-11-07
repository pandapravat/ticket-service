package com.pkp.ticketservice.dao;

import java.util.List;

import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * The interface to the ticketservice database.
 * 
 * @author pravat
 */
public interface TicketServiceDao {

	/**
	 * Get available seats in the given level
	 */
	int getAvailableInLevel(Level level);

	/**
	 * Finds seats which are available between minLevel and maxLevel. Once the count 
	 * returns numSeats,
	 * the method returns. The method essentially finds the maximum avalilable seats which
	 * are equal to or less that numSeats.
	 * 
	 * @param minLevel the minimumLevel to search
	 * @param maxLevel the maximum level to search
	 * @param numSeats maximum number of seats to search
	 * @param mainDataBase the dataDase
	 * @return
	 */
	List<Seat> getSeats(Level minLevel, Level maxLevel, int numSeats);

	/**
	 * Hold the given seats
	 */
	boolean holdSeats(List<Seat> seats);

	/**
	 * creates the seathold transaction DB so that it can be referred later
	 */
	boolean createTransaction(SeatHold seatHold);

	/**
	 * Returns a transaction for the seatHoldId. Returns null if no transaction is found
	 */
	Transaction getTransaction(int seatHoldId);


	/**
	 * Reserves the seats
	 */
	void reserveSeats(List<Seat> seats);

	/**
	 * Clears a hold transaction
	 */
	void removeHoldTransaction(int holdId);

	/**
	 * Marks a transaction with status
	 */
	void markTransaction(Transaction holdTransaction, String reservationCode, Status booked);
	
	/**
	 * Ensures that the database is locked
	 */
	void ensureLock();

	/**
	 * Releases a lock on the database
	 */
	void releaseLock();

}
