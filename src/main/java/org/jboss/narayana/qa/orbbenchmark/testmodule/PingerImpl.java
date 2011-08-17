package org.jboss.narayana.qa.orbbenchmark.testmodule;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.narayana.qa.orbbenchmark.orbmanagment.ORBManagement;
import org.jboss.narayana.qa.orbbenchmark.testmodule.TestModule.PingerPOA;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextOperations;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;

public class PingerImpl extends PingerPOA {
	private static final Logger log = LogManager.getLogger(PingerImpl.class);

	public PingerImpl(ORBManagement orbManagement) throws InvalidPolicy,
			AdapterNonExistent, ServantAlreadyActive, WrongPolicy,
			ServantNotActive, NotFound, CannotProceed, InvalidName {
		POA poa = orbManagement.getRootPoa();
		byte[] activate_object = poa.activate_object(this);
		log.debug("activated this " + activate_object);

		NamingContextOperations ctx = orbManagement.getNamingContext();
		List<NameComponent> name = new ArrayList<NameComponent>();
		name.add(new NameComponent("pinger", ""));
		org.omg.CORBA.Object objref = poa.servant_to_reference(this);
		ctx.rebind(name.toArray(new NameComponent[] {}), objref);
	}

	public void ping() {
		// This does nothing as we are doing a performance test of the orb
		log.info("Pung");
	}
}
