package businessLayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataAccessLayer.ConnectionFactory;
import model.Customer;
import model.Order;
import presentation.GUI;

/**
 * @author Ardelean Eugen Richard
 * class used to manage the table of customers
 */
public class CustomerAdministration
{
	
	protected static final Logger LOGGER = Logger.getLogger(Customer.class.getName());

	/**
	 * @param id the id of the customer to be found
	 * @return the customer with the id from the parameter
	 */
	public static Customer findById(int id) 
	{
		Customer toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM customer where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String address = rs.getString("address");
			String phone = rs.getString("phone");
			toReturn = new Customer(id, name, address, phone);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"customer:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}

	/**
	 * @param customer the customer that you want added in the table
	 * @return the id of the customer inserted
	 */
	public static int insert(Customer customer) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO customer (name,address,phone) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, customer.getName());
			insertStatement.setString(2, customer.getAddress());
			insertStatement.setString(3, customer.getPhone());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "customer:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id id of the customer you want deleted
	 * @return the row affected by the deletion
	 */
	public static int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		Order order;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM customer where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
				deleteStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "customer:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the customer you want updated
	 * @param name new name
	 * @param address new address
	 * @param phone new phone
	 * @return the id of the customer updated
	 */
	public static int update(int id, String name, String address, String phone) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE customer SET name=?, address=?, phone=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, name);
			updateStatement.setString(2, address);
			updateStatement.setString(3, phone);
			updateStatement.setInt(4, id);
			if(findById(id)!=null)
				updateStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "customer:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}
}
