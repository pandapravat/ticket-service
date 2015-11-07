package com.pkp.ticketservice.service.util;

import com.pkp.ticketservice.dao.TicketServiceDao;
import com.pkp.ticketservice.dao.impl.TicketServiceDaoImpl;

/**
 * Factory class for TicketServiceDao
 * @author pravat
 *
 */
public class TicketServiceDaoFactory {


	// instance of TicketServiceDao to be created by the factory method
	private static TicketServiceDao dao;
	
	private TicketServiceDaoFactory() {
		// private constructor to enable creation of singleton
		// instance of TicketService
	}
	/**
	 * Factory method to get a singleton  instance of TicketServiceDao
	 * @return an instance of TicketService
	 */
	public static TicketServiceDao getTicketServiceDaoInstance() {
		
		if(null == dao) {
			synchronized (TicketServiceDaoFactory.class) {
				if(null == dao) {
					dao = new TicketServiceDaoImpl();
				}
			}
		}
		return dao;
	}
}
