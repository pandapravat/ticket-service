package com.pkp.ticketservice.dao.domain;

import java.util.List;

import com.pkp.ticketservice.domain.Seat;
import com.pkp.ticketservice.domain.Status;

/**
 * Symbolizes a transaction
 * 
 * @author pravat
 *
 */
public class Transaction {
	
	private String customerEmail;
	private List<Seat> seats;
	private Status transacionStatus;
	private int holdId;
	private String reservationCode;
	
	public int getHoldId() {
		return holdId;
	}
	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}
	public String getReservationCode() {
		return reservationCode;
	}
	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}
	public Status getTransacionStatus() {
		return transacionStatus;
	}
	public void setTransacionStatus(Status transacionStatus) {
		this.transacionStatus = transacionStatus;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public List<Seat> getSeats() {
		return seats;
	}
	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}
}
