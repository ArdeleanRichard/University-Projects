package org.LayerDataAccess;

import java.util.ArrayList;

public interface GameDAO {
	public int findTournamentID(int gameID);	
	public int findScore1(int id);
	public int findScore2(int id);
	public int testGame(int id);	
	public Game findById(int id); 	
	public int findGameIdByPlayerId(int playerid); 	
	public int insert(Game game); 
	public int delete(int id);
	public int updateScore(int id, boolean which); 
	public ArrayList<Integer> findAllGamesMatchId(int id); 
	public int deleteAllGamesFromTournament(int id); 	
	public int deleteGamesByMatchId(int matchID);

}
