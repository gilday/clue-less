package edu.jhu.ep.butlerdidit.service.api;


public interface GSMatchListener {
	
	void enterNewGame(GSMatch match);
	
	void layoutMatch(GSMatch match);
	
	void takeTurn(GSMatch match);
	
	void receiveEndGame(GSMatch match);
	
	//void sendNotice(String notice, Match match);
}
