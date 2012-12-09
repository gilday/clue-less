package edu.jhu.ep.butlerdidit.domain;

import edu.jhu.ep.butlerdidit.service.api.GSPlayer;


/**
 * A CluePlayer is used in the scope of a Clueless match. 
 * Every GamePlayer playing Clueless maps to a CluePlayer
 *
 */
public class CluePlayer {
	
	private GSPlayer gamePlayer;
	
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
}
