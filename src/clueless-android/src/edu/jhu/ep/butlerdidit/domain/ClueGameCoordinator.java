package edu.jhu.ep.butlerdidit.domain;

import java.util.List;

import android.util.Log;

import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;

public class ClueGameCoordinator {
	
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 6;
	
	ClueGameCoordinator(GSLocalPlayerHolder localPlayerHolder) { 
		this.localPlayerHolder = localPlayerHolder;
	}

	private CluePlayer currentPlayer;
	private List<CluePlayer> players;
	private GameBoard gameBoard;
	private GSLocalPlayerHolder localPlayerHolder;
	
	public CluePlayer getLocalPlayer() {
		for(CluePlayer player : players) {
			if(player.getGamePlayer().getEmail().equals(localPlayerHolder.getLocalPlayerEmail()))
				return player;
		}
		return null;
	}
	
	public boolean isLocalPlayersTurn() {
		return (localPlayerHolder.getLocalPlayerEmail().equals(getCurrentPlayer().getGamePlayer().getEmail()));
	}
	
	public void endTurn() {
		// advance to next player
		int currentPlayerIndex = players.indexOf(currentPlayer);
		int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
		Log.d(ClueGameCoordinator.class.getName(), String.format("Player %s ended their turn. now %s's turn", currentPlayer.getGamePlayer().getEmail(), players.get(nextPlayerIndex).getGamePlayer().getEmail()));
		currentPlayer = players.get(nextPlayerIndex);
	}

	public CluePlayer getCurrentPlayer() {
		return currentPlayer;
	}

	void setCurrentPlayer(CluePlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<CluePlayer> getPlayers() {
		return players;
	}

	void setPlayers(List<CluePlayer> players) {
		this.players = players;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
}
