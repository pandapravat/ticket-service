package com.pkp.ticketservice.domain;

public class SeatReserveRequest {
	private int seatHoldId;
	private String customerEmail;
	
	
	public SeatReserveRequest(int seatHoldId, String customerEmail) {
		super();
		this.seatHoldId = seatHoldId;
		this.customerEmail = customerEmail;
	}
	
	public int getSeatHoldId() {
		return seatHoldId;
	}
	public void setSeatHoldId(int seatHoldId) {
		this.seatHoldId = seatHoldId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "SeatReserveRequest [seatHoldId=" + seatHoldId + ", customerEmail=" + customerEmail + "]";
	}
	
	
}
