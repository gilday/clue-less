package edu.jhu.ep.butlerdidit.domain;

import com.google.gson.annotations.SerializedName;

public class GameParticipant {
	
	@SerializedName("player")
	private GamePlayer gamePlayer;

	public GamePlayer getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(GamePlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}
}
