package ro.tuc.dsrl.ds.handson.assig.two.server.services;

import ro.tuc.dsrl.ds.handson.assig.two.common.entities.Car;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.ITaxService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @Author: Technical University of Cluj-Napoca, Romania
 *          Distributed Systems Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Module: assignment-two-server
 * @Since: Sep 1, 2015
 * @Description:
 * 	Class used for computation of taxes to be paid for a specific car. An instance
 * 	of this class is published in the Registry so that it can be remotely accessed
 * 	by a client.
 */
public class TaxService extends UnicastRemoteObject implements ITaxService {

	public TaxService() throws RemoteException
	{
		super();
	}

	public double computeTax(Car c) {
		// Dummy formula
		if (c.getEngineCapacity() <= 0) {
			throw new IllegalArgumentException("Engine capacity must be positive.");
		}
		int sum = 8;
		if(c.getEngineCapacity() > 1601) sum = 18;
		if(c.getEngineCapacity() > 2001) sum = 72;
		if(c.getEngineCapacity() > 2601) sum = 144;
		if(c.getEngineCapacity() > 3001) sum = 290;
		return c.getEngineCapacity() / 200.0 * sum;
	}
}
