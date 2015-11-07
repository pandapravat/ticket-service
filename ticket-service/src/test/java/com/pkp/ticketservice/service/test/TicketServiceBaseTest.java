package com.pkp.ticketservice.service.test;

import org.junit.Assert;
import org.junit.Before;

import com.pkp.ticketservice.dao.impl.TicketServiceDaoImpl;
import com.pkp.ticketservice.domain.Level;
import com.pkp.ticketservice.domain.Status;
import com.pkp.ticketservice.service.TicketService;
import com.pkp.ticketservice.service.impl.TicketServiceImpl;
import com.pkp.ticketservice.service.util.TicketServiceFactory;

/**
 * Base test class for ticket service
 * @author pravat
 *
 */
public class TicketServiceBaseTest {
	
	protected TicketService service;
	private TicketServiceDaoImpl dao;

	@Before
	public void init() {
		service = TicketServiceFactory.getTicketServiceInstance();
		// make sure that a new instance of database is available for each test
		 dao = new TicketServiceDaoImpl();
		
		((TicketServiceImpl)service).setDao(dao);
	}

	protected void checkStatusOfSeat(Level level, int rowNum, int seatNum, Status expectedStatus) {
		Status status = dao.getDataBase().getMainDataBase().get(level).get(rowNum).get(seatNum);
		Assert.assertTrue("Status of seat does not match", status.equals(expectedStatus));
	}
}
