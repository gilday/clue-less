package edu.jhu.ep.butlerdidit.domain;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Match {
	@SerializedName("min_players")
	private int minPlayers;
	
	@SerializedName("max_players")
	private int maxPlayers;
	
	private String status;
	
	private String message;
	
	@SerializedName("match_data")
	private String rawMatchData;
	
	@SerializedName("updated_at")
	private Date updatedAt;

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

	public String getRawMatchData() {
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

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRawMatchData(String rawMatchData) {
		this.rawMatchData = rawMatchData;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
