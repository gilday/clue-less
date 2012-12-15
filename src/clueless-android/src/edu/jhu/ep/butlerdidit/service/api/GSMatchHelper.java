package edu.jhu.ep.butlerdidit.service.api;

public interface GSMatchHelper {
	
	void setGameServerMatchListener(GSMatchListener listener);
	
	GSMatch getCurrentMatch();
	
	void setCurrentMatchById(int matchId);
	
	void startWatchingMatch();
	
	void stopWatchingMatch();
	
	void updateMatch(GSUpdateMatchModel model);

}
