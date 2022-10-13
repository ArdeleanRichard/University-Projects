package presentation;

import javax.swing.*;
import businessLayer.AbstractAdministration;
import businessLayer.CustomerAdministration;
import businessLayer.OrderProcessing;
import businessLayer.WarehouseAdministration;
import dataAccessLayer.ConnectionFactory;
import model.Customer;
import model.Order;
import model.OrderDetail;
import model.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Ardelean Eugen Richard
 * An application that simulates order management.
 * This is the user interface.
 */
public class GUI  extends JFrame
{
	private static JFrame content=new JFrame("Warehouse");
	
	private JButton customerop = new JButton("Customer Operations");
	private JButton productop = new JButton("Product Operations");
	private JButton orderop = new JButton("Order Operations");
	
	private JButton addcustomer = new JButton("<html>Add Customer<br />All fields but ID</html>");
	private JButton deletecustomer = new JButton("<html>Delete Customer<br />Only ID</html>");
	private JButton updatecustomer = new JButton("<html>Update Customer<br />All fields</html>");
	private JTextField customerid = new JTextField("Type ID");
	private JTextField customername = new JTextField("Type Name");
	private JTextField customeraddress = new JTextField("Type Address");
	private JTextField customerphone = new JTextField("Type Phone");
	private int cid;
	private String cname,address,phone;
	private JTable customertable;
	private JScrollPane customerscroll;
	
	private JButton addproduct = new JButton("<html>Add Product<br />All fields but ID</html>");
	private JButton deleteproduct = new JButton("<html>Delete Product<br />Only ID</html>");
	private JButton updateproduct = new JButton("<html>Update Product<br />All fields</html>");
	private JTextField productid = new JTextField("Type ID");
	private JTextField productname = new JTextField("Type Name");
	private JTextField productprice= new JTextField("Type Price");
	private JTextField productdescription = new JTextField("Type Description");
	private JTextField productstock = new JTextField("Type Stock");
	private int pid, price, stock;
	private String pname, description;
	private JTable producttable;
	private JScrollPane productscroll;
	
	private JButton addorder = new JButton("Add order");
	private JButton addproducttoorder=new JButton("Add product");
	private JButton neworder=new JButton("New order");
	private JButton deleteorder=new JButton("Delete Order");
	private JTextField ordercid = new JTextField("Type Customer ID");
	private JTextField orderpid = new JTextField("Type Product ID");
	private JTextField payment= new JTextField("Type Payment Method");
	private JTextField orderpq= new JTextField("Type Product Quantity");
	private int oid, ocid, opid, opq;
	private String paymentmethod;
	private JTable ordertable;
	private JScrollPane orderscroll;
	
	private JButton generateOrderFile=new JButton("Generate Order File (.txt)");
	private JTextField orderIdFile=new JTextField("Type OrderID");

	/**
	 * GUI constructor
	 * Buttons and other components are added to the user interface
	 * It also contains the action listeners
	 */
	GUI() 
	{

		content.setLayout(null);
		content.setSize(1500,1000);
		
		//add customer operations button
		content.add(customerop).setBounds(10, 10, 200, 30);
		//add product operations button
		content.add(productop).setBounds(10, 40, 200, 30);
		//add order operations button
		content.add(orderop).setBounds(10, 70, 200, 30);
		
		//customer operations buttons
        content.add(addcustomer).setBounds(250, 10, 200, 60);
        content.add(deletecustomer).setBounds(450, 10, 200, 60);
        content.add(updatecustomer).setBounds(650, 10, 200, 60);
        content.add(customerid).setBounds(250, 70, 200, 30);
	    content.add(customername).setBounds(450, 70, 200, 30);
    	content.add(customeraddress).setBounds(650, 70, 200, 30);
    	content.add(customerphone).setBounds(850, 70, 200, 30);
        
        //product operation buttons
        content.add(addproduct).setBounds(250, 10, 200, 60);
        content.add(deleteproduct).setBounds(450, 10, 200, 60);
        content.add(updateproduct).setBounds(650, 10, 200, 60);
        content.add(productid).setBounds(250, 70, 200, 30);
        content.add(productname).setBounds(450, 70, 200, 30);
	    content.add(productprice).setBounds(650, 70, 200, 30);
    	content.add(productdescription).setBounds(850, 70, 200, 30);
    	content.add(productstock).setBounds(1050, 70, 200, 30);
        
        //order operation buttons
        content.add(addorder).setBounds(250, 10, 200, 30);
        content.add(addproducttoorder).setBounds(250,40,200,30);
        content.add(neworder).setBounds(250,10,200,30); 	
        //content.add(deleteorder).setBounds(250,70,200,30);
	    content.add(ordercid).setBounds(450, 10, 200, 30);
    	content.add(payment).setBounds(650, 10, 200, 30);
    	content.add(orderpid).setBounds(450, 40, 200, 30);
    	content.add(orderpq).setBounds(650, 40, 200, 30);
    	
    	//generate file button
    	content.add(generateOrderFile).setBounds(1000,900,200,30);
    	content.add(orderIdFile).setBounds(1200,900,200,30);
    	
    	generateOrderFile.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	String fileName="orderFiles/order_" + orderIdFile.getText()+".txt";
	        	try
	        	{
	        		BufferedWriter br=new BufferedWriter(new FileWriter(fileName));
	        		
	        		Object[][] objects=OrderProcessing.generateFiles(Integer.parseInt(orderIdFile.getText()));
        			
	        		String line[] ={ "Product: ", "Price: ", "Quantity: ", "Total Price: "};
	        		int sum=0;
        			for(int i=0;i<objects.length;i++)
	        		{
	        			for(int j=0;j<4;j++)
	        			{
	        				br.write(line[j]);
	        				br.write(""+objects[i][j]);
	        				br.newLine();
	        			}
	        			sum=sum+(int)(objects[i][3]);
	        			br.newLine();
	        		}
        			br.write("Final price of the order: "+sum);
	        		br.close();
	        	}
	        	catch(IOException ex)
	        	{
	        		ex.printStackTrace();
	        	}
	        }
	    });
	    
    	
	    //hide all buttons
	    hideProduct();
        hideCustomer();
        hideOrder();
        
		//Customer Operations
	    customerop.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	revealCustomer();
	        	hideProduct();
	        	hideOrder();
	        	
	    	    addcustomer.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	cname=customername.getText();
	    	        	address=customeraddress.getText();
	    	        	phone=customerphone.getText();
	    	        	CustomerAdministration.insert(new Customer(0,cname,address,phone));
	    	        	showCustomers(250, 150);
	    	        }
	    	    });
	    	    
	        	deletecustomer.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
	    	        		cid=Integer.parseInt(customerid.getText());
	    	        		CustomerAdministration.delete(cid);
		    	        	showCustomers(250, 150);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID is an int");
	    	        		customerid.setText("Type ID");
	    	        	}
	    	        }
	    	    });
	        	 	    
	        	updatecustomer.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
	    	        		cid=Integer.parseInt(customerid.getText());
		    	        	cname=customername.getText();
		    	        	address=customeraddress.getText();
		    	        	phone=customerphone.getText();
		    	        
		    	        	CustomerAdministration.update(cid, cname, address,phone);
		    	        	showCustomers(250, 150);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID is an int");
	    	        		customerid.setText("Type ID");
	    	        	}
	    	        }
	    	    });
	            
	    		showCustomers(250, 150);
	        }
	    });
	    //end customer
	    
	    //product operations
	    productop.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	revealProduct();
	        	hideCustomer();
	        	hideOrder();
	        	
	        	addproduct.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	pname=productname.getText();
	    	        	price=Integer.parseInt(productprice.getText());
	    	        	description=productdescription.getText();
	    	        	stock=Integer.parseInt(productstock.getText());
	    	        	WarehouseAdministration.insert(new Product(0,pname,price,description,stock));
	    	        	showProducts(250, 350);
	    	        }
	    	    });
	            
	    	    deleteproduct.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
	    	        		pid=Integer.parseInt(productid.getText());
		    	        	WarehouseAdministration.delete(pid);
		    	        	showProducts(250, 350);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID is an int");
	    	        		productid.setText("Type ID");
	    	        	}
	    	        }
	    	    });
    
	        	updateproduct.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
	    	        		pid=Integer.parseInt(productid.getText());
		    	        	pname=productname.getText();
		    	        	price=Integer.parseInt(productprice.getText());
		    	        	description=productdescription.getText();
		    	        	stock=Integer.parseInt(productstock.getText());
		    	        	WarehouseAdministration.update(pid, pname, price, description, stock);
		    	        	showProducts(250, 350);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID is an int");
	    	        		productid.setText("Type ID");
	    	        	}

	    	        }
	    	    });
	    	    showProducts(250, 350);
	        }
	    });
		//end product
	    
	    //order operations
	    orderop.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	revealOrder();
	        	hideCustomer();
	        	hideProduct();
	        	
	        	addorder.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
		    	        	ocid=Integer.parseInt(ordercid.getText());
		    	        	paymentmethod=payment.getText();
		    	        	oid=OrderProcessing.insertOrder(new Order(0,ocid,paymentmethod));
		    	        	showOrders(250, 550);
		    	        	
		    	        	addproducttoorder.setVisible(true);
		    	        	neworder.setVisible(true);
		    	        	orderpid.setVisible(true);
		    	        	orderpq.setVisible(true);
		    	        	addorder.setVisible(false);
		    	        	ordercid.setVisible(false);
		    	        	payment.setVisible(false);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID is an int");
	    	        		ordercid.setText("Type ID");
	    	        	}
	    	        }
	    	    });
	        	
	        	addproducttoorder.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	try
	    	        	{
		    	        	opid=Integer.parseInt(orderpid.getText());
		    	        	opq=Integer.parseInt(orderpq.getText());
		    	        	Product updateQuantityProduct= WarehouseAdministration.findById(opid);
		    	        	if(updateQuantityProduct.getStock()-opq>0)
		    	        	{
		    	        		WarehouseAdministration.changeQuantity(opid, updateQuantityProduct.getStock()-opq);
		    	        		OrderProcessing.insertDetail(new OrderDetail(oid,opid,opq));
		    	        	}
		    	        	else
		    	        		showError("Not enough stock.");
		    	        	showOrders(250, 550);
		    	        	showProducts(250, 350);
	    	        	}
	    	        	catch(NumberFormatException nfe)
	    	        	{
	    	        		showError("ID and quantity are integer numbers.");
	    	        		ordercid.setText("Type ID");
	    	        		orderpq.setText("Type quantity");
	    	        	}
	    	        }
	    	    });
	        	neworder.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
			        	addproducttoorder.setVisible(false);
			        	neworder.setVisible(false);
			        	orderpid.setVisible(false);
			        	orderpq.setVisible(false);
			        	addorder.setVisible(true);
			        	ordercid.setVisible(true);
			        	payment.setVisible(true);
			        	ordercid.setText("");
			        	payment.setText("");
			        	showOrders(250, 550);
			        	ordercid.setText("Type Customer ID");
			        	payment.setText("Type Payment Method");
	    	        }
	    	    });
	        	
	        	deleteorder.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	ocid=Integer.parseInt(ordercid.getText());
	    	        	oid=OrderProcessing.delete(ocid);
	    	        	showOrders(250, 550);
	    	        }
	    	    });
	        	
	        	showCustomers(250, 150);
	        	showProducts(250, 350);
	        	showOrders(250, 550);
	        }
	    });
	    
		content.setVisible(true);
		content.setResizable(false);
		content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * @param object an object that can be a customer/product/order
	 * @return a list of fields values of the object
	 */
	public static List<Object> retrieveProperties(Object object) 
	{
		Object value;
		List<Object> properties = new ArrayList<>();
		for (Field field : object.getClass().getDeclaredFields()) 
		{
			field.setAccessible(true);
			try
			{
				value = field.get(object);
				properties.add(value);
			} 
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	/**
	 * function that creates a jtable using the parameters
	 * @param objects list of objects that can be costumers, products or orders
	 * @param columnNames - names of the columns
	 * @return a JTable
	 */
	// method for creating a JTable
	JTable createTable(List<Object> objects, Object columnNames[])
	{
		JTable table = null;
		List<Object> properties = retrieveProperties(objects.get(0)); // properties is the list of fields of an object from the list of objects
		Object rowData[][] = new Object[objects.size()][properties.size()]; // matrix rowData has object size rows and properties size columns
		int i = 0; // used as row iterator in the matrix
		for(Object obj: objects) 
		{
			properties = retrieveProperties(objects.get(i)); //gets the value of the fields of the next object
			int j = 0; // used as column iterator in the matrix
			for(Object prop: properties)
				rowData[i][j++] = prop; // put the data in the matrix
			i++;
		}
		table = new JTable(rowData,columnNames); // create the table using the matrix and a parameter
		return table;
	}
	
	/**
	 * the position of the left corner
	 * @param x of the corner
	 * @param y of the corner
	 */
	public void showCustomers(int x, int y)
	{
		Connection c = ConnectionFactory.getConnection();
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM customer");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Object columnNames[] = { "ID", "Name", "Address", "Phone" };

			List<Object> customers = new ArrayList<>();
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("ID");
				String name = resultSet.getString("Name");
				String address = resultSet.getString("Address");
				String phone = resultSet.getString("Phone");
				Customer customer = new Customer(id, name, address, phone);
				customers.add(customer);
			}
			customertable=createTable(customers,columnNames);
			customertable.setEnabled(false);
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		customerscroll = new JScrollPane(customertable);
		customerscroll.setEnabled(false);
	    content.add(customerscroll).setBounds(x,y,800,200);
	}
	
	/**
	 * the position of the left corner
	 * @param x of the corner
	 * @param y of the corner
	 */
	public void showProducts(int x, int y)
	{
		Connection c = ConnectionFactory.getConnection();
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM product");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Object columnNames[] = { "ID", "Name", "Price", "Description","Stock" };

			List<Object> products = new ArrayList<>();
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("ID");
				String name = resultSet.getString("Name");
				int price = resultSet.getInt("Price");
				String description = resultSet.getString("Description");
				int stock = resultSet.getInt("Stock");
				Product product = new Product(id, name, price, description, stock);
				products.add(product);
			}
			
			producttable=createTable(products,columnNames);
			producttable.setEnabled(false);
			
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
		productscroll = new JScrollPane(producttable);
	    content.add(productscroll).setBounds(x,y,800,200);
	}
	
	/**
	 * the position of the left corner
	 * @param x of the corner
	 * @param y of the corner
	 */
	public void showOrders(int x, int y)
	{
		Connection c = ConnectionFactory.getConnection();
		try
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM orders INNER JOIN orderdetail ON orders.ID=orderdetail.OrderID");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			Object columnNames[] = { "ID", "CustomerID", "PaymentMethod","ProductID", "Quantity" };

			//reflection technique
			class Everything
			{
				public Order ord;
				public OrderDetail orddet;
				Everything(Order ord, OrderDetail orddet)
				{
					this.ord=ord;
					this.orddet=orddet;
				}
			}
			List<Everything> orders = new ArrayList<>();
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("ID");
				int cid = resultSet.getInt("CustomerID");
				String pm = resultSet.getString("PaymentMethod");
				Order order = new Order(id, cid, pm);
				int pid = resultSet.getInt("ProductID");
				int pq = resultSet.getInt("Quantity");
				OrderDetail orderdetail = new OrderDetail(id,pid,pq);
				
				Everything ord=new Everything(order,orderdetail);
				orders.add(ord);
			}
			Object rowData[][] = new Object[orders.size()][5];
			int i=0;
			for(Everything ord: orders) 
			{
				rowData[i][0] = ord.ord.getId();
				rowData[i][1] = ord.ord.getCustomerId();
				rowData[i][2] = ord.ord.getPaymentMethod();
				rowData[i][3] = ord.orddet.getProductID();
				rowData[i++][4] = ord.orddet.getQuantity();
			}
			ordertable=new JTable(rowData,columnNames);
			ordertable.setEnabled(false);
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}
	    
		orderscroll = new JScrollPane(ordertable);
	    content.add(orderscroll).setBounds(x,y,800,200);
	}
	
	public void revealCustomer()
	{
		addcustomer.setVisible(true);
		deletecustomer.setVisible(true);
		updatecustomer.setVisible(true);
		addproduct.setVisible(true);
		deleteproduct.setVisible(true);
		updateproduct.setVisible(true);
		customerid.setVisible(true);
		customername.setVisible(true);
		customeraddress.setVisible(true);
		customerphone.setVisible(true);
	}
	
	public void revealProduct()
	{
		addproduct.setVisible(true);
		deleteproduct.setVisible(true);
		updateproduct.setVisible(true);
		productid.setVisible(true);
		productname.setVisible(true);
		productprice.setVisible(true);
		productdescription.setVisible(true);
		productstock.setVisible(true);
	}
	
	public void revealOrder()
	{
        addorder.setVisible(true);
	    ordercid.setVisible(true);
    	payment.setVisible(true);
    	deleteorder.setVisible(true);

	}
	
	public void hideCustomer()
	{
		addcustomer.setVisible(false);
		deletecustomer.setVisible(false);
		updatecustomer.setVisible(false);
		customerid.setVisible(false);
		customername.setVisible(false);
		customeraddress.setVisible(false);
		customerphone.setVisible(false);
	}
	
	public void hideProduct()
	{
		addproduct.setVisible(false);
		deleteproduct.setVisible(false);
		updateproduct.setVisible(false);
		productid.setVisible(false);
		productname.setVisible(false);
		productprice.setVisible(false);
		productdescription.setVisible(false);
		productstock.setVisible(false);
	}
	
	public void hideOrder()
	{
        addorder.setVisible(false);
        addproducttoorder.setVisible(false);
        neworder.setVisible(false);
        deleteorder.setVisible(false);
	    ordercid.setVisible(false);
    	payment.setVisible(false);
    	orderpid.setVisible(false);
    	orderpq.setVisible(false);
	}
	
	/**
	 * @param errMessage the message shown by the error
	 */
	public static void showError(String errMessage)
	{
		JOptionPane.showMessageDialog(content, errMessage);
	}
	
	public static void main(String[] args) 
	{
		new GUI();
	}
}
