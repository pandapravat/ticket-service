package com.pkp.ticketservice.service.util;

import java.util.concurrent.atomic.AtomicInteger;

public class SeatHoldIdGenerator {

	AtomicInteger id = new AtomicInteger(1);
	private static SeatHoldIdGenerator INSTANCE;
	public int generate() {
		return id.getAndIncrement();
	}
	public static SeatHoldIdGenerator getInstance() {
		if(null == INSTANCE) {
			synchronized (TicketServiceFactory.class) {
				if(null == INSTANCE) {
					INSTANCE = new SeatHoldIdGenerator();
				}
			}
		}
		return INSTANCE;
	}
}
