package edu.jhu.ep.butlerdidit.domain.json;

import edu.jhu.ep.butlerdidit.domain.CluePlayer;

public class CluePlayerModel {
	String email;
	String location;
	String character;
	
	public CluePlayerModel() { }
	
	CluePlayerModel(CluePlayer entity) {
		email = entity.getGamePlayer().getEmail();
		location = entity.getLocation();
		character = entity.getClueCharacter().getName();
	}
	
	public String getEmail() {
		return email;
	}
	public String getLocation() {
		return location;
	}
	public String getCharacter() {
		return character;
	}
}
