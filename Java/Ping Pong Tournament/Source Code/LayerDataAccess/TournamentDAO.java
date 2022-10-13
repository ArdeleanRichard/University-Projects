package org.LayerDataAccess;

public interface TournamentDAO {
	public Tournament findById(int id); 
	public int findIdByName(String name); 
	public int insert(Tournament tournament, String players); 
	public int delete(int id);
	public int update(int id, String name, String status); 
	public int updateWinner(int id, int winner); 
	public boolean anyRunningTournament();

}
