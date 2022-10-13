package org.LayerDataAccess;

public class Match {
	int id;
	int tournamentID;
	int player1;
	int player2;
	int winner;

	public Match(int id, int tournamentID, int player1, int player2) {
		super();
		this.id = id;
		this.tournamentID = tournamentID;
		this.player1 = player1;
		this.player2 = player2;
		this.winner = 0;
	}

	public Match(int id, int tournamentID, int player1, int player2, int winner) {
		super();
		this.id = id;
		this.tournamentID = tournamentID;
		this.player1 = player1;
		this.player2 = player2;
		this.winner = winner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTournamentID() {
		return tournamentID;
	}

	public void setTournamentID(int tournamentID) {
		this.tournamentID = tournamentID;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", tournamentID=" + tournamentID + "]";
	}

	public int getPlayer1() {
		return player1;
	}

	public void setPlayer1(int player1) {
		this.player1 = player1;
	}

	public int getPlayer2() {
		return player2;
	}

	public void setPlayer2(int player2) {
		this.player2 = player2;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}
	
}
