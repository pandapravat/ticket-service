package com.pkp.ticketservice.domain;

import java.util.Optional;

public class SeatHoldRequest {

	int numSeats;
	Optional<Integer> minLevel;
	Optional<Integer> maxLevel;
	String email;
	
	
	
	public SeatHoldRequest(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String email) {
		super();
		this.numSeats = numSeats;
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.email = email;
	}
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public Optional<Integer> getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(Optional<Integer> minLevel) {
		this.minLevel = minLevel;
	}
	public Optional<Integer> getMaxLevel() {
		return maxLevel;
	}
	public void setMaxLevel(Optional<Integer> maxLevel) {
		this.maxLevel = maxLevel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "SeatHoldRequest [numSeats=" + numSeats + ", minLevel=" + minLevel + ", maxLevel=" + maxLevel
				+ ", email=" + email + "]";
	}
	
	
}
