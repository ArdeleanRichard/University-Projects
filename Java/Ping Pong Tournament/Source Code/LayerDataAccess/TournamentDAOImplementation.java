package org.LayerDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TournamentDAOImplementation implements TournamentDAO {
	protected static final Logger LOGGER = Logger.getLogger(Tournament.class.getName());

	/**
	 * @param id the id of the Tournament to be found
	 * @return the Tournament with the id from the parameter
	 */
	public Tournament findById(int id) 
	{
		Tournament toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM tournament where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String status = rs.getString("status");
			toReturn = new Tournament(id, name, status);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Tournament:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int findIdByName(String name) 
	{
		int toReturn = 0;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT id FROM tournament where name = ?");
			findStatement.setString(1, name);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");
			toReturn = id;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Tournament:findByName " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int insert(Tournament tournament, String players) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{

			insertStatement = dbConnection.prepareStatement("INSERT INTO tournament (name,status) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, tournament.getName());
			insertStatement.setString(2, tournament.getStatus());
			insertStatement.executeUpdate();
			
			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
			
			
		//random matches
			String player[]=players.split(",");
			
			
			int randomPlayers[] = new int[8];
			
	        ArrayList<String> list = new ArrayList<String>(8);
	        for(int i = 0; i <= 7; i++) {
	            list.add(player[i]);
	        }
	        
	        if(list.size()!=8)
	        {
	        	return 0;
	        }
	        else
	        {
		        int k=0;
		        Random rand = new Random();
	
		        while(list.size() > 0) 
		        {
		            int index = rand.nextInt(list.size());
		            randomPlayers[k++]=Integer.parseInt(list.remove(index));
		        }
				
				MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
				for(int i=0;i<4;i++)
				{
					Match match = new Match(0, insertedId, randomPlayers[2*i], randomPlayers[2*i+1]);
					matchAdministration.insert(match);
				}
	        }
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "tournament:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id id of the Tournament you want deleted
	 * @return the row affected by the deletion
	 */
	public int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		
		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			MatchDAOImplementation matchDAO = new MatchDAOImplementation();
			matchDAO.deleteAllMatchesFromTournament(id);
			
			deleteStatement = dbConnection.prepareStatement("DELETE FROM tournament where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
			{
				deleteStatement.executeUpdate();
				rowsaffected=1;
			}
				
			else
			{
				rowsaffected=-1;
				System.out.println("The ID you introduced cannot be found.");
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "tournament:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the Tournament you want updated
	 * @param name new name
	 * @param address new address
	 * @param phone new phone
	 * @return the id of the Tournament updated
	 */
	public int update(int id, String name, String status) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		
		ResultSet rs = null;
		PreparedStatement updateStatement = null;
		int updated = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE tournament SET name=?, status=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setString(1, name);
			updateStatement.setString(2, status);
			updateStatement.setInt(3, id);
			
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
			LOGGER.log(Level.WARNING, "tournament:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updated;
	}
	public int updateWinner(int id, int winner) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE tournament SET winner=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, winner);
			updateStatement.setInt(2, id);
			if(findById(id)!=null)
			{
				updateStatement.executeUpdate();
			}
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "tournament:updateWinner " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}
	
	public boolean anyRunningTournament()
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement running = null;
		ResultSet rs = null;
		int count=-1;
		
		try 
		{
			running = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM tournament WHERE winner=0");
			rs = running.executeQuery();
			rs.next();
			count = rs.getInt("rowcount");
			if(count == 0)
			{
				return false;
			}
			else
				return true;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:testMatch " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(running);
			ConnectionFactory.close(dbConnection);
		}
		return true;
	}
}
