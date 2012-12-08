package edu.jhu.ep.butlerdidit.domain;

/**
 * GamePlayer is like our user account. GamePlayers are in the scopeo of all games 
 * being played on Game Server so this player may be playing multiple 
 * matches of Clue or other games. Remember, this is modeled after Game Center
 */
public class GamePlayer {

	private int id;
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
