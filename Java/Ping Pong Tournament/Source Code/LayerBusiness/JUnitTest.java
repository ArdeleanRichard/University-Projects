package org.LayerBusiness;


import org.LayerDataAccess.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class JUnitTest extends TestCase
{

    public JUnitTest( String testName )
    {
        super( testName );
    }

    public void testLoginPlayer()
    {
    	Functions f = new Functions();
    	assertEquals(f.loginPlayer("r", "r"), 1);
    }
    public void testLoginAdmin()
    {
    	Functions f = new Functions();
    	assertEquals(f.loginAdmin("admin", "admin"), true);
    }
    
    public void testCreateUpdateDeleteAccount()
    {
    	PlayerDAOImplementation playerAdministration = new PlayerDAOImplementation();
    	Player newPlayer = new Player(0, "Test@test.com", "testword", "Test");
    	System.out.println(newPlayer);
    	
    	// if the new player is inserted it will return the ID of this new player, which should be the first available
    	int id=playerAdministration.insert(newPlayer);
    	Player foundPlayer = playerAdministration.findById(id);
    	System.out.println(foundPlayer);
 
    	int updated = playerAdministration.update(id, "Vasile", "Valoare", "Vali");
    	Player updatedPlayer = playerAdministration.findById(id);
    	System.out.println(updatedPlayer);
    	
    	int deleted = playerAdministration.delete(id);
    	
    	
    	assertEquals(updated, 1);
    	assertEquals(deleted, 1);
    }
    
    public void testCreateUpdateDeleteTournament()
    {
    	TournamentDAOImplementation tournamentAdministration = new TournamentDAOImplementation();
    	Tournament newTournament = new Tournament(0, "TestTournament", "Test");
    	System.out.println(newTournament);
    	
    	int id = tournamentAdministration.insert(newTournament, "1,2,3,4,5,6,7,8");
    	Tournament foundTournament= tournamentAdministration.findById(id);
    	System.out.println(foundTournament);
    	
    	int updated = tournamentAdministration.update(id, "TestingUpdate Tournament", "Updated");
    	Tournament updatedTournament= tournamentAdministration.findById(id);
    	System.out.println(updatedTournament);
    	
    	int deleted = tournamentAdministration.delete(id);
    	
    	assertEquals(updated, 1);
    	assertEquals(deleted, 1);
    }
    
    public void testScore1()
    {
    	GameDAOImplementation gameAdministration = new GameDAOImplementation();
    	//parameter is the ID of the game, ID 2 has score 3-4
    	//to say that the game has not ended I chose 0, for winner player 1 I chose 1 and for player 2, naturally 2
        
    	//assertEquals(gameAdministration.testGame(2), 0);
    }
    public void testScore2()
    {
    	GameDAOImplementation gameAdministration = new GameDAOImplementation();
    	//parameter is the ID of the game, ID 1 has score 21-19
    	//to say that the game has not ended I chose 0, for winner player 1 I chose 1 and for player 2, naturally 2
    	// for this the answer should be 1
        //assertNotSame(gameAdmin.testGame(1), 0);
        
    	//assertEquals(gameAdministration.testGame(1), 1);
    }
    
    
    public void testUpdateScore()
    {
    	Functions f = new Functions();
    	//assertEquals(f.updateScore(2, false), 5);
    }
    
    public void testTestMatch()
    {
    	Functions f = new Functions();
    	//assertEquals(f.testMatch(1), 1);
    }
    public void testTestGame()
    {
    	Functions f = new Functions();
    	//assertEquals(f.testGame(1), 1);
    }
}
