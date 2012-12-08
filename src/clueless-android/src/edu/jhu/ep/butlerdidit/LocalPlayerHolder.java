package edu.jhu.ep.butlerdidit;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class LocalPlayerHolder {
	
	private String localPlayerEmail;

	public String getLocalPlayerEmail() {
		return localPlayerEmail;
	}

	public void setLocalPlayerEmail(String localPlayerEmail) {
		this.localPlayerEmail = localPlayerEmail;
	}

}
