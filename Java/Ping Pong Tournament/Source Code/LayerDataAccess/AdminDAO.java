package org.LayerDataAccess;

public interface AdminDAO {
	public Admin findById(int id);
	public Admin findByMail(String mail);
	public int insert(Admin admin);
	public int delete(int id);
	public int update(int id, String mail, String password, String name);
}
