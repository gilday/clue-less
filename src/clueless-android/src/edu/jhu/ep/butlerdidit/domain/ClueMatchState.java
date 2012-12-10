package edu.jhu.ep.butlerdidit.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ClueMatchState {
	
	@SerializedName("players")
	List<CluePlayerModel> playerModels;
	
	// TODO Initialize from coordinator
	public ClueMatchState(ClueGameCoordinator coordinator) {
		
	}
	
	public ClueMatchState(String json) {
		
	}
	
	// TODO
	public String toJSON() {
		return toString();
	}
	
	class CluePlayerModel {
		String email;
		String location;
		String character;
	}
}
