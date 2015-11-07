package com.pkp.ticketservice.service.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * Test class to validate ticket service dao
 * @author pravat
 *
 */
public class TicketServiceDaoImplTest extends TicketServiceBaseDaoTest{

	/**
	 * Test available seats in level
	 */
	@Test
	public void testGetAvailableInLevel() {
		assertEquals(1250, dao.getAvailableInLevel(Level.ORCHESTRA));
		assertEquals(1500, dao.getAvailableInLevel(Level.BALCONY_1));
	}

	/**
	 * Checks of seats can be reserved
	 */
	@Test
	public void testGetSeats() {
		List<Seat> seats = dao.getSeats(Level.ORCHESTRA, Level.ORCHESTRA, 100);
		assertEquals(100, seats.size());

		seats = dao.getSeats(Level.ORCHESTRA, Level.ORCHESTRA, 1250);
		assertEquals(1250, seats.size());
	}

	/**
	 * If enough seats are not available then should expect exception
	 */
	@Test(expected=RuntimeException.class)
	public void testGetSeats_exception() {
		dao.getSeats(Level.ORCHESTRA, Level.ORCHESTRA, 3000);
	}

	/**
	 * If enough seats are not available then should expect exception
	 */
	@Test
	public void testHoldSeats() {
		Seat seat = new Seat(Level.BALCONY_1, 1, 23);
		List<Seat> list = new ArrayList<>();
		list.add(seat);
		boolean holdSeats = dao.holdSeats(list);

		assertTrue(holdSeats);
		checkStatusOfSeat(Level.BALCONY_1, 1, 23, Status.HOLD);
	}
	
	/**
	 * Tests if createTransaction creates a hold transaction or not
	 */
	@Test
	public void testCreateTransaction() {
		Seat seat = new Seat(Level.BALCONY_1, 1, 23);
		List<Seat> list = new ArrayList<>();
		list.add(seat);
		SeatHold seatHold = new SeatHold(list, 23, "someone@somedomain.com");
		dao.createTransaction(seatHold);

		checkHoldTransactionCreated(seatHold);
	}

	/**
	 * Test if mark transaction marks the transaction as BOOKED
	 */
	@Test
	public void testMarkTransaction() {
		Seat seat = new Seat(Level.BALCONY_1, 1, 23);
		List<Seat> list = new ArrayList<>();
		list.add(seat);
		SeatHold seatHold = new SeatHold(list, 23, "someone@somedomain.com");
		dao.createTransaction(seatHold);
		Transaction transaction = dao.getTransaction(seatHold.getSeatHoldId());

		dao.markTransaction(transaction, UUID.randomUUID().toString(), Status.BOOKED);

		assertEquals(transaction.getTransacionStatus(), Status.BOOKED);
	}

	/**
	 * Tests if removeHoldTransaction removes the hold transaction 
	 */
	@Test
	public void testRemoveHoldTransaction() {

		Seat seat = new Seat(Level.BALCONY_1, 1, 23);
		List<Seat> list = new ArrayList<>();
		list.add(seat);
		SeatHold seatHold = new SeatHold(list, 23, "someone@somedomain.com");
		dao.createTransaction(seatHold);
		dao.removeHoldTransaction(seatHold.getSeatHoldId());

		assertNull(dao.getDataBase().getHoldDataBase().get(seatHold.getSeatHoldId()));
	}

	/**
	 * Tests if reserve seats actually reserves the seats or not
	 */
	@Test
	public void testReserveSeats() {

		Seat seat = new Seat(Level.BALCONY_1, 1, 23);
		List<Seat> list = new ArrayList<>();
		list.add(seat);
		dao.holdSeats(list);
		dao.reserveSeats(list);

		Map<Level, Map<Integer, Map<Integer, Status>>> dataBase = dao.getDataBase().getMainDataBase();

		for (Seat seat2 : list) {
			Status status = dataBase.get(seat2.getLevel()).get(seat2.getRowNum()).get(seat2.getSeatNum());
			assertEquals(Status.BOOKED, status);
		}
	}
}
