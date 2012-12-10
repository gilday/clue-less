package edu.jhu.ep.butlerdidit.domain;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.domain.json.ClueMatchState;
import edu.jhu.ep.butlerdidit.domain.json.CluePlayerModel;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSParticipant;

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
		players.get(0).setClueCharacter(ClueCharacter.MsScarlet);
		
		for(int i = 1; i < players.size(); i++) {
			players.get(i).setClueCharacter(ClueCharacter.All.get(i));
		}
	}
}
