package businessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataAccessLayer.ConnectionFactory;
import model.Order;
import model.OrderDetail;
import model.Product;
import presentation.GUI;

/**
 * @author Ardelean Eugen Richard
 * class that is used to make operations on the table of orders
 */
public class OrderProcessing {
	protected static final Logger LOGGER = Logger.getLogger(Order.class.getName());
	
	/**
	 * @param id the id you want to find in the table 
	 * @return the order with the id you are looking for
	 */
	public static Order findById(int id) 
	{
		Order toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM orders where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			int customerid = rs.getInt("customerid");
			String paymentmethod = rs.getString("paymentmethod");
			toReturn = new Order(id, customerid, paymentmethod);
			return toReturn;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"product:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return new Order(0, 0, "");
	}
	
	/**
	 * @param order the order you want inserted in the table of orders
	 * @return the id of the inserted order
	 */
	public static int insertOrder(Order order) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO orders (customerid, paymentmethod) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			
			insertStatement.setInt(1, order.getCustomerId());
			insertStatement.setString(2, order.getPaymentMethod());
			if(CustomerAdministration.findById(order.getCustomerId())!=null)
				insertStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");

			
			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "orders:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	public static int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		PreparedStatement deleteStatement2 = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement2 = dbConnection.prepareStatement("DELETE FROM orderdetail where orderid = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement2.setInt(1, id);
			rowsaffected = deleteStatement2.executeUpdate();
			
			deleteStatement = dbConnection.prepareStatement("DELETE FROM orders where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			rowsaffected = deleteStatement.executeUpdate();
			if(rowsaffected != 0)
				System.out.println("Delete achieved");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "orders:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param detail the detail you want inserted, the products for the order
	 * @return the id of the inserted order detail
	 */
	public static int insertDetail(OrderDetail detail) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO orderdetail (orderid,productid, quantity) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			insertStatement.setInt(1, detail.getOrderID());
			insertStatement.setInt(2, detail.getProductID());
			insertStatement.setInt(3, detail.getQuantity());
			
			if(WarehouseAdministration.findById(detail.getProductID())!=null)
				insertStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");

			
			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "orderdetail:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
	
	/**
	 * @param id the id of the order you want a file generated of
	 * @return a list of objects that represent the fields of the order
	 */
	public static Object[][] generateFiles(int id)
	{
		Connection c = ConnectionFactory.getConnection();
		
		class ProductCharacteristics
		{
			String numeprodus;
			int pretprodus;
			int cantitate;
			public ProductCharacteristics(String numeprodus, int pretprodus, int cantitate) 
			{
				super();
				this.numeprodus = numeprodus;
				this.pretprodus = pretprodus;
				this.cantitate = cantitate;
			}
			public String getNumeprodus() 
			{
				return numeprodus;
			}
			public int getPretprodus() 
			{
				return pretprodus;
			}
			public int getCantitate() 
			{
				return cantitate;
			}
			public int getTotal()
			{
				return pretprodus*cantitate;
			}
		}
		List<ProductCharacteristics> productList=new ArrayList<>();
		
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT product.name, product.price, orderdetail.quantity FROM orderdetail INNER JOIN product ON product.ID=orderdetail.ProductID WHERE OrderID=?");
			
			preparedStatement.setInt(1, id);
			ResultSet rs=preparedStatement.executeQuery();
			
			while(rs.next())
			{
				String name=rs.getString(1);
				int price=rs.getInt(2);
				int quantity=rs.getInt(3);
				productList.add(new ProductCharacteristics(name,price,quantity));
			}
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		Object[][] objects=new Object[productList.size()][4];
		int i=0;
		for(ProductCharacteristics product: productList)
		{
			objects[i][0]=product.getNumeprodus();
			objects[i][1]=product.getPretprodus();
			objects[i][2]=product.getCantitate();
			objects[i++][3]=product.getTotal();
		}
		return objects;
	}
}
