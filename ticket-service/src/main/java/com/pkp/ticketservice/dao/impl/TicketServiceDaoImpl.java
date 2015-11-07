package com.pkp.ticketservice.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pkp.ticketservice.dao.TicketServiceDao;
import com.pkp.ticketservice.dao.domain.TicketServiceDataBase;
import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * The implementation class for {@link TicketServiceDao} 
 * 
 * @author pravat
 */
public class TicketServiceDaoImpl implements TicketServiceDao {

	// the database for the ticket service
	private TicketServiceDataBase dataBase;

	public TicketServiceDaoImpl() {
		dataBase = new TicketServiceDataBase();
	}
	
	@Override
	public int getAvailableInLevel(Level level) {

		Map<Integer, Map<Integer, Status>> levelMap = dataBase.getMainDataBase().get(level);
		int availableCount = 0;
		for (Entry<Integer, Map<Integer, Status>> entry : levelMap.entrySet()) {

			Set<Entry<Integer,Status>> entrySet2 = entry.getValue().entrySet();
			for (Entry<Integer, Status> entry2 : entrySet2) {
				if(Status.AVAILABLE.equals(entry2.getValue())) availableCount++;
			}
		}
		return availableCount;
	}


	@Override
	public List<Seat> getSeats(Level minLevel, Level maxLevel, 
			int numSeats) {

		Level targetLevel = minLevel;
		List<Seat> seats = new ArrayList<>();
		do {
			int requiredSeats = numSeats - seats.size();
			seats.addAll(findSeats(requiredSeats, targetLevel));

		} while(null != (targetLevel = Level.getNextLevel(targetLevel, maxLevel)) 
				&& seats.size() < numSeats) ;

		if(seats.size() < numSeats) {
			throw new RuntimeException("Can't hold enough seats for the given criteria");
		}
		return seats;
	}

	/*
	 * Finds available seats equal to or lesser than the requiredSeats in the given
	 * level.
	 */
	private List<Seat> findSeats(int requiredSeats, Level currLevel) {

		Map<Integer, Map<Integer, Status>> mainDatabaseForLevel = dataBase.getMainDataBase().get(currLevel);
		List<Seat> seats = new ArrayList<>();
		Set<Entry<Integer,Map<Integer,Status>>> entrySet = mainDatabaseForLevel.entrySet();
		for (Entry<Integer, Map<Integer, Status>> entry : entrySet) {
			Set<Entry<Integer,Status>> entrySet2 = entry.getValue().entrySet();
			for (Entry<Integer, Status> entry2 : entrySet2) {
				if(Status.AVAILABLE.equals(entry2.getValue()))  {
					seats.add(new Seat(currLevel, entry.getKey(), entry2.getKey()));
				}
				if(seats.size() >= requiredSeats) return seats;
			}
		}
		return seats;
	}

	@Override
	public boolean createTransaction(SeatHold seatHold) {

		Transaction transaction = new Transaction();
		transaction.setTransacionStatus(Status.HOLD);
		transaction.setCustomerEmail(seatHold.getCustomerEmail());
		transaction.setSeats(seatHold.getSeats());
		dataBase.getHoldDataBase().put(seatHold.getSeatHoldId(), transaction);

		return true;
	}

	@Override
	public boolean holdSeats(List<Seat> seats) {

		for (Seat seat : seats) {
			Map<Integer, Status> map = dataBase.getMainDataBase().get(seat.getLevel()).get(seat.getRowNum());
			if(Status.AVAILABLE.equals(map.get(seat.getSeatNum()))) {
				map.put(seat.getSeatNum(), Status.HOLD);
			} else {
				// This condition might never occur as getSeats and holdSeats are 
				// invoked from a locked instance of the dataBase
				throw new RuntimeException("An internal error occurred");
			}
		}

		return true;
	}
	
	@Override
	public Transaction getTransaction(int seatHoldId) {
		return dataBase.getHoldDataBase().get(seatHoldId);
	}
	
	@Override
	public void reserveSeats(List<Seat> seats) {
		for (Seat seat : seats) {
			Map<Integer, Status> map = dataBase.getMainDataBase().get(seat.getLevel()).get(seat.getRowNum());
			if(Status.HOLD.equals(map.get(seat.getSeatNum()))) {
				map.put(seat.getSeatNum(), Status.BOOKED);
			} else {
				// This condition might never occur as database would be locked
				// by the service layer before invoking this method
				throw new RuntimeException("An internal error occurred..");
			}
		}
		
	}
	
	@Override
	public void removeHoldTransaction(int holdId) {
		dataBase.getHoldDataBase().remove(holdId);
	}
	
	@Override
	public void markTransaction(Transaction transaction, String reservationCode, Status status) {
		transaction.setReservationCode(reservationCode);
		transaction.setTransacionStatus(status);
	}
	
	@Override
	public void ensureLock() {
		dataBase.waitAndLock(60*1000);
	}
	@Override
	public void releaseLock() {
		dataBase.releaseLock();
	}
	
	public TicketServiceDataBase getDataBase() {
		return dataBase;
	}
}
