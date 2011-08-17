package org.jboss.narayana.qa.orbbenchmark;

import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public interface PingerServer {

	void init(ORBManagement orbManagement) throws InvalidPolicy, AdapterNonExistent, ServantAlreadyActive, WrongPolicy, ServantNotActive, NotFound, CannotProceed, InvalidName;

}
