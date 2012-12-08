package edu.jhu.ep.butlerdidit.test;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.HallwaySpace;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.Room;

public class GameBoardTests extends TestCase {

	public void testCanCreateHallwayFromTwoRooms() {
		// GIVEN
		// two rooms - Study and Hall
		Room study = new Room(Room.STUDY);
		Room hall = new Room(Room.HALL);
		
		// WHEN
		// a new hallway space is created connecting the two
		HallwaySpace hallway = new HallwaySpace(study, hall);
		
		// THEN
		// The hallway's ID should be a concatenation of the joining rooms
		assertEquals("Study-Hall", hallway.getSpaceId());
		// AND
		// the rooms should be connected
		assertTrue(hallway.getNavigationTargets().contains(hall));
		assertTrue(hallway.getNavigationTargets().contains(study));
		assertTrue(study.getNavigationTargets().contains(hallway));
		assertTrue(hall.getNavigationTargets().contains(hallway));
	}
	
	public void testProfPlumMayMoveFromStartToHallway() {
		// GIVEN
		// a game board with Professor Plum at his start spot
		CluePlayer profPlum = new CluePlayer();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation(profPlum.getClueCharacter().getName());
		
		List<CluePlayer> players = new Vector<CluePlayer>();
		GameBoard gameBoard = new GameBoard(players);
		
		// WHEN
		// system asks board where Plum can go
		List<GameBoardSpace> possibleMoves = gameBoard.getPossibleMoves(profPlum);
		
		// THEN
		// board returns HallwaySpace between Study and Library
		assertEquals(1, possibleMoves.size());
		assertEquals("Library-Study", possibleMoves.get(0).getSpaceId());
	}
	
	public void testPlayerMayNotMoveToOccupiedHallway() {
		// GIVEN
		// a GameBoard with Professor Plum on his start spot
		// and some other player is on the hallway he must move to
		CluePlayer profPlum = new CluePlayer();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation(profPlum.getClueCharacter().getName());
		
		CluePlayer msScarlet = new CluePlayer();
		msScarlet.setClueCharacter(ClueCharacter.MsScarlet);
		msScarlet.setLocation("Library-Study");
		
		List<CluePlayer> players = new Vector<CluePlayer>();
		players.add(profPlum);
		players.add(msScarlet);
		
		GameBoard gameBoard = new GameBoard(players);
		
		// WHEN
		// system asks GameBoard where professor plum may move
		List<GameBoardSpace> possibleMoves = gameBoard.getPossibleMoves(profPlum);
		
		// THEN
		// GameBoard says there are no possible spaces
		assertEquals(0, possibleMoves.size());
	}
	
	public void testPlayerMayUseSecretPassage() {
		// GIVEN
		// a GameBoard where professor plum is in the study
		CluePlayer profPlum = new CluePlayer();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation(Room.STUDY);
		
		List<CluePlayer> players = new Vector<CluePlayer>();
		players.add(profPlum);
		GameBoard gameBoard = new GameBoard(players);
		
		// WHEN
		// system asks if professor plum may move to kitchen a la passage
		boolean mayMoveToConservatory = gameBoard.isPlayerAbleToMoveToSpace(profPlum, gameBoard.getSpaces().get(Room.KITCHEN));
		
		// THEN
		// GameBoard says yes
		assertTrue(mayMoveToConservatory);
	}
}
