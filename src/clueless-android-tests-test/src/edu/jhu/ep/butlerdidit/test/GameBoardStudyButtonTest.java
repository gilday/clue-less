package edu.jhu.ep.butlerdidit.test;

import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;

public class GameBoardStudyButtonTest extends TestCase 
{
	public void Study_Button_Test() 
	{
		// GIVEN
		// a GameBoard with Professor Plum on his start spot
		// and some other player is on the hallway he must move to
		CluePlayer profPlum = new CluePlayer();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation("Study-Library");
		
		List<CluePlayer> players = new Vector<CluePlayer>();
		players.add(profPlum);
		
		GameBoard gameBoard = new GameBoard(players);
		
		// WHEN
		// system asks GameBoard where professor plum may move
		List<GameBoardSpace> possibleMoves = gameBoard.getPossibleMoves(profPlum);
		
		// THEN
		// GameBoard says there are no possible spaces
		assertEquals(2, possibleMoves.size());
	}
}
