package org.jboss.narayana.qa.orbbenchmark;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class Server {
	private static final Logger log = LogManager.getLogger(Server.class);

	public static void main(String[] args) throws InvalidName, AdapterInactive,
			NotFound, AlreadyBound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName, IOException,
			InvalidPolicy, AdapterNonExistent, ServantAlreadyActive,
			WrongPolicy, ServantNotActive, InterruptedException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		String orbName = System.getProperty("orb.name");
		ORBManagement orbManagement = new ORBManagement(orbName);
		PingerServer pingerServer = (PingerServer) Class.forName(
				"org.jboss.narayana.qa.orbbenchmark.testmodule." + orbName
						+ ".PingerImpl").newInstance();
		pingerServer.init(orbManagement);
		orbManagement.block();
	}
}
