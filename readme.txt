The benchmark has the following qualities:
1. Round-trip latency, rather than overall throughput
2. No arguments in the inout parameters or return type as we are testing the raw speed of the ORB


To run the tests with jacorb:
1. Start the ORB naming service locally on port 1050
	orbd  -ORBInitialPort 1050
2. On the remote node, start server:
	mvn clean compile exec:java -Dname.server.address=<e.g. localhost> -P jacorb 
3. On the client, start the tests:
	mvn test -Diteration.count=50000 -Dname.server.address=<e.g. localhost> -P jacorb
	
To run the tests with idlj:
1. Start the ORB naming service locally on port 1050
	orbd  -ORBInitialPort 1050
2. On the remote node, start server:
	mvn clean compile exec:java -Dname.server.address=<e.g. localhost> -P idlj
3. On the client, start the tests:
	mvn test -Diteration.count=50000 -Dname.server.address=<e.g. localhost> -P idlj