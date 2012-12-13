package edu.jhu.ep.butlerdidit.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import android.util.Log;

import com.google.gson.Gson;
import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.domain.json.ClueMatchState;
import edu.jhu.ep.butlerdidit.domain.json.CluePlayerModel;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSParticipant;
import edu.jhu.ep.butlerdidit.service.api.GSPlayer;

public class ClueGameCoordinatorFactory {
	
	private GSLocalPlayerHolder localPlayerHolder;
	
	@Inject
	public ClueGameCoordinatorFactory(GSLocalPlayerHolder localPlayerHolder) {
		this.localPlayerHolder = localPlayerHolder;
	}

	public ClueGameCoordinator newGameFromMatch(GSMatch gsMatch) {
		
		// Do some domain validation
		if(gsMatch.getParticipants().size() < ClueGameCoordinator.MIN_PLAYERS 
				|| gsMatch.getParticipants().size() > ClueGameCoordinator.MAX_PLAYERS) {
			throw new IllegalStateException(
					String.format("Clueless may only have between %d and %d players. This match has %d players", 
							ClueGameCoordinator.MIN_PLAYERS, 
							ClueGameCoordinator.MAX_PLAYERS, 
							gsMatch.getParticipants().size()));			
		}
		
		ClueGameCoordinator coordinator = new ClueGameCoordinator(localPlayerHolder);
		coordinator.setPlayers(createPlayers(gsMatch.getParticipants()));
		
		// HACK Piggy back on GameServer's ordering for turn ordering
		// therefore, first player must be current
		coordinator.setCurrentPlayer(coordinator.getPlayers().get(0));
		// assert this hack
		if(!coordinator.getCurrentPlayer().getGamePlayer().getEmail().equals(coordinator.getPlayers().get(0).getGamePlayer().getEmail()))
			throw new IllegalStateException("First player in the order must be the current player for a new game");
		
		// This is a new game so assign Characters to players
		assignCharactersToPlayers(coordinator);
		
		// Initialize players' start spaces
		for(CluePlayer player : coordinator.getPlayers()) {
			player.setLocation(player.getClueCharacter().getName());
		}
		
		// Make the game board with the players list
		coordinator.setGameBoard(new GameBoard(coordinator.getPlayers()));
		
		return coordinator;
	}
	
	public ClueGameCoordinator loadGameFromMatch(GSMatch gsMatch) {
		
		ClueGameCoordinator coordinator = new ClueGameCoordinator(localPlayerHolder);
		coordinator.setPlayers(createPlayers(gsMatch.getParticipants()));
		
		// find current palyer
		for(CluePlayer cluePlayer : coordinator.getPlayers()) {
			if(cluePlayer.getGamePlayer().getEmail().equals(gsMatch.getCurrentPlayer()))
			{
				coordinator.setCurrentPlayer(cluePlayer);
				break;
			}
		}
		
		// Load player locations and Characters
		Gson gson = new Gson();
		ClueMatchState matchState = gson.fromJson(gsMatch.getRawMatchData(), ClueMatchState.class);
		for(CluePlayerModel model : matchState.getPlayerModels()) {
			for(CluePlayer cluePlayer : coordinator.getPlayers()) {
				if(cluePlayer.getGamePlayer().getEmail().equals(model.getEmail())) {
					cluePlayer.setLocation(model.getLocation());
					for(ClueCharacter character : ClueCharacter.All) {
						if(character.getName().equals(model.getCharacter())) {
							cluePlayer.setClueCharacter(character);
							break;
						}
					}
					break;
				}
			}
		}
		
		// Create game board
		coordinator.setGameBoard(new GameBoard(coordinator.getPlayers()));
		return coordinator;
	}
	
	public ClueGameCoordinator coordinatorForTesting() {
		ClueGameCoordinator coordinator = new ClueGameCoordinator(localPlayerHolder);
		List<ClueCard> testDeck = Deck.AllDeck;
				
		List<CluePlayer> cluePlayers = new Vector<CluePlayer>();
		CluePlayer player1 = new CluePlayer();
		GSPlayer gsPlayer1 = new GSPlayer();
		gsPlayer1.setEmail("player1@test.com");
		gsPlayer1.setId(1);
		player1.setGamePlayer(gsPlayer1);
		player1.setLocation(ClueCharacter.MsScarletID);
		player1.setClueCharacter(ClueCharacter.MsScarlett);
		cluePlayers.add(player1);
		
		CluePlayer player2 = new CluePlayer();
		GSPlayer gsPlayer2 = new GSPlayer();
		gsPlayer2.setEmail("player2@test.com");
		gsPlayer2.setId(2);
		player2.setGamePlayer(gsPlayer2);
		player2.setLocation(ClueCharacter.ColMustardID);
		player2.setClueCharacter(ClueCharacter.ColMustard);
		cluePlayers.add(player2);
	
		coordinator.setPlayers(cluePlayers);
		GameBoard board = new GameBoard(cluePlayers);
		coordinator.setGameBoard(board);
		coordinator.setCurrentPlayer(player1);
		
		assignHandToPlayers(testDeck, coordinator);
		
		return coordinator;
	}
	
	private List<CluePlayer> createPlayers(Collection<GSParticipant> participants) {
		List<CluePlayer> cluePlayers = new Vector<CluePlayer>();
		for(GSParticipant participant : participants) {
			CluePlayer cluePlayer = new CluePlayer();
			cluePlayer.setGamePlayer(participant.getGamePlayer());
			cluePlayers.add(cluePlayer);
		}
		return cluePlayers;
	}
	
	private void assignCharactersToPlayers(ClueGameCoordinator coordinator) {
		// Ms Scarlet is special, assign her first to the current player
		List<CluePlayer> players = coordinator.getPlayers();
		players.get(0).setClueCharacter(ClueCharacter.MsScarlett);
		
		for(int i = 1; i < players.size(); i++) {
			players.get(i).setClueCharacter(ClueCharacter.All.get(i));
		}
	}
	
	private void assignHandToPlayers(List<ClueCard> deck, ClueGameCoordinator coordinator)
	{		
		List<CluePlayer> players = coordinator.getPlayers();
		// Initialize empty hand for all players
		for(CluePlayer player : players) {
			player.setHand(new ArrayList<ClueCard>());
		}
		
		// LinkedList is a List and a Queue
		LinkedList<ClueCard> shuffledDeck = new LinkedList<ClueCard>(deck);
		// Use Java's awesome built in shuffling method
		Collections.shuffle((List<ClueCard>)shuffledDeck);
		
		while(shuffledDeck.peek() != null) {
			for(CluePlayer player : players) {
				ClueCard card = shuffledDeck.poll();
				if(card == null) { 
					// Spent all the cards in the queue
					// we're done now handing cards out now
					return;
				}
				player.getHand().add(card);
			}
		}
		//The following is test code to make sure the cards coming out are correct. 
		for(ClueCard cards : players.get(1).getHand())
			Log.d(ClueGameCoordinatorFactory.class.getName(), "players 1 card is " + cards.getCard());
	}
}
