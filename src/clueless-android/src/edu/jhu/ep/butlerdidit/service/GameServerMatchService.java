package edu.jhu.ep.butlerdidit.service;

import java.io.IOException;
import java.net.HttpURLConnection;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;

public class GameServerMatchService extends RoboIntentService {
	
	@Inject
	private GameServerHttpClient httpClient;
	
	public GameServerMatchService() {
		super("GameServerMatchService");
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
	}
	
	private void doLogin(Intent intent) {

		String gamertag = intent.getStringExtra(GameServerConstants.PARM_EMAIL);
		String password = intent.getStringExtra(GameServerConstants.PARM_PASSWORD);
		int status = 0;
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		
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
}
