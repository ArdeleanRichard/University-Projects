package org.LayerDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminDAOImplementation implements AdminDAO {
	protected static final Logger LOGGER = Logger.getLogger(Admin.class.getName());

	/**
	 * @param id the id of the Admin to be found
	 * @return the Admin with the id from the parameter
	 */
	public Admin findById(int id) 
	{
		Admin toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM admin where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String mail = rs.getString("mail");
			String password = rs.getString("password");
			String name = rs.getString("name");
			toReturn = new Admin(id, mail, password, name);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Admin:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public Admin findByMail(String mail) 
	{
		Admin toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM admin where mail = ?");
			findStatement.setString(1, mail);
			rs = findStatement.executeQuery();
			rs.next();
			
			int id = rs.getInt("id");
			String password = rs.getString("password");
			String name = rs.getString("name");
			toReturn = new Admin(id, mail, password, name);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Admin:findByMail " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int insert(Admin admin) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO admin (mail,password,name) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, admin.getMail());
			insertStatement.setString(2, admin.getPassword());
			insertStatement.setString(3, admin.getName());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "admin:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id id of the Admin you want deleted
	 * @return the row affected by the deletion
	 */
	public int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM admin where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
				deleteStatement.executeUpdate();
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Admin:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the Admin you want updated
	 * @param name new name
	 * @param address new address
	 * @param phone new phone
	 * @return the id of the Admin updated
	 */
	public int update(int id, String mail, String password, String name) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE admin SET mail=?, password=?, name=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, mail);
			updateStatement.setString(2, password);
			updateStatement.setString(3, name);
			updateStatement.setInt(4, id);
			if(findById(id)!=null)
				updateStatement.executeUpdate();
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "admin:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}
}
