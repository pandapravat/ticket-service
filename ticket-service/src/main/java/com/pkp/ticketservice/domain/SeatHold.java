package com.pkp.ticketservice.domain;

import java.util.List;

/**
 * Represents a seathold response
 * @author pravat
 *
 */
public class SeatHold {
	
	private List<Seat> seats;
	private int seatHoldId;
	private String customerEmail;
	
	public SeatHold(List<Seat> seats, int seatHoldId, String customerEmail) {
		super();
		this.seats = seats;
		this.seatHoldId = seatHoldId;
		this.customerEmail = customerEmail;
	}

	public int getSeatHoldId() {
		return seatHoldId;
	}

	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "SeatHold [NumberOf Seats=" + seats.size() + ", seatHoldId=" + seatHoldId + ", customerEmail=" + customerEmail + "]";
	}

	
	
}
