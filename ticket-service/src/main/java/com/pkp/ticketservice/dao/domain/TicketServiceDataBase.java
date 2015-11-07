package com.pkp.ticketservice.dao.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Status;

/**
 * Equivalent to the ticket service database
 * @author pravat
 *
 */
public class TicketServiceDataBase {
	
	Logger LOGGER = Logger.getLogger(TicketServiceDataBase.class);
	
	/**
	 * Map which holds the price details
	 */
	private Map<Integer, Integer> levelToPriceMap;
	
	private Map<Level, Map<Integer, Map<Integer, Status>>> dataBase;
	/**
	 * Database equivalent which keeps track of hold transactions
	 */
	private Map<Integer, Transaction> holdTransactionDB;
	
	/**
	 * Database which keeps track of reserved transaction
	 */
	private Map<String, Transaction> confirmedTransactionDB;
	
	
	
	public TicketServiceDataBase() {

		levelToPriceMap = new HashMap<>();
		levelToPriceMap.put(1, 100);
		levelToPriceMap.put(2, 75);
		levelToPriceMap.put(3, 50);
		levelToPriceMap.put(4, 40);
		
		dataBase = new HashMap<>();
		dataBase.put(Level.ORCHESTRA, populateLevelRows(25, 50));
		dataBase.put(Level.MAIN, populateLevelRows(20, 100));
		dataBase.put(Level.BALCONY_1, populateLevelRows(15, 100));
		dataBase.put(Level.BALCONY_2, populateLevelRows(15, 100));
		
		holdTransactionDB = new HashMap<>();
		confirmedTransactionDB = new HashMap<>();
	}

	// initialise levels
	private Map<Integer,Map<Integer,Status>> populateLevelRows(int rows, int seatsInRow) {
		Map<Integer,Map<Integer,Status>> rowMap = new HashMap<>();
		for(int rowCount = 0; rowCount< rows; rowCount++) {
			rowMap.put(rowCount, populateRow(seatsInRow));
		}
		return rowMap;
	}

	// initialize the rows
	private Map<Integer, Status> populateRow(int seatsInRow) {
		
		Map<Integer, Status> seats = new HashMap<>();
		for(int seatCount = 0; seatCount < seatsInRow; seatCount ++) {
			seats.put(seatCount, Status.AVAILABLE);
		}
		
		return seats;
	}

	
	public Map<Level, Map<Integer, Map<Integer, Status>>> getMainDataBase() {
		return dataBase;
	}
	
	public  Map<Integer, Transaction> getHoldDataBase() {
		return holdTransactionDB;
	}
	
	public  Map<String, Transaction> getConfirmedDataBase() {
		return confirmedTransactionDB;
	}

	private static final int LOCKED = 0x124;
	private static final int FREE = 0x134;
	int lockStatus = FREE;
	
	/**
	 * Try to acquire lock. If the database is already locked, wait for time miliseconds
	 * @param time the max wait time in miliseconds
	 */
	public void waitAndLock(int time) {
		if(time < 0) {
			LOGGER.warn("Could not get lock on the database. timing out..");
			throw new RuntimeException("An internal error occurred. The operation timed out");
		}
		if(lockStatus == FREE) lockStatus = LOCKED;
		else {
			try {
				// wait for one second and again try lock
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			waitAndLock(time - 1000);
		}
		
	}

	public void releaseLock() {
		lockStatus = FREE;
	}
}
