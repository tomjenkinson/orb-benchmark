package org.jboss.narayana.qa.orbbenchmark;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextOperations;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;

public class ORBManagement implements Runnable {
	private static final Logger log = LogManager.getLogger(ORBManagement.class);
	private ORB orb;
	private POA rootPoa;
	private NamingContext nc;
	private Thread callbackThread;
	private Object runLock = new Object();
	private boolean orbDone;

	public ORBManagement(String orbName) throws IOException, InvalidName,
			AdapterInactive, NotFound, AlreadyBound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName {
		Properties properties = new Properties();
		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(orbName + ".properties");
		properties.load(is);
		is.close();
		String nameServerAddress = System.getProperty("name.server.address", "localhost");
		orb = org.omg.CORBA.ORB.init(new String[] {
				"-ORBInitRef",
				"NameService=corbaloc::" + nameServerAddress
						+ ":1050/NameService" }, properties);
		rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
		rootPoa.the_POAManager().activate();

		NamingContextExt nce = NamingContextExtHelper.narrow(orb
				.resolve_initial_references("NameService"));
		try {
			NameComponent[] aNameComponentArray = new NameComponent[1];
			aNameComponentArray[0] = new NameComponent("orb-benchmark", "");
			nc = nce.bind_new_context(aNameComponentArray);
		} catch (AlreadyBound e) {
			log.debug("Context already bound");
			org.omg.CORBA.Object aObject = nce.resolve_str("orb-benchmark");
			nc = NamingContextHelper.narrow(aObject);
		}

		callbackThread = new Thread(this);
		callbackThread.setDaemon(true);
		callbackThread.start();
	}

	public void run() {
		log.debug("Running the orb");
		orb.run();
		synchronized (runLock) {
			orbDone = true;
			runLock.notify();
		}
	}

	public void block() throws InterruptedException {
		synchronized (runLock) {
			if (!orbDone) {
				log.info("Waiting for the orb to complete");
				runLock.wait();
			}
		}
	}

	public void destroy() {
		log.info("Closing");
		orb.shutdown(true);
		orb.destroy();
		try {
			// Wait for thread to join
			callbackThread.join();
		} catch (InterruptedException e) {
			log.error("Could not join with callback: " + e, e);
		}
		log.info("Closed");
		nc = null;
		rootPoa = null;
		orb = null;
	}

	public NamingContextOperations getNamingContext() {
		return nc;
	}

	public ORB getOrb() {
		return orb;
	}

	public POA getRootPoa() {
		return rootPoa;
	}
}
