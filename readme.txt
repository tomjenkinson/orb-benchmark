The benchmark has the following qualities:
1. Round-trip latency, rather than overall throughput
2. No arguments in the inout parameters or return type as we are testing the raw speed of the ORB


To run the tests:
1. Start the ORB naming service locally on port 1050
	orbd  -ORBInitialPort 1050
2. On the remote node, start server:
	mvn clean compile exec:java -P jacorb
3. On the client, start the tests:
	mvn test