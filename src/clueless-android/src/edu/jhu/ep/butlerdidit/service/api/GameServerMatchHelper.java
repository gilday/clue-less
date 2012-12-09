package edu.jhu.ep.butlerdidit.service.api;


public interface GameServerMatchHelper {
	
	void setGameServerMatchListener(GameServerMatchListener listener);
	
	Match getCurrentMatch();
	
	void setCurrentMatchById(int matchId);
	
	void startWatchingMatch();
	
	void stopWatchingMatch();
	
	void updateMatch(Match match);

}
