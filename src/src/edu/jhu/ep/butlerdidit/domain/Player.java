package edu.jhu.ep.butlerdidit.domain;

public class Player {

    private String location;
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    private String playerId;
    public String getPlayerId() {

        return playerId;
    }

    private ClueCharacter clueCharacter;

    public ClueCharacter getClueCharacter() {
        return clueCharacter;
    }

    public void setClueCharacter(ClueCharacter clueCharacter) {
        this.clueCharacter = clueCharacter;
    }
}
