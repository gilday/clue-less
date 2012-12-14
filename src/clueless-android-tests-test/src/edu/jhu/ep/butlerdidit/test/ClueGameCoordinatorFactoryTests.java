package edu.jhu.ep.butlerdidit.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.google.gson.Gson;

import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinatorFactory;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.Room;
import edu.jhu.ep.butlerdidit.domain.json.ClueMatchState;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSParticipant;
import edu.jhu.ep.butlerdidit.service.api.GSPlayer;

public class ClueGameCoordinatorFactoryTests extends TestCase {
	
	public void testLoadMatchStateFromJson() {
		// GIVEN
		// a GSMatch with 2 players
		GSMatch match = new GSMatch();
		match.setId(1);
		match.setMaxPlayers(2);
		match.setMaxPlayers(6);
		match.setMessage("Player 1 just took their turn");
		match.setStatus("playing");
		match.setCurrentPlayer("player2@test.com");
		
		GSPlayer player1 = new GSPlayer();
		player1.setEmail("player1@test.com");
		player1.setId(1);
		GSParticipant participant1 = new GSParticipant();
		participant1.setGamePlayer(player1);
		GSPlayer player2 = new GSPlayer();
		player2.setEmail("player2@test.com");
		player2.setId(2);
		GSParticipant participant2 = new GSParticipant();
		participant2.setGamePlayer(player2);
		
		List<GSParticipant> participants = new ArrayList<GSParticipant>(2);
		participants.add(participant1);
		participants.add(participant2);
		match.setParticipants(participants);
		
		// whose raw match data is
		// a chunk of JSON of 2 players with locations and characters
		Gson gson = new Gson();
		String matchJson = 
				"{\"players\": [" +
				"{\"email\":\"player1@test.com\", \"character\":\"" + ClueCharacter.MsScarletID + "\", \"location\":\"" + Room.STUDY + "\"}," +
				"{\"email\":\"player2@test.com\", \"character\":\"" + ClueCharacter.ProfPlumID + "\", \"location\":\"" + Room.KITCHEN + "\"} " +
				"]}";
		ClueMatchState matchState = gson.fromJson(matchJson, ClueMatchState.class);
		match.setRawMatchData(gson.toJsonTree(matchState));
		
		
		// WHEN
		// load new MatchState with JSON
		ClueGameCoordinatorFactory factory = new ClueGameCoordinatorFactory(new GSLocalPlayerHolder());
		ClueGameCoordinator coordinator = factory.loadGameFromMatch(match);
		
		// THEN
		// MatchState initialized correctly
		assertEquals(2, coordinator.getPlayers().size());
		CluePlayer cluePlayer2 = coordinator.getCurrentPlayer();
		assertEquals("player2@test.com", cluePlayer2.getGamePlayer().getEmail());
		assertEquals(ClueCharacter.ProfPlum, cluePlayer2.getClueCharacter());
		assertEquals(Room.KITCHEN, cluePlayer2.getLocation());
	}

}
