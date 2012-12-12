package edu.jhu.ep.butlerdidit.domain.json;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;

public class ClueMatchState {
	
	@SerializedName("players")
	List<CluePlayerModel> playerModels;
	
	public ClueMatchState(ClueGameCoordinator coordinator) {
		List<CluePlayerModel> playerModels = new ArrayList<CluePlayerModel>(coordinator.getPlayers().size());
		
		for(CluePlayer player : coordinator.getPlayers()) {
			CluePlayerModel model = new CluePlayerModel(player);
			playerModels.add(model);
		}
		this.playerModels = playerModels;
	}
	
	public String toJSON() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public List<CluePlayerModel> getPlayerModels() {
		return playerModels;
	}
}
