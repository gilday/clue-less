package edu.jhu.ep.butlerdidit.domain;

import java.util.List;

import edu.jhu.ep.butlerdidit.service.api.GSPlayer;
import edu.jhu.ep.butlerdidit.domain.Deck;

/**
 * A CluePlayer is used in the scope of a Clueless match. 
 * Every GamePlayer playing Clueless maps to a CluePlayer
 *
 */
public class CluePlayer {
	
	private GSPlayer gamePlayer;
	private List<Deck> Hand;
	
    public GSPlayer getGamePlayer() {
		return gamePlayer;
	}
	public void setGamePlayer(GSPlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}

	private String location;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    private ClueCharacter clueCharacter;

    public ClueCharacter getClueCharacter() {
        return clueCharacter;
    }

    public void setClueCharacter(ClueCharacter clueCharacter) {
        this.clueCharacter = clueCharacter;
    }
    
    public void setHand(List<Deck> Hand)
    //The cards that are set for the player.
    {
    	this.Hand = Hand;
    }
    
    public List<Deck> getHand()
    {
    	return Hand;
    }
}
