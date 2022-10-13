package org.LayerDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchDAOImplementation implements MatchDAO{
	protected static final Logger LOGGER = Logger.getLogger(Match.class.getName());
	
	public int findScore1(int matchID) 
	{
		Match toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement winsPlayer1 = null;
		ResultSet rs = null;
		int wp1 = 0;
		try 
		{
			winsPlayer1 = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM game WHERE game.matchid=? AND ((game.score1 = 11 AND game.score2<=9) OR (game.score1>11 AND game.score1-game.score2=2))");
			winsPlayer1.setLong(1, matchID);
			rs = winsPlayer1.executeQuery();
			rs.next();
			wp1 = rs.getInt("rowcount");
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:testMatch " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(winsPlayer1);
			ConnectionFactory.close(dbConnection);
		}
		return wp1;
	}
	
	public int findScore2(int matchID) 
	{
		Match toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement winsPlayer2 = null;
		ResultSet rs = null;
		int wp2 = 0;
		try 
		{
			winsPlayer2 = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM game WHERE game.matchid=? AND ((game.score2 = 11 AND game.score1<=9) OR (game.score2>11 AND game.score2-game.score1=2))");
			winsPlayer2.setLong(1, matchID);
			rs = winsPlayer2.executeQuery();
			rs.next();
			wp2 = rs.getInt("rowcount");
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:testMatch " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(winsPlayer2);
			ConnectionFactory.close(dbConnection);
		}
		return wp2;
	}
	
	public int findMatchByPlayerId(int playerID) 
	{
		int toReturn = 0;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT id FROM `match` where player1 = ? or player2 =?");
			findStatement.setLong(1, playerID);
			findStatement.setLong(2, playerID);
			rs = findStatement.executeQuery();
			rs.next();

			
			toReturn = rs.getInt("id");
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:findMatchByPlayerId " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public int testMatch(int matchID) 
	{
		Match toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement winsPlayer1 = null;
		PreparedStatement winsPlayer2 = null;
		ResultSet rs = null;
		
		try 
		{
			winsPlayer1 = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM game WHERE game.matchid=? AND ((game.score1 = 11 AND game.score2<=9) OR (game.score1>11 AND game.score1-game.score2=2))");
			winsPlayer1.setLong(1, matchID);
			rs = winsPlayer1.executeQuery();
			rs.next();
			int wp1 = rs.getInt("rowcount");
			
			winsPlayer2 = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM game WHERE game.matchid=? AND ((game.score2 = 11 AND game.score1<=9) OR (game.score2>11 AND game.score2-game.score1=2))");
			winsPlayer2.setLong(1, matchID);
			rs = winsPlayer2.executeQuery();
			rs.next();
			int wp2 = rs.getInt("rowcount");
			
			if(wp1==3)
				return 1;
			if(wp2==3)
				return 2;
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:testMatch " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(winsPlayer1);
			ConnectionFactory.close(winsPlayer2);
			ConnectionFactory.close(dbConnection);
		}
		return 0;
	}
	
	public Match findById(int id) 
	{
		Match toReturn = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT * FROM `match` where id=?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int tournamentid = rs.getInt("tournamentid");
			int player1 = rs.getInt("player1");
			int player2 = rs.getInt("player2");
			toReturn = new Match(id, tournamentid, player1, player2);
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return toReturn;
	}
	
	public boolean test4Matches(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner!=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countWon = rs.getInt("rowcount");
			
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countNot = rs.getInt("rowcount");
			
			if(countWon==4 && countNot==0)
				return true;
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return false;
	}
	
	public ArrayList<Integer> getSemiFinalsWinners(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT winner FROM `match` where tournamentid=?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			
			for(int i=0;i<4;i++)
			{
				rs.next();
				winners.add(rs.getInt("winner"));
			}
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return winners;
	}
	
	public boolean test6Matches(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner!=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countWon = rs.getInt("rowcount");
			
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countNot = rs.getInt("rowcount");
			if(countWon==2 && countNot==0)
				return true;
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return false;
	}
	
	public ArrayList<Integer> getFinalsWinners(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> winners = new ArrayList<Integer>();
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT winner FROM `match` where tournamentid=?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			
			for(int i=0;i<2;i++)
			{
				rs.next();
				winners.add(rs.getInt("winner"));
			}
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return winners;
	}
	
	public boolean test7Matches(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner!=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countWon = rs.getInt("rowcount");
			
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where tournamentid=? AND winner=0");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int countNot = rs.getInt("rowcount");
			if(countWon==1 && countNot==0)
				return true;
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return false;
	}
	
	public int getFinalsWinner(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		int winner = 0;
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT winner FROM `match` where tournamentid=?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			
			rs.next();
			winner=rs.getInt("winner");
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:test4Matches " + e.getMessage());
		}
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return winner;
	}
	
	public int insert(Match match) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try 
		{
			insertStatement = dbConnection.prepareStatement("INSERT INTO `match` (tournamentID,player1,player2,winner) VALUES (?,?,?,0)", Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, match.getTournamentID());
			insertStatement.setInt(2, match.getPlayer1());
			insertStatement.setInt(3, match.getPlayer2());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) 
			{
				insertedId = rs.getInt(1);
			}
			for(int i=0;i<5;i++)
			{
				GameDAOImplementation gameDAO = new GameDAOImplementation();
				Game newGame = new Game(0, insertedId, 0, 0);
				gameDAO.insert(newGame);
			}
			
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	public ArrayList<Integer> findAllMatchesTournmanetId(int id) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		
		ArrayList<Integer> matchIDs = new ArrayList<Integer>();
		
		
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT id FROM `match` where tournamentid = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			while(rs.next())
			{
				int matchid = rs.getInt("id");
				matchIDs.add(matchid);
			}
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:findAllMatchesTournmanetId " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return matchIDs;
	}
	
	public int deleteAllMatchesFromTournament(int id) {
		Connection dbConnection = ConnectionFactory.getConnection();
		
		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			ArrayList<Integer> matchIDs = new ArrayList<Integer>();
			matchIDs=findAllMatchesTournmanetId(id);
			
			for(int matchid: matchIDs)
			{
				GameDAOImplementation gameDAO = new GameDAOImplementation();
				gameDAO.deleteAllGamesFromTournament(matchid);
				
			}
			
			deleteStatement = dbConnection.prepareStatement("DELETE FROM `match` where tournamentid = ?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, id);
			deleteStatement.executeUpdate();


		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match:deleteAllMatchesFromTournament " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
		
	}
	
	public int findIdMatch(int playerid) {
		Connection dbConnection = ConnectionFactory.getConnection();
		ResultSet rs = null;
		PreparedStatement findStatement = null;
		int id=0;
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT id FROM `match` where player1 = ? OR player2=?", Statement.RETURN_GENERATED_KEYS);
			findStatement.setInt(1, playerid);
			findStatement.setInt(2, playerid);
			rs = findStatement.executeQuery();
			rs.next();
			
			id = rs.getInt("id");

		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match:deleteMatches " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return id;
		
	}
	
	public int deleteMatches(int playerid) {
		Connection dbConnection = ConnectionFactory.getConnection();
		
		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{
			int matchid = findIdMatch(playerid);
			
			GameDAOImplementation gameDAO = new GameDAOImplementation();
			gameDAO.deleteAllGamesFromTournament(matchid);
			
			deleteStatement = dbConnection.prepareStatement("DELETE FROM `match` where player1 = ? OR player2=?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, playerid);
			deleteStatement.setInt(2, playerid);
			deleteStatement.executeUpdate();


		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match:deleteMatches " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
		
	}
	
	public int delete(int matchid) {
		Connection dbConnection = ConnectionFactory.getConnection();
		
		PreparedStatement deleteStatement = null;
		int rowsaffected=0;
		try 
		{			
			GameDAOImplementation gameDAO = new GameDAOImplementation();
			gameDAO.deleteAllGamesFromTournament(matchid);
			
			deleteStatement = dbConnection.prepareStatement("DELETE FROM `match` where id=?", Statement.RETURN_GENERATED_KEYS);
			deleteStatement.setInt(1, matchid);
			deleteStatement.executeUpdate();


		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return rowsaffected;
		
	}
	
	public int update(int id, int winner) 
	{
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement updateStatement = null;
		int updatedId = -1;
		try 
		{
			updateStatement = dbConnection.prepareStatement("UPDATE `match` SET winner=? WHERE id=?", Statement.RETURN_GENERATED_KEYS);
			updateStatement.setInt(1, winner);
			updateStatement.setInt(2, id);
			if(findById(id)!=null)
				updateStatement.executeUpdate();
			else
				System.out.println("The ID you introduced cannot be found.");
		} 
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING, "match: update " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return updatedId;
	}

	public int findWinner(int id, int player) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		int winner=0;
		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT player1, player2 FROM `match` where id = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			if(player==1)
				winner = rs.getInt("player1");
			else
				winner = rs.getInt("player2");
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:findWinner " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return winner;
	}

	public int whichPlayer(int id) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;

		try 
		{
			findStatement = dbConnection.prepareStatement("SELECT COUNT(*) as rowcount FROM `match` where player2 = ?");
			findStatement.setLong(1, id);
			rs = findStatement.executeQuery();
			rs.next();
			
			int rowcount = rs.getInt("rowcount");
			if(rowcount==0)
				return 1;
			else
				return 2;
			
		}
		catch (SQLException e)
		{
			LOGGER.log(Level.WARNING,"Match:whichPlayer " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return 0;
	}
}
