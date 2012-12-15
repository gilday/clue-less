package edu.jhu.ep.butlerdidit.service.api;

import com.google.gson.annotations.SerializedName;

public class GSParticipant {
	
	@SerializedName("player")
	private GSPlayer gamePlayer;

	public GSPlayer getGamePlayer() {
		return gamePlayer;
	}

	public void setGamePlayer(GSPlayer gamePlayer) {
		this.gamePlayer = gamePlayer;
	}
}
