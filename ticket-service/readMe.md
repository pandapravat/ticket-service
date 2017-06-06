Ticket Service(requirements in src/main/resources directory)
--------------
Assumptions
 1. For simplicity, no frameworks has been used.
 2. In case of any validation errors in the input, a TicketServiceException is thrown. Invoking application is assumed to handle it in an elegant way. The exception message will contain a friendly message.
 3. Though the application is designed to behave properly in multi threaded environment. In case of any unusual circumstance, a TicketServiceException is thrown(which might happen incase of multiple threads, trying to operate on the same resource). The calling application is assumed to handle this exception in an elegant way. e.g. showing the exception message to the UI
 4. For holdSeat and reserve seat operations, the application holds a lock on the resouce. Any other thread trying to hold the lock will timeout in 60 seconds. In this case an exception is thrown.
 5. Logs are generated in a file called "ticket-service.log" so that it does not interfere while running the application in console
 
Build instructions
------------------
gradle clean build distZip

Running Tests
-------------
gradle test

Run Instructions
------------------
After the application is built, it will create a distribution.
 1. Navigate to the "build/distributions" directory
 2. Unzip the file "ticket-service-1.0-RELEASE.zip"
 3. Navigate to the bin directory and the run the "ticket-service" file (for *nix based systems) or "ticket-service.bat" (for windows based systems
 

