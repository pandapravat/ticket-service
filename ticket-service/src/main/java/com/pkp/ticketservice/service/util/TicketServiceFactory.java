package com.pkp.ticketservice.service.util;

import com.pkp.ticketservice.service.TicketService;
import com.pkp.ticketservice.service.impl.TicketServiceImpl;

/**
 * Factory class for ticket service 
 * 
 * @author pravat
 *
 */
public class TicketServiceFactory {

	// instance of ticket service to be created by the factory method
	private static TicketService service;
	
	private TicketServiceFactory() {
		// private constructor to enable creation of singleton
		// instance of TicketService
	}
	/**
	 * Factory method to get a singleton  instance of ticket service
	 * @return an instance of TicketService
	 */
	public static TicketService getTicketServiceInstance() {
		
		if(null == service) {
			synchronized (TicketServiceFactory.class) {
				if(null == service) {
					service = new TicketServiceImpl();
				}
			}
		}
		return service;
	}

}
