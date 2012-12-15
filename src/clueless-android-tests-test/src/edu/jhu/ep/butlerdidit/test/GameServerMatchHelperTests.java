package edu.jhu.ep.butlerdidit.test;

import java.util.Calendar;

import com.google.gson.Gson;

import junit.framework.TestCase;
import android.test.mock.MockContext;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.GSMatchHelperImpl;
import edu.jhu.ep.butlerdidit.service.api.GSMatchListener;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;

public class GameServerMatchHelperTests extends TestCase {

	private GSMatchHelperImpl gsHelper;
	private GameServerAssertions assertions;
	private GSMatch match;
	private Gson gson;
	private GSLocalPlayerHolder lpHolder;
	
	protected void setUp() throws Exception {
		super.setUp();
		
        lpHolder = new GSLocalPlayerHolder();
        lpHolder.setLocalPlayerEmail("player@test.com");
        
        gsHelper = new GSMatchHelperImpl(new MockContext());
        gsHelper.setCurrentMatchById(1);
        gsHelper.setLocalPlayerHolder(lpHolder);
        resetAssertions();
        
        match = new GSMatch();
        match.setId(1);
        
        gson = new Gson();
	}
	
	private void resetAssertions() {
        assertions = new GameServerAssertions();
        gsHelper.setGameServerMatchListener(assertions);
	}
	
	public void testLayoutMatch() {
		// GIVEN
		// a match has been updated and it's a new players turn
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player@test.com");
        match.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
        match.setUpdatedAt(cal.getTime());
        
		GSMatch newMatch = new GSMatch();
		newMatch.setId(1);
		newMatch.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		newMatch.setCurrentPlayer("player2@test.com");
        cal.add(Calendar.SECOND, 60);
        newMatch.setUpdatedAt(cal.getTime());
        
        gsHelper.matchReceived(match);
        
        resetAssertions();
        // WHEN
        // new match received
        gsHelper.matchReceived(newMatch);
        
        // THEN
        assertTrue(assertions.layoutMatchCalled);
	}
	
	public void testEnterNewGame() {
		// GIVEN
		// a new match with no data
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player@test.com");
		match.setRawMatchData(null);
		match.setUpdatedAt(cal.getTime());
		
		resetAssertions();
		// WHEN
		// new match received
		gsHelper.matchReceived(match);
		
		// THEN
		// enter new game
		assertTrue(assertions.enterGameCalled);
	}
	
	public void testTakeTurn() {
		// GIVEN
		// a match where it is some other players turn
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player2@test.com");
		match.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		match.setUpdatedAt(cal.getTime());
		
		gsHelper.matchReceived(match);
		
		GSMatch newMatch = new GSMatch();
		newMatch.setId(1);
		newMatch.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		newMatch.setCurrentPlayer("player@test.com");
		cal.add(Calendar.SECOND, 60);
		match.setUpdatedAt(cal.getTime());
		
		resetAssertions();
		// WHEN
		// receive new match and it is our turn
		gsHelper.matchReceived(newMatch);
		
		// THEN
		// take turn
		assertTrue(assertions.takeTurnCalled);
	}
	
	public void testReceiveEndGame() {
		// GIVEN
		// a match where we are playing
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player2@test.com");
		match.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		match.setUpdatedAt(cal.getTime());
		
		gsHelper.matchReceived(match);
		
		GSMatch newMatch = new GSMatch();
		newMatch.setId(1);
		newMatch.setCurrentPlayer("player2@test.com");
		newMatch.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		newMatch.setStatus("finished");
		cal.add(Calendar.SECOND, 60);
		newMatch.setUpdatedAt(cal.getTime());
		
		resetAssertions();
		// WHEN
		// receive match with status "finished"
		gsHelper.matchReceived(newMatch);
		
		// THEN
		// announce end game
		assertTrue(assertions.receiveEndGameCalled);
	}
	
	public void testReceiveDuplicateMatchNothingHappens() {
		// GIVEN
		// some match
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player@test.com");
		match.setUpdatedAt(cal.getTime());
		match.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		
		gsHelper.matchReceived(match);

		resetAssertions();
		// WHEN
		// receive the same match and nothing has changed
		gsHelper.matchReceived(match);
		
		// THEN
		// better not call any listeners because nothing happened
		assertFalse(assertions.enterGameCalled);
		assertFalse(assertions.layoutMatchCalled);
		assertFalse(assertions.receiveEndGameCalled);
		assertFalse(assertions.takeTurnCalled);
	}
	
	public void testTurnoverTurn() {
		// GIVEN
		// player@test.com is is playing and takes his/her turn
		Calendar cal = Calendar.getInstance();
		
		match.setCurrentPlayer("player@test.com");
		match.setUpdatedAt(cal.getTime());
		match.setRawMatchData(gson.toJsonTree("DATA-DATA-DATA"));
		
		// WHEN
		// player receives an existing match
		resetAssertions();
		gsHelper.matchReceived(match);
		
		// THEN
		assertTrue(assertions.takeTurnCalled);
	}
	
	/**
	 * TODO I bet Java has a mock library that can do this for me without 
	 * making a new inner class for it
	 */
	class GameServerAssertions implements GSMatchListener {

		boolean enterGameCalled = false;
		boolean layoutMatchCalled = false;
		boolean takeTurnCalled = false;
		boolean receiveEndGameCalled = false;
		
		@Override
		public void enterNewGame(GSMatch match) {
			enterGameCalled = true;
		}

		@Override
		public void layoutMatch(GSMatch match) {
			layoutMatchCalled = true;
		}

		@Override
		public void takeTurn(GSMatch match) {
			takeTurnCalled = true;
		}

		@Override
		public void receiveEndGame(GSMatch match) {
			receiveEndGameCalled = true;
		}
		
	}

}
