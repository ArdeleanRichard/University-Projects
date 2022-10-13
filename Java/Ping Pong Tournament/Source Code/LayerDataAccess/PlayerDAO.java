package org.LayerDataAccess;

public interface PlayerDAO {
	public Player findById(int id); 
	public Player findByMail(String mail); 
	public int insert(Player player); 
	public int delete(int id);
	public int update(int id, String mail, String password, String name); 
	public String findNameById(int winnerID);
}
