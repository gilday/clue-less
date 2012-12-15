package edu.jhu.ep.butlerdidit.service;

public interface AuthenticationChangedListener {

	void onUserAuthenticated(String gamertag);
	
	void onUserLoggedOff();
	
	void onAuthenticationError(String errorMessage);
}
