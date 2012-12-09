package edu.jhu.ep.butlerdidit.service;

import com.google.inject.Singleton;

@Singleton
public class GSLocalPlayerHolder {
	
	private String localPlayerEmail;

	public String getLocalPlayerEmail() {
		return localPlayerEmail;
	}

	public void setLocalPlayerEmail(String localPlayerEmail) {
		this.localPlayerEmail = localPlayerEmail;
	}

}
