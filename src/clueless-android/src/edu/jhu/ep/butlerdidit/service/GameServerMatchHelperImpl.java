package edu.jhu.ep.butlerdidit.service;

import java.util.Timer;
import java.util.TimerTask;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.LocalPlayerHolder;
import edu.jhu.ep.butlerdidit.domain.Match;
import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;
import edu.jhu.ep.butlerdidit.service.api.GameServerMatchHelper;
import edu.jhu.ep.butlerdidit.service.api.GameServerMatchListener;

@ContextSingleton
public class GameServerMatchHelperImpl implements GameServerMatchHelper, MatchDataListener {
	
	private static final int INTERVAL = 1000;

	@Inject
	private Context context;
	
	@Inject
	private LocalPlayerHolder lpHolder;
	
	private Match currentMatch;
	private int currentMatchId;
	private GameServerMatchListener listener;
	private Timer timer;
	private MatchDataBroadcastReceiver matchDataReceiver;
	
	public GameServerMatchHelperImpl(Context context) {
		this.context = context;
		matchDataReceiver = new MatchDataBroadcastReceiver();
		matchDataReceiver.registerListener(this);
	}
	
	@Override
	public void setGameServerMatchListener(GameServerMatchListener listener) {
		this.listener = listener;
	}
	
	@Override
	public Match getCurrentMatch() { return currentMatch; }
	
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
		lbm.registerReceiver(matchDataReceiver, new IntentFilter(GameServerConstants.ACTION_MATCH_RECEIVED));	
		
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
	public void updateMatch(Match match) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLocalPlayerHolder(LocalPlayerHolder lpHolder) { 
		this.lpHolder = lpHolder;
	}
	
	private boolean isWatchingMatch() {
		return timer != null;
	}
	
	private void requestMatchById(int matchId) {
		Intent pollMatch = new Intent(context, GameServerMatchService.class);
		pollMatch.setAction(GameServerConstants.ACTION_GET_MATCH);
		pollMatch.putExtra(GameServerConstants.PARM_ID, matchId);
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
	public void matchReceived(Match newMatch) {
		if(currentMatchId != newMatch.getId()) {
			Log.e(GameServerMatchHelper.class.getName(), "Can only receive match entities of one ID for now");
			return;
		}
		Match oldMatch = currentMatch;
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
