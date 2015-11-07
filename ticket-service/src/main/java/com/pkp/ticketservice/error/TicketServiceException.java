package com.pkp.ticketservice.error;

public class TicketServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public TicketServiceException(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
