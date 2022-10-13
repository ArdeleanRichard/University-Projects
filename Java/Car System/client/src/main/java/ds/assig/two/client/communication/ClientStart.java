package ro.tuc.dsrl.ds.handson.assig.two.client.communication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ro.tuc.dsrl.ds.handson.assig.two.common.entities.Car;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.IPriceService;
import ro.tuc.dsrl.ds.handson.assig.two.common.serviceinterfaces.ITaxService;

import javax.swing.*;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems,
 *          http://dsrl.coned.utcluj.ro/
 * @Module: assignment-two-client
 * @Since: Sep 24, 2015
 * @Description: Starting point of the Client application.
 */
public class ClientStart extends JFrame {
	private static final Log LOGGER = LogFactory.getLog(ClientStart.class);

	private JTextField engineTF = new JTextField(20);
	private JTextField yearTF= new JTextField(20);
	private JTextField priceTF = new JTextField(20);

	private JButton priceBtn = new JButton("Calculate Price");
	private JButton taxBtn = new JButton("Calculate Tax");
	JLabel calculation = new JLabel();

	private static final String INITIAL_VALUE = "0";

	public static JFrame frame;

	public ClientStart() {
		//... Initialize components
		yearTF.setText(INITIAL_VALUE);
		engineTF.setText(INITIAL_VALUE);
		priceTF.setText(INITIAL_VALUE);

		frame=new JFrame("Assignment2");
		frame.setLayout(null);
		frame.setSize(400,220);


		calculation.setBounds(10, 160, 200, 20);
		frame.add(calculation);

		JLabel yearLabel = new JLabel("Enter year: ");
		yearLabel.setBounds(10, 10, 150, 20);
		frame.add(yearLabel);
		yearTF.setBounds(160, 10, 200, 20);
		frame.add(yearTF);

		JLabel capacityLabel = new JLabel("Enter engine capacity: ");
		capacityLabel.setBounds(10, 50, 150, 20);
		frame.add(capacityLabel);
		engineTF.setBounds(160, 50, 200, 20);
		frame.add(engineTF);

		JLabel priceLabel = new JLabel("Enter price: ");
		priceLabel.setBounds(10, 90, 150, 20);
		frame.add(priceLabel);
		priceTF.setBounds(160, 90, 200, 20);
		frame.add(priceTF);

		taxBtn.setBounds(10, 125, 150, 20);
		frame.add(taxBtn);
		taxBtn.addActionListener(e -> {
			Integer year = Integer.parseInt(yearTF.getText());
			Integer capacity = Integer.parseInt(engineTF.getText());
			Integer price = Integer.parseInt(priceTF.getText());

			try {
				Registry registry = LocateRegistry.getRegistry(9000);
				ITaxService taxService  = (ITaxService) registry.lookup("tax");

				String tax = "Tax value: " + taxService.computeTax(new Car(year, capacity));
				System.out.println(tax);
				calculation.setText(tax);
			} catch (Exception e1) {
				LOGGER.error("",e1);
			}
		});

		priceBtn.setBounds(200, 125, 150, 20);
		frame.add(priceBtn);
		priceBtn.addActionListener(e -> {
			Integer year = Integer.parseInt(yearTF.getText());
			Integer capacity = Integer.parseInt(engineTF.getText());
			Integer price = Integer.parseInt(priceTF.getText());

			try {
				Registry registry = LocateRegistry.getRegistry(9000);
				IPriceService priceService = (IPriceService) registry.lookup("price");

				String sprice = "Selling price: "+ priceService.computePrice(new Car(year, capacity, price));
				System.out.println(sprice);
				calculation.setText(sprice);

			} catch (Exception e1) {
				LOGGER.error("",e1);
			}
		});



		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public static void main(String[] args) throws IOException {
		new ClientStart();
	}
}
