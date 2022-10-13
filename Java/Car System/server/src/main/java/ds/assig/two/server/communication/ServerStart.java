package ro.tuc.dsrl.ds.handson.assig.two.server.communication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.IPriceService;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.ITaxService;
import ro.tuc.dsrl.ds.handson.assig.two.server.services.PriceService;
import ro.tuc.dsrl.ds.handson.assig.two.server.services.TaxService;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author: Technical University of Cluj-Napoca, Romania
 *          Distributed Systems, http://dsrl.coned.utcluj.ro/
 * @Module: assignment-two-server
 * @Since: Sep 24, 2015
 * @Description:
 *	Server application starting point.
 */
public class ServerStart {
	private static final Log LOGGER = LogFactory.getLog(ServerStart.class);

	private ServerStart() {
	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(9000);
			ITaxService taxService = new TaxService();
			IPriceService priceService = new PriceService();
			registry.rebind("tax", taxService);
			registry.rebind("price", priceService);
			System.out.println("The server started.");
		} catch (IOException e) {
			LOGGER.error("",e);
		}
	}
}
