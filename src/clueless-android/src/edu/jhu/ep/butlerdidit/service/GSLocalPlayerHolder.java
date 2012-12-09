package edu.jhu.ep.butlerdidit.service;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class GSLocalPlayerHolder {
	
	private String localPlayerEmail;

	public String getLocalPlayerEmail() {
		return localPlayerEmail;
	}

	public void setLocalPlayerEmail(String localPlayerEmail) {
		this.localPlayerEmail = localPlayerEmail;
	}

}
