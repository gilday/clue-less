package edu.jhu.ep.butlerdidit.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.LocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.api.GSConstants;

public class AuthenticationBroadcastReceiver extends GSBroadcastReceiver<AuthenticationChangedListener> {

	@Inject
	private LocalPlayerHolder lpHolder;
	
	public AuthenticationBroadcastReceiver(LocalPlayerHolder lpHolder) { 
		this.lpHolder = lpHolder;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(action.equals(GSConstants.BROADCAST_AUTHENTICATION_SUCCESS)) {
			String email = intent.getStringExtra(GSConstants.PARM_EMAIL);
			for(AuthenticationChangedListener l : listeners) 
				l.onUserAuthenticated(email);
			// Set local player
			lpHolder.setLocalPlayerEmail(email);
		}
		else if (action.equals(GSConstants.BROADCAST_AUTHENTICATION_FAILED)) {
			String errorMessage = intent.getStringExtra(GSConstants.PARM_ERROR_MESSAGE);
			for(AuthenticationChangedListener l : listeners)
				l.onAuthenticationError(errorMessage);
		}
		else {
			Log.e(AuthenticationBroadcastReceiver.class.getName(), "I don't know what to do with " + action);
		}
	}

}
