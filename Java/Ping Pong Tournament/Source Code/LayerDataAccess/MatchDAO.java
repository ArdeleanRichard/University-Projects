package org.LayerDataAccess;

import java.util.ArrayList;

public interface MatchDAO {
	public int findScore1(int matchID); 
	public int findScore2(int matchID); 
	public int findMatchByPlayerId(int playerID); 
	public Match findById(int id); 
	public boolean test4Matches(int id); 
	public ArrayList<Integer> getSemiFinalsWinners(int id); 	
	public boolean test6Matches(int id); 
	public ArrayList<Integer> getFinalsWinners(int id); 
	public boolean test7Matches(int id); 
	public int getFinalsWinner(int id); 
	public int insert(Match match); 
	public ArrayList<Integer> findAllMatchesTournmanetId(int id); 
	public int deleteAllMatchesFromTournament(int id); 
	public int findIdMatch(int playerid); 
	public int deleteMatches(int playerid); 
	public int delete(int matchid); 	
	public int update(int id, int winner); 
	public int findWinner(int id, int player);
	public int whichPlayer(int id);

}
