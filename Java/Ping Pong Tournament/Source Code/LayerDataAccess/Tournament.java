package org.LayerDataAccess;

public class Tournament {
	int id;
	String name;
	String status;
	int winner=0;
	public Tournament(int id, String name, String status) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.winner=0;
	}
	
	public Tournament(int id, String name, String status, int winner) {
		super();
		this.id = id;
		this.name = name;
		this.status = status;
		this.winner=winner;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Tournament [id=" + id + ", name=" + name + ", status=" + status + "]";
	}
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	
}
