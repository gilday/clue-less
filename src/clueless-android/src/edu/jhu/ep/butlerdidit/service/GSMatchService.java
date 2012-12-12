package edu.jhu.ep.butlerdidit.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.service.api.GSConstants;
import edu.jhu.ep.butlerdidit.service.api.GSMatchHelper;

public class GSMatchService extends RoboIntentService {
	
	@Inject
	private GSHttpClient httpClient;
	private LocalBroadcastManager lbm;
	
	public GSMatchService() {
		super(GSMatchService.class.getName());
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		if(action.equals(GSConstants.ACTION_LOGIN))
			doLogin(intent);
		else if(action.equals(GSConstants.ACTION_MATCH_UPDATE))
			doUpdateMatch(intent);
		else if(action.equals(GSConstants.ACTION_GET_MATCH))
			doGetMatch(intent);
	}
	
	private void doLogin(Intent intent) {

		String gamertag = intent.getStringExtra(GSConstants.PARM_EMAIL);
		String password = intent.getStringExtra(GSConstants.PARM_PASSWORD);
		int status = 0;
		lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		
		try {
			RestResponse response = httpClient.login(gamertag, password);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent broadcast = new Intent();
			broadcast.putExtra(GSConstants.PARM_ERROR_MESSAGE, ioe.getMessage());
			broadcast.setAction(GSConstants.BROADCAST_AUTHENTICATION_FAILED);
			lbm.sendBroadcast(broadcast);
		}
		
		Intent broadcast = new Intent();
		switch(status) {
		case HttpURLConnection.HTTP_CREATED:
			// send login success
			broadcast.setAction(GSConstants.BROADCAST_AUTHENTICATION_SUCCESS);
			broadcast.putExtra(GSConstants.PARM_EMAIL, gamertag);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			// send login failure
			broadcast.setAction(GSConstants.BROADCAST_AUTHENTICATION_FAILED);
			broadcast.putExtra(GSConstants.PARM_ERROR_MESSAGE, "Email or Password is incorrect");
			break;
		}
		lbm.sendBroadcast(broadcast);
	}
	
	private void doUpdateMatch(Intent intent) {
		String json = intent.getStringExtra(GSConstants.PARM_UPDATEMATCH);
		int matchId = intent.getIntExtra(GSConstants.PARM_ID, 0);
		
		int status = 0;
		lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		
		try {
			RestResponse response = httpClient.updateMatch(matchId, json);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent broadcast = new Intent(GSConstants.BROADCAST_MATCHUPDATE_SUCCESS);
			intent.putExtra(GSConstants.PARM_ERROR_MESSAGE, ioe.getMessage());
			lbm.sendBroadcast(broadcast);
		}
		
		Intent broadcast;
		switch(status) {
		case HttpURLConnection.HTTP_OK:
			broadcast = new Intent(GSConstants.BROADCAST_MATCHUPDATE_SUCCESS);
			lbm.sendBroadcast(broadcast);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			broadcast = new Intent(GSConstants.BROADCAST_MATCHUPDATE_FAILURE);
			lbm.sendBroadcast(broadcast);
			break;
		default:
			reportUnexpectedResponse(status);
		}
	}
	
	private void doGetMatch(Intent intent) {
		int matchId = intent.getIntExtra(GSConstants.PARM_ID, -1);
		int status = 0;
		lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		RestResponse response = null;
		
		try {
			response = httpClient.getMatchById(matchId);
			status = response.getHttpStatusCode();
		} catch(IOException ioe) {
			Intent getMatchIntent = new Intent(GSConstants.ACTION_GET_MATCH);
			getMatchIntent.putExtra(GSConstants.PARM_ID, matchId);
			lbm.sendBroadcast(getMatchIntent);
			return;
		}
		
		Intent broadcast;
		switch(status) {
		case HttpURLConnection.HTTP_OK:
			broadcast = new Intent(GSConstants.BROADCAST_MATCHRECEIVED_SUCCESS);
			broadcast.putExtra(GSConstants.PARM_JSON, response.getJsonString());
			lbm.sendBroadcast(broadcast);
			break;
		case HttpURLConnection.HTTP_BAD_REQUEST:
			broadcast = new Intent(GSConstants.BROADCAST_MATCHRECEIVED_FAILURE);
			lbm.sendBroadcast(broadcast);
			break;
		default:
			reportUnexpectedResponse(status);
		}
	}
	
	private void reportUnexpectedResponse(int status) {
		Log.e(GSMatchHelper.class.getName(), String.format("Unexpected response from HTTP Client: %d", status));
	}
}
