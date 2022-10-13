package org.LayerDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PlayerDAOImplementation implements PlayerDAO{
	protected static final Logger LOGGER = Logger.getLogger(Player.class.getName());

	/**
	 * @param id the id of the player to be found
	 * @return the Player with the id from the parameter
	 */
	public Player findById(int id) 
	{
		Player toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM player where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String mail = rs.getString("mail");
			String password = rs.getString("password");
			String name = rs.getString("name");
			toReturn = new Player(id, mail, password, name);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Player:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public Player findByMail(String mail) 
	{
		Player toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM player where mail = ?");
			findStatement.setString(1, mail);
			rs = findStatement.executeQuery();
			rs.next();
			
			int id = rs.getInt("id");
			String password = rs.getString("password");
			String name = rs.getString("name");
			toReturn = new Player(id, mail, password, name);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"player:findByMail " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int insert(Player player) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO player (mail,password,name) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, player.getMail());
			insertStatement.setString(2, player.getPassword());
			insertStatement.setString(3, player.getName());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "player:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id id of the Player you want deleted
	 * @return the row affected by the deletion
	 */
	public int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=-1;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM player where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
			{
				deleteStatement.executeUpdate();
				rowsaffected=1;
			}
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Player:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the Player you want updated
	 * @param name new name
	 * @param address new address
	 * @param phone new phone
	 * @return the id of the Player updated
	 */
	public int update(int id, String mail, String password, String name) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updated = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE player SET mail=?, password=?, name=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, mail);
			updateStatement.setString(2, password);
			updateStatement.setString(3, name);
			updateStatement.setInt(4, id);
			if(findById(id)!=null)
			{
				updateStatement.executeUpdate();
				updated=1;
			}
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "player:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updated;
	}

	public String findNameById(int winnerID) {
		String toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT name FROM player where id = ?");
			findStatement.setLong(1, winnerID);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			toReturn = name;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Player:findNameById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
}
