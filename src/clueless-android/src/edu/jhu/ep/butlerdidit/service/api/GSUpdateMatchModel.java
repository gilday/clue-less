package edu.jhu.ep.butlerdidit.service.api;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;


/**
 * Just used to serialize only the fields we want with GSON
 * The fields we want serialized when updating a match is 
 * different than the fields we want deserialized when 
 * receiving a match
 */
public class GSUpdateMatchModel {
	
	private int id;
	private String status;
	private String message;
	@SerializedName("match_data")
	private JsonElement rawMatchData;
	@SerializedName("current_player")
	private String currentPlayer;
	
	public GSUpdateMatchModel() { }

	public GSUpdateMatchModel(GSMatch entity) {
		id = entity.getId();
		status = entity.getStatus();
		message = entity.getMessage();
		rawMatchData = entity.getRawMatchData();
		currentPlayer = entity.getCurrentPlayer();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JsonElement getMatchData() {
		return rawMatchData;
	}

	public void setMatchData(JsonElement matchData) {
		this.rawMatchData = matchData;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
