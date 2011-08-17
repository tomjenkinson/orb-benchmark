import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.narayana.qa.orbbenchmark.ORBManagement;
import org.junit.Test;
import org.omg.CORBA.Object;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

public class TestPinger {
	private static final Logger log = LogManager.getLogger(TestPinger.class);

	@Test
	public void testPinger() throws InvalidName, AdapterInactive, NotFound,
			AlreadyBound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName, IOException {
		int iterations = 1000;
		String orbName = System.getProperty("orb.name");
		ORBManagement orbManagement = new ORBManagement(orbName);
		List<NameComponent> name = new ArrayList<NameComponent>();
		name.add(new NameComponent("pinger", ""));
		Object resolve = orbManagement.getNamingContext().resolve(
				name.toArray(new NameComponent[] {}));
		long currentTimeMillis = -1;
		long newCurrentTimeMillis = -1;
		if (orbName.equals("idlj")) {
			org.jboss.narayana.qa.orbbenchmark.testmodule.idlj.TestModule.Pinger pinger = org.jboss.narayana.qa.orbbenchmark.testmodule.idlj.TestModule.PingerHelper
					.narrow(resolve);
			currentTimeMillis = System.currentTimeMillis();
			for (int i = 0; i < iterations; i++) {
				pinger.ping();
			}
			newCurrentTimeMillis = System.currentTimeMillis();
		} else {
			org.jboss.narayana.qa.orbbenchmark.testmodule.jacorb.TestModule.Pinger pinger = org.jboss.narayana.qa.orbbenchmark.testmodule.jacorb.TestModule.PingerHelper
					.narrow(resolve);
			currentTimeMillis = System.currentTimeMillis();
			for (int i = 0; i < iterations; i++) {
				pinger.ping();
			}
			newCurrentTimeMillis = System.currentTimeMillis();
		}
		log.info("Took: " + (newCurrentTimeMillis - currentTimeMillis)
				+ " milliseconds to execute: " + iterations + " interations");
		log.info("Each iteration takes: "
				+ ((newCurrentTimeMillis - currentTimeMillis) / (double) iterations)
				+ " milliseconds");
		orbManagement.destroy();
	}
}
