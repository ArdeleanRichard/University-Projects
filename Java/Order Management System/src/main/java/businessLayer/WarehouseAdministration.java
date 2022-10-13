package businessLayer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataAccessLayer.ConnectionFactory;
import model.Product;
import presentation.GUI;

/**
 * @author Ardelean Eugen Richard
 * class used to manage the table of products
 */
public class WarehouseAdministration //extends AbstractAdministration<Product>
{

	protected static final Logger LOGGER = Logger.getLogger(Product.class.getName());

	/**
	 * @param id the id you want to find in the table 
	 * @return the product with the id you are looking for
	 */
	public static Product findById(int id) 
	{
		Product toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM product where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			int price = rs.getInt("price");
			String description = rs.getString("description");
			int stock = rs.getInt("stock");
			toReturn = new Product(id, name, price, description, stock);
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
		return toReturn;
	}

	/**
	 * @param product the product you want to insert
	 * @return the id of the inserted product
	 */
	public static int insert(Product product) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO product (name,price,description,stock) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, product.getName());
			insertStatement.setInt(2, product.getPrice());
			insertStatement.setString(3, product.getDescription());
			insertStatement.setInt(4, product.getStock());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "product:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id of the product you want deleted
	 * @return the row affected by the deletion
	 */
	public static int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM product where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
				deleteStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "product:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the product you want updated
	 * @param name new name
	 * @param price new price
	 * @param description new description
	 * @param stock new stock number
	 * @return the id of the updated product
	 */
	public static int update(int id, String name, int price, String description, int stock) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE product SET name=?, price=?, description=?, stock=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, name);
			updateStatement.setInt(2, price);
			updateStatement.setString(3, description);
			updateStatement.setInt(4, stock);
			updateStatement.setInt(5, id);
			updateStatement.executeUpdate();
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "product:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}
	
	/**
	 * @param id id of the product you want to change the stock of
	 * @param stock the new stock
	 * @return the id of the product you want to change the stock of
	 */
	public static int changeQuantity(int id, int stock) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE product SET stock=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, stock);
			updateStatement.setInt(2, id);
			if(findById(id)!=null)
				updateStatement.executeUpdate();
			else
				GUI.showError("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "product:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}

}
