package edu.jhu.ep.butlerdidit.domain;


/**
 * A CluePlayer is used in the scope of a Clueless match. 
 * Every GamePlayer playing Clueless maps to a CluePlayer
 *
 */
public class CluePlayer {
	
	private GamePlayer gamePlayer;
	
    public GamePlayer getGamePlayer() {
		return gamePlayer;
	}
	public void setGamePlayer(GamePlayer gamePlayer) {
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
