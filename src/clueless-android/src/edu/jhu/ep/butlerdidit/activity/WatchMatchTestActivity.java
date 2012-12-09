package edu.jhu.ep.butlerdidit.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSMatchHelper;
import edu.jhu.ep.butlerdidit.service.api.GSMatchListener;

@ContentView(R.layout.activity_watch_match_test)
public class WatchMatchTestActivity extends RoboActivity implements GSMatchListener {

	public static final String PARM_MATCHID = "edu.jhu.ep.butlerdidit.activity.watchactivity.matchid";
	
	private int currentMatchId = 0;
	@Inject
	private GSMatchHelper gsHelper;

	@InjectView(R.id.screen_log)
	private TextView screenLog;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentMatchId = getIntent().getIntExtra(PARM_MATCHID, 0);
		if(currentMatchId <= 0) { 
			Log.e(WatchMatchTestActivity.class.getName(), "Didn't pass a match ID!");
			finish();
		}
		
		gsHelper.setCurrentMatchById(currentMatchId);
		gsHelper.setGameServerMatchListener(this);
		gsHelper.startWatchingMatch();
		logToScreen("Watch match");
	}
	
	private void logToScreen(String message) {
		String text = (String) screenLog.getText();
		text += "\n" + message;
		screenLog.setText(text);
	}

	@Override
	public void enterNewGame(GSMatch match) {
		logToScreen("Enter new game");
	}

	@Override
	public void layoutMatch(GSMatch match) {
		logToScreen("Layout match");
	}

	@Override
	public void takeTurn(GSMatch match) {
		logToScreen("Take turn");
	}

	@Override
	public void receiveEndGame(GSMatch match) {
		logToScreen("End game");
	}
}
