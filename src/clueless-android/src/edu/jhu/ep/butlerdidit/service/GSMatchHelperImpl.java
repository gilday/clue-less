package edu.jhu.ep.butlerdidit.service;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.jhu.ep.butlerdidit.service.api.GSConstants;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSMatchDataListener;
import edu.jhu.ep.butlerdidit.service.api.GSMatchHelper;
import edu.jhu.ep.butlerdidit.service.api.GSMatchListener;
import edu.jhu.ep.butlerdidit.service.api.GSUpdateMatchModel;

@Singleton
public class GSMatchHelperImpl implements GSMatchHelper, GSMatchDataListener {
	
	private static final int INTERVAL = 3000;

	private Context context;
	
	@Inject
	private GSLocalPlayerHolder lpHolder;
	
	private GSMatch currentMatch;
	private int currentMatchId;
	private GSMatchListener listener;
	private Timer timer;
	private MatchDataBroadcastReceiver matchDataReceiver;
	
	@Inject
	public GSMatchHelperImpl(Context context) {
		this.context = context;
		matchDataReceiver = new MatchDataBroadcastReceiver();
		matchDataReceiver.registerListener(this);
	}
	
	@Override
	public void setGameServerMatchListener(GSMatchListener listener) {
		this.listener = listener;
	}
	
	@Override
	public GSMatch getCurrentMatch() { return currentMatch; }
	
	@Override
	public void setCurrentMatchById(int matchId) {
		if(isWatchingMatch())
			stopWatchingMatch();
		
		// Store ID
		currentMatchId = matchId;
	}
	

	@Override
	public void startWatchingMatch() {
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
		lbm.registerReceiver(matchDataReceiver, new IntentFilter(GSConstants.BROADCAST_MATCHRECEIVED_SUCCESS));	
		
		// Start timer
    	timer = new Timer();
    	timer.schedule(new PollMatch(), 0, INTERVAL);
	}

	@Override
	public void stopWatchingMatch() {
		if(isWatchingMatch()) {
			LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
			lbm.unregisterReceiver(matchDataReceiver);
			timer.cancel();
			timer = null;
		}
	}

	@Override
	public void updateMatch(GSUpdateMatchModel model) {
		
		Intent updateIntent = new Intent(context, GSMatchService.class);
		updateIntent.setAction(GSConstants.ACTION_MATCH_UPDATE);
		updateIntent.putExtra(GSConstants.PARM_UPDATEMATCH, model);
		context.startService(updateIntent);
	}
	
	public void setLocalPlayerHolder(GSLocalPlayerHolder lpHolder) { 
		this.lpHolder = lpHolder;
	}
	
	private boolean isWatchingMatch() {
		return timer != null;
	}
	
	private void requestMatchById(int matchId) {
		Intent pollMatch = new Intent(context, GSMatchService.class);
		pollMatch.setAction(GSConstants.ACTION_GET_MATCH);
		pollMatch.putExtra(GSConstants.PARM_ID, matchId);
		context.startService(pollMatch);
	}
	
	class PollMatch extends TimerTask {

		@Override
		public void run() {
			requestMatchById(currentMatchId);
		}
		
	}

	/**
	 * TODO this is ugly. don't like the branching
	 */
	@Override
	public void matchReceived(GSMatch newMatch) {
		if(currentMatchId != newMatch.getId()) {
			Log.e(GSMatchHelper.class.getName(), "Can only receive match entities of one ID for now");
			return;
		}
		GSMatch oldMatch = currentMatch;
		currentMatch = newMatch;
		// The logic for determining if any events should be raised like "it's your turn"

		// Has the match been updated at all since our last check?
		if(oldMatch != null && oldMatch.getUpdatedAt().equals(currentMatch.getUpdatedAt()))
			return;
		
		// Is over?
		if(currentMatch.getStatus() == "finished") {
			listener.receiveEndGame(currentMatch);
			return;
		}
		
		// Is New Game?
		if(currentMatch.getRawMatchData() == null) {
			listener.enterNewGame(currentMatch);
			return;
		}
		
		if(oldMatch == null)
			return;
	
		// Player has changed?
		if(!oldMatch.getCurrentPlayer().equals(currentMatch.getCurrentPlayer())) {
			// Is our turn?
			String localPlayerEmail = lpHolder.getLocalPlayerEmail();
			if(localPlayerEmail == currentMatch.getCurrentPlayer()) {
				listener.takeTurn(currentMatch);
			} else {
				// Is not our turn, but hands have changed
				listener.layoutMatch(currentMatch);
			}
		}
	}	
}
