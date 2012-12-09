package edu.jhu.ep.butlerdidit.test;

import java.util.List;
import java.util.Vector;

import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.Player;
import junit.framework.TestCase;

public class GameBoard_Study_Button_Test extends TestCase 
{
	public void Study_Button_Test() 
	{
		// GIVEN
		// a GameBoard with Professor Plum on his start spot
		// and some other player is on the hallway he must move to
		Player profPlum = new Player();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation("Study-Library");
		
		List<Player> players = new Vector<Player>();
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
