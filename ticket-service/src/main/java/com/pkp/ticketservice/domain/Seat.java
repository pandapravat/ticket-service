package com.pkp.ticketservice.domain;

/**
 * Represents a Seat
 * @author pravat
 *
 */
public class Seat {
	
	Level level;
	int rowNum;
	int seatNum;
	
	
	public Seat(Level level, int rowNum, int seatNum) {
		super();
		this.level = level;
		this.rowNum = rowNum;
		this.seatNum = seatNum;
	}
	
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	@Override
	public String toString() {
		return "Seat [level=" + level + ", rowNum=" + rowNum + ", seatNum=" + seatNum + "]";
	}

	
}
