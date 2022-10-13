package org.LayerBusiness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import org.LayerDataAccess.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Functions 
{
	public TableView<Tournament> createTournamentTable()
	{
		TableColumn<Tournament, String> idColumn = new TableColumn<>("Id");
		//Whatever you write, it looks for getX, so if you dont have, it wont work
		idColumn.setMinWidth(50);
		idColumn.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Id"));
		
		TableColumn<Tournament, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(250);
		nameColumn.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Name"));
		
		TableColumn<Tournament, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setMinWidth(100);
		statusColumn.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Status"));
		
		TableColumn<Tournament, String> winnerColumn = new TableColumn<>("Winner");
		winnerColumn.setMinWidth(50);
		winnerColumn.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Winner"));
		
		TableView<Tournament> tournamentTable;
		
        tournamentTable = new TableView<>();
        tournamentTable.setItems(getTournaments());
        tournamentTable.getColumns().addAll(idColumn, nameColumn, statusColumn, winnerColumn);
        
        return tournamentTable;
	}
	
	public TableView<Match> createMatchTable()
	{
		TableColumn<Match, String> idColumn2 = new TableColumn<>("Id");
		idColumn2.setMinWidth(100);
		idColumn2.setCellValueFactory(new PropertyValueFactory<Match, String>("Id"));
		
		TableColumn<Match, String> tournamentIDColumn = new TableColumn<>("TournamentID");
		tournamentIDColumn.setMinWidth(100);
		tournamentIDColumn.setCellValueFactory(new PropertyValueFactory<Match, String>("TournamentID"));
		
		TableColumn<Match, String> player1IDColumn = new TableColumn<>("Player1");
		player1IDColumn.setMinWidth(100);
		player1IDColumn.setCellValueFactory(new PropertyValueFactory<Match, String>("Player1"));
		
		TableColumn<Match, String> player2IDColumn = new TableColumn<>("Player2");
		player2IDColumn.setMinWidth(100);
		player2IDColumn.setCellValueFactory(new PropertyValueFactory<Match, String>("Player2"));
		
		TableColumn<Match, String> winnerColumn = new TableColumn<>("Winner");
		winnerColumn.setMinWidth(50);
		winnerColumn.setCellValueFactory(new PropertyValueFactory<Match, String>("Winner"));
		
		TableView<Match> matchTable;
        matchTable = new TableView<>();
        matchTable.setItems(getMatches());
        matchTable.getColumns().addAll(idColumn2, tournamentIDColumn, player1IDColumn, player2IDColumn, winnerColumn);
        return matchTable;
	}
	
	public ObservableList<Tournament> getTournaments()
	{
		ObservableList<Tournament> tournaments = FXCollections.observableArrayList();
		
		Connection c = ConnectionFactory.getConnection();
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM tournament");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String status = resultSet.getString("status");
				int winner = resultSet.getInt("winner");
				Tournament tournament = new Tournament(id, name, status, winner);
				tournaments.add(tournament);
			}	
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}


		return tournaments;
	}
	
	public ObservableList<Match> getMatches()
	{
		ObservableList<Match> matches = FXCollections.observableArrayList();
		
		Connection c = ConnectionFactory.getConnection();
		try 
		{
			PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM `match`");
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) 
			{
				int id = resultSet.getInt("id");
				int tournamentID = resultSet.getInt("tournamentid");
				int player1 = resultSet.getInt("player1");
				int player2 = resultSet.getInt("player2");
				int winner = resultSet.getInt("winner");
				Match match = new Match(id, tournamentID, player1, player2, winner);
				matches.add(match);
			}	
		}
		catch (SQLException e1) 
		{
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		}

		return matches;
	}
	
	public int createTournament(String createTourNameInput, String createTourStatusInput, String createTourPlayersInput)
	{
		TournamentDAOImplementation tourAdministration = new TournamentDAOImplementation();
		if(createTourNameInput.equals("") || createTourStatusInput.equals("") || createTourPlayersInput.equals(""))
			return 0;
		else
		{
			Tournament tournament = new Tournament(0, createTourNameInput, createTourStatusInput);
			if(tourAdministration.anyRunningTournament()==true)
				return -1;
			else
			{
				if(tourAdministration.insert(tournament, createTourPlayersInput)==0)
					return 0;
				return 1;
			}
		}
	}
	
	public boolean deleteTournament(String deleteTourIDInput)
	{
		TournamentDAOImplementation tourAdministration = new TournamentDAOImplementation();
		if(tourAdministration.findById(Integer.parseInt(deleteTourIDInput)) != null)
			tourAdministration.delete(Integer.parseInt(deleteTourIDInput));
		else
			return false;
		return true;
	}

	public boolean updateTournament(String updateTourIDInput, String updateTourNameInput, String updateTourStatusInput)
	{
		TournamentDAOImplementation tourAdministration = new TournamentDAOImplementation();
		if(tourAdministration.findById(Integer.parseInt(updateTourIDInput)) != null)
			tourAdministration.update(Integer.parseInt(updateTourIDInput), updateTourNameInput, updateTourStatusInput);
		else
			return false;
		return true;
	}
	
	public boolean createAccount(String registerMailInput, String registerPassInput, String registerNameInput)
	{
    	PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
    	if(registerMailInput.equals("") || registerPassInput.equals("") || registerNameInput.equals(""))
    		return false;
    	else
    	{
    		Player player=new Player(0, registerMailInput, registerPassInput, registerNameInput);
    		playerAdministration.insert(player);
    		return true;
    	}
	}
	
	public boolean deleteAccount(String deleteAccIDInput)
	{
		PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
		if(playerAdministration.findById(Integer.parseInt(deleteAccIDInput)) != null)
			playerAdministration.delete(Integer.parseInt(deleteAccIDInput));
		else
			return false;
		return true;
	}
	
	public boolean updateAccount(String updateAccIDInput, String updateAccMailInput, String updateAccPasswordInput, String updateAccNameInput)
	{
		PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
		if(playerAdministration.findById(Integer.parseInt(updateAccIDInput)) != null)
			playerAdministration.update(Integer.parseInt(updateAccIDInput), updateAccMailInput, updateAccPasswordInput, updateAccNameInput);
		else
			return false;
		return true;
	}
	
	public int loginPlayer(String mail, String password)
	{
		PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
		Player player;
		if(playerAdministration.findByMail(mail)!=null)
			player = playerAdministration.findByMail(mail);
		else
			return 0;
		
		if(player!=null & password.equals(new String(Base64.getDecoder().decode(player.getDecodedPassword().getBytes()))))
		{
			return player.getId();
		}
		return 0;
	}
	
	public boolean loginAdmin(String mail, String password)
	{
		AdminDAOImplementation adminAdministration = new AdminDAOImplementation();
		Admin admin;
		if(adminAdministration.findByMail(mail)!=null)
			admin = adminAdministration.findByMail(mail);
		else
			return false;
		
		if(admin!=null & password.equals(admin.getDecodedPassword()))
			return true;
		return false;
	}
	
	public void deleteGamesByMatchId(int matchID)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		gameAdministration.deleteGamesByMatchId(matchID);
	}
	
	public int findGameID(int loginPlayerID)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		return gameAdministration.findGameIdByPlayerId(loginPlayerID);
	}
	
	public int testGame(int gameID)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		if(gameAdministration.testGame(gameID)==1)
			return 1;
		if(gameAdministration.testGame(gameID)==2)
			return 2;
		return 0;
	}
	
	public int updateScore(int gameID, boolean which)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		if(which==false)
			return gameAdministration.updateScore(gameID, false);
		else
			return gameAdministration.updateScore(gameID, true);
		
	}
	
	public int findMatchID(int playerID)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		return matchAdministration.findMatchByPlayerId(playerID);
	}
	
	public int testMatch(int matchID)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		if(matchAdministration.testMatch(matchID)==2)
			return 2;
		if(matchAdministration.testMatch(matchID)==1)
			return 1;
		return 0;
	}
	
	public int whichPlayer(int id)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		if(matchAdministration.whichPlayer(id)==1)
			return 1;
		else
			return 2;
	}
	
	public int findGameScore1(int id)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		return gameAdministration.findScore1(id);
	}
	public int findGameScore2(int id)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		return gameAdministration.findScore2(id);
	}
	
	public int findMatchScore1(int id)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		return matchAdministration.findScore1(id);
	}
	public int findMatchScore2(int id)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		return matchAdministration.findScore2(id);
	}
	
	public void updateWinner(int id, int winner)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		matchAdministration.update(id, winner);
	}
	
	public int findWinner(int id, int player)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		return matchAdministration.findWinner(id, player);
	}
	
	public void semiFinals(int tournamentid)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		if(matchAdministration.test4Matches(tournamentid))
		{
			ArrayList<Integer> winners = new ArrayList<Integer>();
			winners = matchAdministration.getSemiFinalsWinners(tournamentid);
			Match match1 = new Match(0, tournamentid, winners.get(0), winners.get(1));
			Match match2 = new Match(0, tournamentid, winners.get(2), winners.get(3));
			
			matchAdministration.deleteMatches(winners.get(0));
			matchAdministration.deleteMatches(winners.get(1));
			matchAdministration.deleteMatches(winners.get(2));
			matchAdministration.deleteMatches(winners.get(3));
			
			int matchid1 = matchAdministration.insert(match1);
			int matchid2 = matchAdministration.insert(match2);
			
			
			GameDAOImplementation gameAdministration = new GameDAOImplementation();
			Game game1 = new Game(0, matchid1, 0, 0);
			Game game2 = new Game(0, matchid2, 0, 0);
			for(int i=0;i<5;i++)
				gameAdministration.insert(game1);
			for(int i=0;i<5;i++)
				gameAdministration.insert(game2);
				
		}
			
	}
	public void finals(int tournamentid)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		if(matchAdministration.test6Matches(tournamentid))
		{
			ArrayList<Integer> winners = new ArrayList<Integer>();
			winners = matchAdministration.getFinalsWinners(tournamentid);
			Match match = new Match(0, tournamentid, winners.get(0), winners.get(1));
			
			matchAdministration.deleteMatches(winners.get(0));
			matchAdministration.deleteMatches(winners.get(1));
			int matchid = matchAdministration.insert(match);
			
			GameDAOImplementation gameAdministration = new GameDAOImplementation();
			Game game = new Game(0, matchid, 0, 0);
			for(int i=0;i<5;i++)
				gameAdministration.insert(game);
		}	
	}
	
	public int tournamentWinner(int tournamentid)
	{
		MatchDAOImplementation matchAdministration = new MatchDAOImplementation();
		if(matchAdministration.test7Matches(tournamentid))
		{
			int winner = 0;
			winner = matchAdministration.getFinalsWinner(tournamentid);
			matchAdministration.deleteMatches(winner);
			
			TournamentDAOImplementation tournamentAdministration = new TournamentDAOImplementation();
			tournamentAdministration.updateWinner(tournamentid, winner);
			return winner;
		}
		return 0;
	}
	
	public int findTournamentID(int gameID)
	{
		GameDAOImplementation gameAdministration = new GameDAOImplementation();
		return gameAdministration.findTournamentID(gameID);
	}

	public String getWinnerName(int winnerID) {
		PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
		return playerAdministration.findNameById(winnerID);
	}

}
