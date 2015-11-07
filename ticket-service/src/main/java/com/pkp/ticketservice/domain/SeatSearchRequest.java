package com.pkp.ticketservice.domain;

import java.util.Optional;

public class SeatSearchRequest {
	
	Optional<Integer> level;

	public SeatSearchRequest(Optional<Integer> level) {
		super();
		this.level = level;
	}

	public Optional<Integer> getLevel() {
		return level;
	}

	public void setLevel(Optional<Integer> level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "SeatSearchRequest [level=" + level + "]";
	}
	
}
