import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

public class TestORBManagement {
	@Test
	public void testORBManagement() throws InvalidName, AdapterInactive,
			NotFound, AlreadyBound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName, IOException {
		ORBManagement orbManagement = new ORBManagement("orb-management-test",
				"jacorb.properties");
		assertTrue(orbManagement.getNamingContext() != null);
		orbManagement.destroy();
	}
}
