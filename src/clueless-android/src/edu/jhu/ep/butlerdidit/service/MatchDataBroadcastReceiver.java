package edu.jhu.ep.butlerdidit.service;

import com.google.gson.Gson;

import edu.jhu.ep.butlerdidit.service.api.GSConstants;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSMatchDataListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MatchDataBroadcastReceiver extends GSBroadcastReceiver<GSMatchDataListener> {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(GSConstants.BROADCAST_MATCHRECEIVED_SUCCESS)) {
			singleMatchReceived(intent.getStringExtra(GSConstants.PARM_JSON));
		}
	}
	
	private void singleMatchReceived(String json) {
		if(json == null) Log.e(MatchDataBroadcastReceiver.class.getName(), "JSON is empty!");
		Gson gson = new Gson();
		GSMatch match = gson.fromJson(json, GSMatch.class);
		if(match == null) {
			Log.e(MatchDataBroadcastReceiver.class.getName(), "Match is null");
			return;
		}
		for(GSMatchDataListener listener : listeners) {
			listener.matchReceived(match);
		}
	}
}
