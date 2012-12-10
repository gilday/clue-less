package edu.jhu.ep.butlerdidit.domain.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;

public class ClueMatchState {
	
	@SerializedName("players")
	List<CluePlayerModel> playerModels;
	
	// TODO Initialize from coordinator
	public ClueMatchState(ClueGameCoordinator coordinator) {
		
	}
	
	// TODO
	public String toJSON() {
		return toString();
	}
	
	public List<CluePlayerModel> getPlayerModels() {
		return playerModels;
	}
}
