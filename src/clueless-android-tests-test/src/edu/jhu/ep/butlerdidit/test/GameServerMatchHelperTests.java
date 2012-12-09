package edu.jhu.ep.butlerdidit.test;

import java.util.Calendar;

import junit.framework.TestCase;
import android.test.mock.MockContext;
import edu.jhu.ep.butlerdidit.LocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.GameServerMatchHelperImpl;
import edu.jhu.ep.butlerdidit.service.api.GameServerMatchListener;
import edu.jhu.ep.butlerdidit.service.api.Match;

public class GameServerMatchHelperTests extends TestCase {

	private GameServerMatchHelperImpl gsHelper;
	private GameServerAssertions assertions;
	private Match match;
	
	protected void setUp() throws Exception {
		super.setUp();
		
        LocalPlayerHolder lpHolder = new LocalPlayerHolder();
        lpHolder.setLocalPlayerEmail("player@test.com");
        
        gsHelper = new GameServerMatchHelperImpl(new MockContext());
        gsHelper.setCurrentMatchById(1);
        gsHelper.setLocalPlayerHolder(lpHolder);
        resetAssertions();
        
        match = new Match();
        match.setId(1);
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
        match.setRawMatchData("DATA-DATA-DATA");
        match.setUpdatedAt(cal.getTime());
        
		Match newMatch = new Match();
		newMatch.setId(1);
		newMatch.setRawMatchData("DATA-DATA-DATA");
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
		match.setRawMatchData("DATA-DATA-DATA");
		match.setUpdatedAt(cal.getTime());
		
		gsHelper.matchReceived(match);
		
		Match newMatch = new Match();
		newMatch.setId(1);
		newMatch.setRawMatchData("DATA-DATA-DATA");
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
		match.setRawMatchData("DATA-DATA-DATA");
		match.setUpdatedAt(cal.getTime());
		
		gsHelper.matchReceived(match);
		
		Match newMatch = new Match();
		newMatch.setId(1);
		newMatch.setCurrentPlayer("player2@test.com");
		newMatch.setRawMatchData("DATA-DATA-DATA");
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
	
	/**
	 * TODO I bet Java has a mock library that can do this for me without 
	 * making a new inner class for it
	 */
	class GameServerAssertions implements GameServerMatchListener {

		boolean enterGameCalled = false;
		boolean layoutMatchCalled = false;
		boolean takeTurnCalled = false;
		boolean receiveEndGameCalled = false;
		
		@Override
		public void enterNewGame(Match match) {
			enterGameCalled = true;
		}

		@Override
		public void layoutMatch(Match match) {
			layoutMatchCalled = true;
		}

		@Override
		public void takeTurn(Match match) {
			takeTurnCalled = true;
		}

		@Override
		public void receiveEndGame(Match match) {
			receiveEndGameCalled = true;
		}
		
	}

}
