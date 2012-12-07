package edu.jhu.ep.butlerdidit.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;

public class AuthenticationBroadcastReceiver extends GameServerBroadcastReceiver<AuthenticationChangedListener> {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(GameServerConstants.BROADCAST_AUTHENTICATION_SUCCESS)) {
			String email = intent.getStringExtra(GameServerConstants.PARM_EMAIL);
			for(AuthenticationChangedListener l : listeners) 
				l.onUserAuthenticated(email);
		}
		else if (action.equals(GameServerConstants.BROADCAST_AUTHENTICATION_FAILED)) {
			String errorMessage = intent.getStringExtra(GameServerConstants.PARM_ERROR_MESSAGE);
			for(AuthenticationChangedListener l : listeners)
				l.onAuthenticationError(errorMessage);
		}
		else {
			Log.e(AuthenticationBroadcastReceiver.class.getName(), "I don't know what to do with " + action);
		}
	}

}
