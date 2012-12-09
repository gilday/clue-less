package edu.jhu.ep.butlerdidit.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;
import edu.jhu.ep.butlerdidit.service.api.GameServerMatchHelper;

public class GameServerMatchService extends RoboIntentService {
	
	@Inject
	private GameServerHttpClient httpClient;
	private LocalBroadcastManager lbm;
	
	public GameServerMatchService() {
		super("GameServerMatchService");
		lbm = LocalBroadcastManager.getInstance(getApplicationContext());
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		if(action.equals(GameServerConstants.ACTION_LOGIN))
			doLogin(intent);
		else if(action.equals(GameServerConstants.ACTION_MATCH_UPDATE))
			doUpdateMatch(intent);
		else if(action.equals(GameServerConstants.ACTION_GET_MATCH))
			doGetMatch(intent);
	}
	
	private void doLogin(Intent intent) {

		String gamertag = intent.getStringExtra(GameServerConstants.PARM_EMAIL);
		String password = intent.getStringExtra(GameServerConstants.PARM_PASSWORD);
		int status = 0;
		
		try {
			RestResponse response = httpClient.login(gamertag, password);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent broadcast = new Intent();
			broadcast.putExtra(GameServerConstants.PARM_ERROR_MESSAGE, ioe.getMessage());
			broadcast.setAction(GameServerConstants.BROADCAST_AUTHENTICATION_FAILED);
			lbm.sendBroadcast(broadcast);
		}
		
		Intent broadcast = new Intent();
		switch(status) {
		case HttpURLConnection.HTTP_CREATED:
			// send login success
			broadcast.setAction(GameServerConstants.BROADCAST_AUTHENTICATION_SUCCESS);
			broadcast.putExtra(GameServerConstants.PARM_EMAIL, gamertag);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			// send login failure
			broadcast.setAction(GameServerConstants.BROADCAST_AUTHENTICATION_FAILED);
			broadcast.putExtra(GameServerConstants.PARM_ERROR_MESSAGE, "Email or Password is incorrect");
			break;
		}
		lbm.sendBroadcast(broadcast);
	}
	
	private void doUpdateMatch(Intent intent) {
		UpdateMatchModel model = intent.getParcelableExtra(GameServerConstants.PARM_UPDATEMATCH);
		int status = 0;
		
		try {
			RestResponse response = httpClient.updateMatch(model);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent broadcast = new Intent(GameServerConstants.BROADCAST_MATCHUPDATE_SUCCESS);
			intent.putExtra(GameServerConstants.PARM_ERROR_MESSAGE, ioe.getMessage());
			lbm.sendBroadcast(broadcast);
		}
		
		Intent broadcast;
		switch(status) {
		case HttpURLConnection.HTTP_OK:
			broadcast = new Intent(GameServerConstants.BROADCAST_MATCHUPDATE_SUCCESS);
			lbm.sendBroadcast(broadcast);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			broadcast = new Intent(GameServerConstants.BROADCAST_MATCHUPDATE_FAILURE);
			lbm.sendBroadcast(broadcast);
			break;
		default:
			reportUnexpectedResponse();
		}
	}
	
	private void doGetMatch(Intent intent) {
		int matchId = intent.getIntExtra(GameServerConstants.PARM_ID, -1);
		int status = 0;
		
		try {
			RestResponse response = httpClient.getMatchById(matchId);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent getMatchIntent = new Intent(GameServerConstants.ACTION_GET_MATCH);
			getMatchIntent.putExtra(GameServerConstants.PARM_ID, matchId);
			lbm.sendBroadcast(getMatchIntent);
		}
		
		Intent broadcast;
		switch(status) {
		case HttpURLConnection.HTTP_OK:
			broadcast = new Intent(GameServerConstants.BROADCAST_MATCHRECEIVED_SUCCESS);
			lbm.sendBroadcast(broadcast);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			broadcast = new Intent(GameServerConstants.BROADCAST_MATCHRECEIVED_FAILURE);
			lbm.sendBroadcast(broadcast);
			break;
		default:
			reportUnexpectedResponse();
		}
	}
	
	private void reportUnexpectedResponse() {
		Log.e(GameServerMatchHelper.class.getName(), "Unexpected response from HTTP Client");
	}
}
