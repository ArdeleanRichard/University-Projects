package org.LayerDataAccess;

public class Game {
	int id;
	int matchid;
	int score1;
	int score2;
	
	
	
	public Game(int id, int matchid, int score1, int score2) {
		super();
		this.id = id;
		this.matchid = matchid;
		this.score1 = score1;
		this.score2 = score2;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public int getMatchid() {
		return matchid;
	}

	public void setMatchid(int matchid) {
		this.matchid = matchid;
	}
	
	
}
