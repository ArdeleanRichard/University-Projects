package ro.tuc.dsrl.ds.handson.assig.two.server.services;

import ro.tuc.dsrl.ds.handson.assig.two.common.entities.Car;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.IPriceService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PriceService extends UnicastRemoteObject implements IPriceService {

    public PriceService() throws RemoteException
    {
        super();
    }

    public double computePrice(Car c) {

        double purchasingPrice = c.getPrice();
        if (purchasingPrice <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        double price = 0;
        if(2018-c.getYear()<7)
            price = purchasingPrice - (purchasingPrice/7 * (2018-c.getYear()));
        return price;
    }
}
