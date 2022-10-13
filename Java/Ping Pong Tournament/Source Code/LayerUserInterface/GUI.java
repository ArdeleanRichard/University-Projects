package org.LayerUserInterface;

import org.LayerBusiness.*;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class GUI extends Application //implements EventHandler<ActionEvent>
{
	Stage window;
	Scene sceneMain, sceneUserAfterLogin, sceneAdminAfterLogin; 
	Scene sceneCreateTournament, sceneDeleteTournament, sceneUpdateTournament;
	Scene sceneDeleteAccount, sceneUpdateAccount;
	Scene sceneTournament, sceneMatch, sceneCreateAcc;
	Button viewTournamentsBtn, viewMatchesBtn, updateScore1, updateScore2;
	Button exitBtn;
	
	int loginPlayerID, loginAdminID;
	int gameID, tournamentID;
	
    public static void main( String[] args )
    {
        launch(args); 
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		window=primaryStage;
		window.setOnCloseRequest(e-> 
		{
			e.consume(); //we take over, dont let java close, we handle manually
			closeProgram();
		});
		
		
		exitBtn=new Button("Exit");
		exitBtn.setOnAction(e->closeProgram());
		
		Label whichPlayer = new Label("");
		GridPane.setConstraints(whichPlayer, 0, 2);
		Label matchScore = new Label("");
		Label gameScore = new Label();
		
	//TABLE
		Functions f = new Functions();

		


		
	//MAIN WINDOW	
		//GridPane with 10px padding around edge
        GridPane gridMain = new GridPane();
        gridMain.setPadding(new Insets(10, 10, 10, 10));
        gridMain.setVgap(8);
        gridMain.setHgap(10);
        
        //LOGIN
        //Name Label - constrains use (child, column, row)
        Label mailLabel = new Label("Mail:");
        GridPane.setConstraints(mailLabel, 0, 0);

        //Name Input
        TextField mailInput = new TextField();
        mailInput.setPromptText("Enter mail");
        GridPane.setConstraints(mailInput, 1, 0);
        mailInput.setText("r");
        
        //Password Label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, 0, 1);

        //Password Input
        PasswordField passInput = new PasswordField();
        GridPane.setConstraints(passInput, 1, 1);
        passInput.setPromptText("Enter password");
        passInput.setText("r");
        

        //Login
        Button loginButton = new Button("Log In");
        GridPane.setConstraints(loginButton, 1, 2);
        loginButton.setOnAction(e-> {
        	if(mailInput.getText().equals("") || passInput.getText().equals(""))
        		AlertBox.display("No input", "You forgot to write your mail/password");
        	else
	        	if((loginPlayerID = f.loginPlayer(mailInput.getText(), passInput.getText()))>0)
	        	{
	        		sceneUserAfterLogin.getStylesheets().add("Style.css");
	        		window.setScene(sceneUserAfterLogin);
	        		
	        		whichPlayer.setText("Your ID is "+loginPlayerID+"\nYou are Player"+f.whichPlayer(loginPlayerID));
	        		
					int matchID = f.findMatchID(loginPlayerID);
					int matchWinner = f.testMatch(matchID);
					int matchWinnerID = f.findWinner(matchID, matchWinner);
	        		
					if(matchID != 0)
					{
						int matchScore1 = f.findMatchScore1(matchID);
						int matchScore2 = f.findMatchScore2(matchID);
						matchScore.setText("Match: " + matchScore1 + "-"+matchScore2);
					}
					if(matchWinner!=0)
					{
						updateScore1.setDisable(true);
						updateScore2.setDisable(true);
					}
					else
					{
						updateScore1.setDisable(false);
						updateScore2.setDisable(false);
					}
					
	        		if(f.findGameID(loginPlayerID)==0)
	        		{
	    				AlertBox.display("No matches", "You have no on-going matches or games");
	    				updateScore1.setDisable(true);
						updateScore2.setDisable(true);
	    				gameID=0;
	        		}
	        		else
	        		{
	        			gameID=f.findGameID(loginPlayerID);
	        			tournamentID = f.findTournamentID(gameID);
						int gameScore1 = f.findGameScore1(gameID);
						int gameScore2 = f.findGameScore2(gameID);	
				        gameScore.setText("Game: " + gameScore1 + "-"+gameScore2);
	        		}
	        	}
	        	else
	        		if(f.loginAdmin(mailInput.getText(), passInput.getText()))
	        		{
	        			sceneAdminAfterLogin.getStylesheets().add("Style.css");
	        			window.setScene(sceneAdminAfterLogin);
	        		}
	        		else
	        			AlertBox.display("Wrong credentials", "Your account/password are wrong.");
        });
        

        
        //Add everything to grid
        gridMain.getChildren().addAll(mailLabel, mailInput, passLabel, passInput, loginButton);

        
    

        
    //ADMIN WINDOW
        GridPane gridAdmin = new GridPane();
        gridAdmin.setPadding(new Insets(10, 10, 10, 10));
        gridAdmin.setVgap(8);
        gridAdmin.setHgap(10);
        
		Button backLoginButton = new Button("Back");
		GridPane.setConstraints(backLoginButton, 0, 6);
		backLoginButton.setOnAction(e-> { sceneMain.getStylesheets().add("Style.css"); window.setScene(sceneMain); });

		Button createTournamentButton = new Button("Create Tournament");
		GridPane.setConstraints(createTournamentButton, 0, 0);
		createTournamentButton.setOnAction(e-> { sceneCreateTournament.getStylesheets().add("Style.css"); window.setScene(sceneCreateTournament); });
		
	//CREATE TOURNAMENT
        GridPane gridCreateTour = new GridPane();
        gridCreateTour.setPadding(new Insets(10, 10, 10, 10));
        gridCreateTour.setVgap(8);
        gridCreateTour.setHgap(10);
        
        //Name Label - constrains use (child, column, row)
        Label createTourNameLabel = new Label("Name:");
        GridPane.setConstraints(createTourNameLabel, 0, 0);

        //Name Input
        TextField createTourNameInput = new TextField();
        createTourNameInput.setPromptText("Enter name");
        GridPane.setConstraints(createTourNameInput, 1, 0);
        
        //Status Label
        Label createTourStatusLabel = new Label("Status:");
        GridPane.setConstraints(createTourStatusLabel, 0, 1);
        
        //Status Input
        TextField createTourStatusInput = new TextField();
        createTourStatusInput.setPromptText("Enter status");
        GridPane.setConstraints(createTourStatusInput, 1, 1);
        
        //Players Label
        Label createTourPlayersLabel = new Label("The 8 players:");
        GridPane.setConstraints(createTourPlayersLabel, 0, 2);
        
        //Players Input
        TextField createTourPlayersInput = new TextField();
        createTourPlayersInput.setPromptText("Enter 8 playersID separated by comma");
        GridPane.setConstraints(createTourPlayersInput, 1, 2);
        
		Button backCreateTourButton = new Button("Back");
		GridPane.setConstraints(backCreateTourButton, 1, 3);
		backCreateTourButton.setOnAction(e-> { sceneAdminAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin);});
		
		Button createTourButton = new Button("Create");
		GridPane.setConstraints(createTourButton, 0, 3);
		createTourButton.setOnAction(e-> {
			int res=2;
			if((res=f.createTournament(createTourNameInput.getText(), createTourStatusInput.getText(), createTourPlayersInput.getText()))==1)
				AlertBox.display("Creation", "The tournament has been created");
			else
				if(res==0)
					AlertBox.display("No input", "You forgot to enter name/status/players or wrong players format");
				else
					if(res==-1)
						AlertBox.display("Running Tournament", "You have to wait for the current tournament to finish");
		});
        
        gridCreateTour.getChildren().addAll(createTourNameLabel, createTourNameInput, createTourPlayersLabel, createTourPlayersInput, createTourStatusLabel, createTourStatusInput, backCreateTourButton, createTourButton);
        
        sceneCreateTournament = new Scene(gridCreateTour, 300, 300);
        
		
        
        Button deleteTournamentButton = new Button("Delete Tournament");
		GridPane.setConstraints(deleteTournamentButton, 0, 1);
		deleteTournamentButton.setOnAction(e-> { sceneDeleteTournament.getStylesheets().add("Style.css"); window.setScene(sceneDeleteTournament); }); 
    //DELETE TOURNAMENT
        GridPane gridDeleteTour = new GridPane();
        gridDeleteTour.setPadding(new Insets(10, 10, 10, 10));
        gridDeleteTour.setVgap(8);
        gridDeleteTour.setHgap(10);
        
        Label deleteTourIDLabel = new Label("ID:");
        GridPane.setConstraints(deleteTourIDLabel, 0, 0);

        TextField deleteTourIDInput = new TextField();
        deleteTourIDInput.setPromptText("Enter ID");
        GridPane.setConstraints(deleteTourIDInput, 1, 0);
        
		Button backDeleteTourButton = new Button("Back");
		GridPane.setConstraints(backDeleteTourButton, 1, 3);
		backDeleteTourButton.setOnAction(e-> { sceneAdminAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin);});
        
		Button deleteTourButton = new Button("Delete");
		GridPane.setConstraints(deleteTourButton, 1, 2);
		deleteTourButton.setOnAction(e-> {
			if(f.deleteTournament(deleteTourIDInput.getText())!=true)
				AlertBox.display("Error", "That ID does not exist");
		});
		
		gridDeleteTour.getChildren().addAll(deleteTourIDLabel, deleteTourIDInput, deleteTourButton, backDeleteTourButton);
        
        sceneDeleteTournament = new Scene(gridDeleteTour, 300, 200);
		
        
        
        
		Button updateTournamentButton = new Button("Update Tournament");
		GridPane.setConstraints(updateTournamentButton, 0, 2);
		updateTournamentButton.setOnAction(e-> { sceneUpdateTournament.getStylesheets().add("Style.css"); window.setScene(sceneUpdateTournament); });
    //UPDATE TOURNAMENT
		GridPane gridUpdateTour = new GridPane();
		gridUpdateTour.setPadding(new Insets(10, 10, 10, 10));
		gridUpdateTour.setVgap(8);
		gridUpdateTour.setHgap(10);
        
        Label updateTourIDLabel = new Label("ID:");
        GridPane.setConstraints(updateTourIDLabel, 0, 0);

        TextField updateTourIDInput = new TextField();
        updateTourIDInput.setPromptText("Enter ID");
        GridPane.setConstraints(updateTourIDInput, 1, 0);
		
        Label updateTourNameLabel = new Label("Name:");
        GridPane.setConstraints(updateTourNameLabel, 0, 1);

        TextField updateTourNameInput = new TextField();
        updateTourNameInput.setPromptText("Enter name");
        GridPane.setConstraints(updateTourNameInput, 1, 1);
        
        Label updateTourStatusLabel = new Label("Status:");
        GridPane.setConstraints(updateTourStatusLabel, 0, 2);

        TextField updateTourStatusInput = new TextField();
        updateTourStatusInput.setPromptText("Enter status");
        GridPane.setConstraints(updateTourStatusInput, 1, 2);
        
		Button backUpdateTourButton = new Button("Back");
		GridPane.setConstraints(backUpdateTourButton, 1, 4);
		backUpdateTourButton.setOnAction(e-> { sceneAdminAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin); });
        
		Button updateTourButton = new Button("Update");
		GridPane.setConstraints(updateTourButton, 1, 3);
		updateTourButton.setOnAction(e-> {
			if(f.updateTournament(updateTourIDInput.getText(), updateTourNameInput.getText(), updateTourStatusInput.getText())!=true)
				AlertBox.display("Error", "That ID does not exist");
		});
		gridUpdateTour.getChildren().addAll(updateTourIDLabel, updateTourIDInput, updateTourNameLabel, updateTourNameInput, updateTourStatusLabel, updateTourStatusInput, updateTourButton, backUpdateTourButton);
        
        sceneUpdateTournament = new Scene(gridUpdateTour, 300, 200);
		
        
        Button createAccountButton = new Button("Create Account");
        GridPane.setConstraints(createAccountButton, 0, 3);
        createAccountButton.setOnAction(e -> {sceneCreateAcc.getStylesheets().add("Style.css"); window.setScene(sceneCreateAcc);});
    //CREATE ACCOUNT
        GridPane gridCreateAcc = new GridPane();
        gridCreateAcc.setPadding(new Insets(10, 10, 10, 10));
        gridCreateAcc.setVgap(8);
        gridCreateAcc.setHgap(10);
        
        Label registerMailLabel = new Label("Mail:");
        GridPane.setConstraints(registerMailLabel, 0, 0);

        TextField registerMailInput = new TextField();
        mailInput.setPromptText("Enter mail");
        GridPane.setConstraints(registerMailInput, 1, 0);
        
        Label registerPassLabel = new Label("Password:");
        GridPane.setConstraints(registerPassLabel, 0, 1);

        TextField registerPassInput = new TextField();
        GridPane.setConstraints(registerPassInput, 1, 1);
        passInput.setPromptText("Enter password");
        
        Label registerNameLabel = new Label("Name:");
        GridPane.setConstraints(registerNameLabel, 0, 2);

        TextField registerNameInput = new TextField();
        GridPane.setConstraints(registerNameInput, 1, 2);
        passInput.setPromptText("Enter password");
        
		Button backRegisterButton = new Button("Back");
		GridPane.setConstraints(backRegisterButton, 1, 4);
		backRegisterButton.setOnAction(e-> { sceneMain.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin); });
        
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 3);
        registerButton.setOnAction(e -> {
        	if(f.createAccount(registerMailInput.getText(), registerPassInput.getText(), registerNameInput.getText()))
        		AlertBox.display("Registration", "Your account has been created");
        	else
        		AlertBox.display("No input", "You forgot to enter mail/password/name");
        });
        
        
        gridCreateAcc.getChildren().addAll(registerMailLabel, registerMailInput, registerPassLabel, registerPassInput, registerNameLabel, registerNameInput, registerButton, backRegisterButton);
        
        sceneCreateAcc = new Scene(gridCreateAcc, 300, 200);
        
        Button deleteAccountButton = new Button("Delete Account");
		GridPane.setConstraints(deleteAccountButton, 0, 4);
		deleteAccountButton.setOnAction(e-> { sceneDeleteAccount.getStylesheets().add("Style.css"); window.setScene(sceneDeleteAccount); }); 
    //DELETE ACCOUNT
        GridPane gridDeleteAcc = new GridPane();
        gridDeleteAcc.setPadding(new Insets(10, 10, 10, 10));
        gridDeleteAcc.setVgap(8);
        gridDeleteAcc.setHgap(10);
        
        Label deleteAccIDLabel = new Label("ID:");
        GridPane.setConstraints(deleteAccIDLabel, 0, 0);

        TextField deleteAccIDInput = new TextField();
        deleteAccIDInput.setPromptText("Enter ID");
        GridPane.setConstraints(deleteAccIDInput, 1, 0);
        
		Button backDeleteAccButton = new Button("Back");
		GridPane.setConstraints(backDeleteAccButton, 1, 3);
		backDeleteAccButton.setOnAction(e-> { sceneAdminAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin); });
        
		Button deleteAccButton = new Button("Delete");
		GridPane.setConstraints(deleteAccButton, 1, 2);
		deleteAccButton.setOnAction(e-> {
			if(f.deleteAccount(deleteAccIDInput.getText())!=true)
				AlertBox.display("Error", "That ID does not exist");
		});
		
		gridDeleteAcc.getChildren().addAll(deleteAccIDLabel, deleteAccIDInput, deleteAccButton, backDeleteAccButton);
        
        sceneDeleteAccount= new Scene(gridDeleteAcc, 300, 200);
		
        
        
        
        
		Button updateAccountButton = new Button("Update Account");
		GridPane.setConstraints(updateAccountButton, 0, 5);
		updateAccountButton.setOnAction(e->{ sceneUpdateAccount.getStylesheets().add("Style.css");  window.setScene(sceneUpdateAccount); });
    //UPDATE ACCOUNT
		GridPane gridUpdateAcc = new GridPane();
		gridUpdateAcc.setPadding(new Insets(10, 10, 10, 10));
		gridUpdateAcc.setVgap(8);
		gridUpdateAcc.setHgap(10);
        
        Label updateAccIDLabel = new Label("ID:");
        GridPane.setConstraints(updateAccIDLabel, 0, 0);

        TextField updateAccIDInput = new TextField();
        updateAccIDInput.setPromptText("Enter ID");
        GridPane.setConstraints(updateAccIDInput, 1, 0);
	
        Label updateAccMailLabel = new Label("Mail:");
        GridPane.setConstraints(updateAccMailLabel, 0, 1);

        TextField updateAccMailInput = new TextField();
        updateAccMailInput.setPromptText("Enter Mail");
        GridPane.setConstraints(updateAccMailInput, 1, 1);
        
        Label updateAccPasswordLabel = new Label("Password:");
        GridPane.setConstraints(updateAccPasswordLabel, 0, 2);

        TextField updateAccPasswordInput = new TextField();
        updateAccPasswordInput.setPromptText("Enter password");
        GridPane.setConstraints(updateAccPasswordInput, 1, 2);
        
        Label updateAccNameLabel = new Label("Name:");
        GridPane.setConstraints(updateAccNameLabel, 0, 3);

        TextField updateAccNameInput = new TextField();
        updateAccNameInput.setPromptText("Enter name");
        GridPane.setConstraints(updateAccNameInput, 1, 3);
        
		Button backUpdateAccButton = new Button("Back");
		GridPane.setConstraints(backUpdateAccButton, 1, 5);
		backUpdateAccButton.setOnAction(e-> { sceneAdminAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneAdminAfterLogin); });
        
		Button updateAccButton = new Button("Update");
		GridPane.setConstraints(updateAccButton, 1, 4);
		updateAccButton.setOnAction(e-> {
			if(f.updateAccount(updateAccIDInput.getText(), updateAccMailInput.getText(), updateAccPasswordInput.getText(), updateAccNameInput.getText())!=true)
				AlertBox.display("Error", "That ID does not exist");
		});
		gridUpdateAcc.getChildren().addAll(updateAccIDLabel, updateAccIDInput, updateAccNameLabel, updateAccNameInput, updateAccMailLabel, updateAccMailInput, updateAccPasswordLabel, updateAccPasswordInput, updateAccButton, backUpdateAccButton);
        
        sceneUpdateAccount = new Scene(gridUpdateAcc, 300, 250);
        
		
		gridAdmin.getChildren().addAll(backLoginButton, createTournamentButton, deleteTournamentButton, updateTournamentButton, createAccountButton, deleteAccountButton, updateAccountButton);
		
        sceneAdminAfterLogin = new Scene(gridAdmin, 200, 300);
        
        
    //USER WINDOW
        GridPane gridNext = new GridPane();
        gridNext.setPadding(new Insets(10, 10, 10, 10));
        gridNext.setVgap(8);
        gridNext.setHgap(10);
        
        
        

        
        
		viewTournamentsBtn = new Button("View Tournaments");
		GridPane.setConstraints(viewTournamentsBtn, 0, 0);
		
		//TOURNAMENT SCENE
		Button tournamentBackButton = new Button("Back");
		tournamentBackButton.setOnAction(e-> { sceneUserAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneUserAfterLogin);});		
        BorderPane viewTournamentPane = new BorderPane();
        viewTournamentPane.setBottom(tournamentBackButton);
        
		sceneTournament = new Scene(viewTournamentPane, 470, 200);
		viewTournamentsBtn.setOnAction(e -> { sceneTournament.getStylesheets().add("Style.css"); viewTournamentPane.setCenter(f.createTournamentTable()); window.setScene(sceneTournament); });
		
		
		
		viewMatchesBtn = new Button("View Matches");
		GridPane.setConstraints(viewMatchesBtn, 0, 1);
		
		//MATCH SCENE
		Button matchBackButton = new Button("Back");
		matchBackButton.setOnAction(e-> { sceneUserAfterLogin.getStylesheets().add("Style.css"); window.setScene(sceneUserAfterLogin);});
		
        BorderPane viewMatchPane = new BorderPane();
        viewMatchPane.setBottom(matchBackButton);
        
		sceneMatch = new Scene(viewMatchPane, 470, 200);
		viewMatchesBtn.setOnAction(e -> { sceneMatch.getStylesheets().add("Style.css");  viewMatchPane.setCenter(f.createMatchTable()); window.setScene(sceneMatch); });
		
		
		
		updateScore1 = new Button("Update Score1");
		GridPane.setConstraints(updateScore1, 2, 0);
		updateScore1.setOnAction(e -> {
			f.updateScore(gameID, false);
			if(f.testGame(gameID)==1)
			{
				AlertBox.display("Winner1", "The first player has won the game.");
				gameID=f.findGameID(loginPlayerID);
				int matchID = f.findMatchID(loginPlayerID);
				int matchWinner = f.testMatch(matchID);
				int matchWinnerID = f.findWinner(matchID, matchWinner);
				if(matchWinner==1)
				{
					AlertBox.display("Winner1", "The first player has won the match.");
					f.updateWinner(matchID, matchWinnerID);
					f.deleteGamesByMatchId(matchID);
					f.semiFinals(tournamentID);
					f.finals(tournamentID);
					int winnerID;
					if((winnerID = f.tournamentWinner(tournamentID))!=0)
					{
						String winner = f.getWinnerName(winnerID);
						AlertBox.display("Congratulations", winner+" has won the tournament.");
					}
					updateScore1.setDisable(true);
					updateScore2.setDisable(true);
					if((gameID=f.findGameID(loginPlayerID))!=0)
					{
						updateScore1.setDisable(false);
						updateScore2.setDisable(false);
					}
				}
				
				int matchScore1 = f.findMatchScore1(matchID);
				int matchScore2 = f.findMatchScore2(matchID);	
		        matchScore.setText("Match: " + matchScore1 + "-"+matchScore2);
			}
			int gameScore1 = f.findGameScore1(gameID);
			int gameScore2 = f.findGameScore2(gameID);	
	        gameScore.setText("Game: " + gameScore1 + "-"+gameScore2);
		}
		);
		
		updateScore2 = new Button("Update Score2");
		GridPane.setConstraints(updateScore2, 2, 1);
		updateScore2.setOnAction(e -> {
			f.updateScore(gameID, true);
			if(f.testGame(gameID)==2)
			{
				AlertBox.display("Winner2", "The second player has won the game.");
				gameID=f.findGameID(loginPlayerID);
				int matchID = f.findMatchID(loginPlayerID);
				int matchWinner = f.testMatch(matchID);
				int matchWinnerID = f.findWinner(matchID, matchWinner);
				if(matchWinner==2)
				{
					AlertBox.display("Winner2", "The second player has won the match.");
					f.updateWinner(matchID, matchWinnerID);
					f.deleteGamesByMatchId(matchID);
					f.semiFinals(tournamentID);
					f.finals(tournamentID);
					int winnerID;
					if((winnerID = f.tournamentWinner(tournamentID))!=0)
					{
						String winner = f.getWinnerName(winnerID);
						AlertBox.display("Congratulations", winner+" has won the tournament.");
					}
					updateScore1.setDisable(true);
					updateScore2.setDisable(true);
					if((gameID=f.findGameID(loginPlayerID))!=0)
					{
						updateScore1.setDisable(false);
						updateScore2.setDisable(false);
					}
				}	
				int matchScore1 = f.findMatchScore1(matchID);
				int matchScore2 = f.findMatchScore2(matchID);	
		        matchScore.setText("Match: " + matchScore1 + "-"+matchScore2);

			}
			
			int gameScore1 = f.findGameScore1(gameID);
			int gameScore2 = f.findGameScore2(gameID);	
	        
	        gameScore.setText("Game: " + gameScore1 + "-"+gameScore2);

		}
		);

		GridPane.setConstraints(matchScore, 2, 2);
        GridPane.setConstraints(gameScore, 2, 3);
        
		
		Button backButton = new Button("Back");
		GridPane.setConstraints(backButton, 2, 4);
		backButton.setOnAction(e-> { sceneMain.getStylesheets().add("Style.css"); window.setScene(sceneMain); });
		
		Button testButton = new Button("Test");
		GridPane.setConstraints(testButton, 2, 5);
		testButton.setOnAction(e-> {
			
		});
		
		gridNext.getChildren().addAll(viewTournamentsBtn, viewMatchesBtn,updateScore1,updateScore2, backButton, matchScore, gameScore, whichPlayer);

		sceneUserAfterLogin = new Scene(gridNext, 300, 250);
		
		sceneMain = new Scene(gridMain, 300, 150);
		sceneMain.getStylesheets().add("Style.css");
		window.setScene(sceneMain);
		window.setTitle("Assignment1");
		window.show();
	}

	void closeProgram()
	{
		Boolean answer = ConfirmBox.display("Exit", "Are you sure you want to close the window?");
		if(answer)
			window.close();
	}
	
	
}
