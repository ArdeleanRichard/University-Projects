package org.LayerDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameDAOImplementation {
	protected static final Logger LOGGER = Logger.getLogger(Game.class.getName());

	/**
	 * @param id the id of the Game to be found
	 * @return the Game with the id from the parameter
	 */
	
	public int findTournamentID(int gameID)
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		int tournamentid = 0;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT tournament.id FROM tournament "
					+ "INNER JOIN `match` ON tournament.id=match.tournamentid "
					+ "INNER JOIN game ON `match`.id=game.matchid "
					+ "where game.id = ?");
			findStatement.setLong(1, gameID);
			rs = findStatement.executeQuery();
			rs.next();

			tournamentid = rs.getInt("id");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findTournamentID " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		
		return tournamentid;
	}
	
	public int findScore1(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		int score1 = 0;
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT score1 FROM game where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			score1 = rs.getInt("score1");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findScore1 " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		
		return score1;
	}
	
	public int findScore2(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		int score2=0;
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT score2 FROM game where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			score2 = rs.getInt("score2");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findScore2 " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		
		return score2;
	}
	
	public int testGame(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		int score1 = 0, score2=0;
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT score1, score2 FROM game where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			score1 = rs.getInt("score1");
			score2 = rs.getInt("score2");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		if(score1 == 11 && score2<=9)
			return 1;
		if(score2 == 11 && score1<=9)
			return 2;
		if(score1>9 && score2>9)
		{
			if(score1-score2==2)
				return 1;
			if(score2-score1==2)
				return 2;
		}
		
		return 0;
		
	}
	
	public Game findById(int id) 
	{
		Game toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM game where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();

			int matchid = rs.getInt("matchid");
			int score1 = rs.getInt("score1");
			int score2 = rs.getInt("score2");
			toReturn = new Game(id, matchid, score1, score2);
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int findGameIdByPlayerId(int playerid) 
	{
		int toReturn = 0;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT game.id FROM game INNER JOIN `match` ON game.matchid=`match`.id WHERE (`match`.player1 = ? OR `match`.player2=?) AND ((game.score1<11 AND game.score2<11) "
					+ "OR (game.score1>=11 AND ((game.score1-game.score2)=0 OR (game.score1-game.score2)=1)) "
					+ "OR (game.score2>=11 AND ((game.score2-game.score1)=0 OR (game.score2-game.score1)=1)))");
			findStatement.setInt(1, playerid);
			findStatement.setInt(2, playerid);
			rs = findStatement.executeQuery();
			rs.next();

			int id = rs.getInt("id");			
			toReturn = id;
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"game:findGameIdByPlayerId" + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int insert(Game game) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO game (matchid,score1,score2) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, game.getMatchid());
			insertStatement.setInt(2, game.getScore1());
			insertStatement.setInt(3, game.getScore2());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "game:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * @param id id of the Game you want deleted
	 * @return the row affected by the deletion
	 */
	public int delete(int id)
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM game where id = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			if(findById(id)!=null)
				deleteStatement.executeUpdate();
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Game:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}
	
	/**
	 * @param id of the Game you want updated
	 * @param name new name
	 * @param address new address
	 * @param phone new phone
	 * @return the id of the Game updated
	 */
	public int updateScore(int id, boolean which) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int newScore = -1;
		try 
		{
			if(which==false)
				updateStatement = dbConnection.prepareStatement("UPDATE game SET score1=? WHERE id=?", Statement.RETURN_GENERATED_KEYS); //AND (game.score1<11 OR ((game.score1-game.score2)=0 OR (game.score1-game.score2)=1))
			else
				updateStatement = dbConnection.prepareStatement("UPDATE game SET score2=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			
			Game game = null;
			if(findById(id)!=null)
				game = findById(id);
			if(which==false)
			{
				newScore = game.getScore1()+1;
				updateStatement.setInt(1, newScore);
			}
			else
			{
				newScore = game.getScore2()+1;
				updateStatement.setInt(1, newScore);
			}
				

			updateStatement.setInt(2, id);
			
			if(findById(id)!=null)
				updateStatement.executeUpdate();
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Game:update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return newScore;
	}

	public ArrayList<Integer> findAllGamesMatchId(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> gamesIDs = new ArrayList<Integer>();
		
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT id FROM game where matchid = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			while(rs.next())
			{
				int gameid = rs.getInt("id");
				gamesIDs.add(gameid);
			}
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:findAllGameMatchId " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return gamesIDs;
	}
	
	public int deleteAllGamesFromTournament(int id) 	
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM game where matchid = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();

		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Game:deleteAllGamesFromTournament " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
	}

	public int deleteGamesByMatchId(int matchID) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			deleteStatement = dbConnection.prepareStatement("DELETE FROM game where matchid = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, matchID);
			deleteStatement.executeUpdate();

		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "Game:deleteGamesByMatchId " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
		
	}

}
