package com.pkp.ticketservice.main;

import java.util.Optional;
import java.util.Scanner;

import com.pkp.ticketservice.domain.SeatHold;
import com.pkp.ticketservice.error.TicketServiceException;
import com.pkp.ticketservice.service.TicketService;
import com.pkp.ticketservice.service.util.TicketServiceFactory;

/**
 * Entry point of the application
 * 
 * @author pravat
 *
 */
public class TicketServiceMain {
	public static void main(String[] args) {

		System.out.println("Welcome to the ticketing service!!");

		TicketService instance = TicketServiceFactory.getTicketServiceInstance();
		Scanner scanner = new Scanner(System.in);
		int option = -1;
		do {
			try {
				System.out.println("***************************************************");
				System.out.println("Please enter your option followed by return. Press 0 to exit");
				System.out.println("1. Check available seats");
				System.out.println("2. Hold Seats");
				System.out.println("3. Reserve Seats");
				System.out.println("***************************************************");
				String opt = scanner.next();
				if(!isInt(opt)) option = -1;
				else option = Integer.valueOf(opt);
				if(1 == option) {
					System.out.println("Enter Level id and press return. Press X to check in all levels");
					String nextInt = scanner.next();
					if("X".equalsIgnoreCase(nextInt)) nextInt = null;
					if(!isIntOrNull(nextInt)) {
						System.out.println("Invalid option..");
						continue;
					}
					System.out.println("Available seat count is :" +
							instance.numSeatsAvailable(null == nextInt ? Optional.empty() :Optional.of(Integer.valueOf(nextInt))));
				} else if(2 == option) {
					//number of seats
					System.out.println("Enter number of seats..");
					String numSeats = scanner.next();
					if(!isInt(opt)) {
						System.out.println("Numbers only..Try again!");
						option = -1; continue;
					}
					// Min level
					System.out.println("Enter Min Level. Press X not to specify any level");
					String next = scanner.next();
					if("X".equalsIgnoreCase(next)) next = null;
					if(!isIntOrNull(next)) {
						System.out.println("Invalid option..");
						continue;
					}
					Optional<Integer> minLvl = null == next ? Optional.empty() :Optional.of(Integer.valueOf(next));
					// max level
					System.out.println("Enter Max Level. Press X, not to specify any level");
					next = scanner.next();
					if("X".equalsIgnoreCase(next)) next = null;
					if(!isIntOrNull(next)) {
						System.out.println("Invalid option..");
						continue;
					}
					Optional<Integer> maxLvl = null == next ? Optional.empty() :Optional.of(Integer.valueOf(next));
					// email
					System.out.println("Enter email...");
					next = scanner.next();

					SeatHold findAndHoldSeats = instance.findAndHoldSeats(Integer.valueOf(numSeats),minLvl, maxLvl, next);

					System.out.println(findAndHoldSeats);
				} else if(3 == option) {
					System.out.println("Enter seat hold id...");
					String seatHoldId = scanner.next();
					if(!isInt(seatHoldId)) {
						System.out.println("Numbers only..Try again!");
						option = -1; continue;
					}
					System.out.println("Enter email..");
					String customerEmail = scanner.next();
					instance.reserveSeats(Integer.valueOf(seatHoldId), customerEmail);
					System.out.println("Reservation successful");
				} else if(0 != option){
					System.out.println("Invalid option..try again");
				}
			} catch(TicketServiceException e) {
				try {
					Thread.sleep(100);
					System.err.println(e.getErrorMessage());
					Thread.sleep(100);
				} catch (InterruptedException e1) {
				}
			}
		} while (option != 0);

		scanner.close();
	}

	private static boolean isIntOrNull(String nextInt) {

		if(null == nextInt) return true;
		try {
			Integer.parseInt(nextInt);
		} catch (NumberFormatException e) { 
			return false;
		}
		return true;
	}

	private static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) { 
			return false;
		}
		return true;
	}
}
