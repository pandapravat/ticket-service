package com.pkp.ticketservice.service.util.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.pkp.ticketservice.dao.TicketServiceDao;
import com.pkp.ticketservice.service.TicketService;
import com.pkp.ticketservice.service.util.TicketServiceDaoFactory;
import com.pkp.ticketservice.service.util.TicketServiceFactory;

// tests the facories used in ticket service
public class TicketServiceAllFactoryTest {
	@Test
	public void testTicketServiceDaoSameInstanceEveryTime() {
		TicketServiceDao dao = TicketServiceDaoFactory.getTicketServiceDaoInstance();
		for(int count = 0 ; count < 100 ; count ++) {
			assertTrue(dao == TicketServiceDaoFactory.getTicketServiceDaoInstance());
		}
	}
	
	@Test
	public void testTicketServiceSameInstanceEveryTime() {
		TicketService service = TicketServiceFactory.getTicketServiceInstance();
		for(int count = 0 ; count < 100 ; count ++) {
			assertTrue(service == TicketServiceFactory.getTicketServiceInstance());
		}
	}
}
