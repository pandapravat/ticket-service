package com.pkp.ticketservice.service.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;

import com.pkp.ticketservice.dao.domain.Transaction;
import com.pkp.ticketservice.dao.impl.TicketServiceDaoImpl;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.domain.Status;

/**
 * Base test class for TicketServiceDao
 * @author pravat
 *
 */
public class TicketServiceBaseDaoTest {
	
	protected TicketServiceDaoImpl dao;
	@Before
	public void init() {
		 dao = new TicketServiceDaoImpl();
	}
	
	protected void checkStatusOfSeat(Level level, int rowNum, int seatNum, Status expectedStatus) {
		Status status = dao.getDataBase().getMainDataBase().get(level).get(rowNum).get(seatNum);
		Assert.assertTrue("Status of seat does not match", status.equals(expectedStatus));
	}
	
	protected void checkHoldTransactionCreated(SeatHold seatHold) {
		Transaction transaction = dao.getDataBase().getHoldDataBase().get(seatHold.getSeatHoldId());
		if(null == transaction) fail("Transaction not created");
		
		assertEquals(Status.HOLD, transaction.getTransacionStatus());
		assertEquals("Email does not match in transaction", 
				seatHold.getCustomerEmail(), transaction.getCustomerEmail());
		assertEquals("Email does not match in transaction", 
				seatHold.getSeats().size(), transaction.getSeats().size());
	}
	
	protected void checkReservedTransactionCreated(String confCode) {
		Transaction transaction = dao.getDataBase().getConfirmedDataBase().get(confCode);
		if(null == transaction) fail("Transaction not created");
		assertEquals(Status.BOOKED, transaction.getTransacionStatus());
	}

}
