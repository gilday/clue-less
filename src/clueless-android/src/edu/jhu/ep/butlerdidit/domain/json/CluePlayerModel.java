package edu.jhu.ep.butlerdidit.domain.json;

import edu.jhu.ep.butlerdidit.domain.CluePlayer;

public class CluePlayerModel {
	String email;
	String location;
	String character;
	String[] hand;
	
	public CluePlayerModel() { }
	
	CluePlayerModel(CluePlayer entity) {
		email = entity.getGamePlayer().getEmail();
		location = entity.getLocation();
		character = entity.getClueCharacter().getName();
		hand = new String[entity.getHand().size()];
		for(int i = 0; i < hand.length; i++) {
			hand[i] = entity.getHand().get(i).getCard();
		}
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
	public String[] getHand() {
		return hand;
	}
}
