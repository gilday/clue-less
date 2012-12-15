package edu.jhu.ep.butlerdidit.service.api;

import java.util.Collection;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;



public class GSMatch {
	
	private int id;
	
	@SerializedName("min_players")
	private int minPlayers;
	
	@SerializedName("max_players")
	private int maxPlayers;
	
	private String status;
	
	private String message;
	
	@SerializedName("match_data")
	private JsonElement rawMatchData;
	
	@SerializedName("updated_at")
	private Date updatedAt;
	
	private Collection<GSParticipant> participants;
	
	@SerializedName("current_player")
	private String currentPlayer;

	public int getMinPlayers() {
		return minPlayers;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public JsonElement getRawMatchData() {
		return rawMatchData;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}

	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRawMatchData(JsonElement rawMatchData) {
		this.rawMatchData = rawMatchData;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Collection<GSParticipant> getParticipants() {
		return participants;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setParticipants(Collection<GSParticipant> players) {
		this.participants = players;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
