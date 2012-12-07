package edu.jhu.ep.butlerdidit.service;

import com.google.gson.Gson;

import edu.jhu.ep.butlerdidit.domain.Match;
import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;
import android.content.Context;
import android.content.Intent;

public class MatchDataBroadcastReceiver extends GameServerBroadcastReceiver<MatchDataListener> {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(GameServerConstants.ACTION_MATCH_RECEIVED)) {
			singleMatchReceived(intent.getStringExtra(GameServerConstants.PARM_JSON));
		}
	}
	
	private void singleMatchReceived(String json) {
		Gson gson = new Gson();
		Match match = gson.fromJson(json, Match.class);
		for(MatchDataListener listener : listeners) {
			listener.matchReceived(match);
		}
	}
}
