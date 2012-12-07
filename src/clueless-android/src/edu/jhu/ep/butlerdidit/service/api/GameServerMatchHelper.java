package edu.jhu.ep.butlerdidit.service.api;

import edu.jhu.ep.butlerdidit.domain.Match;

public interface GameServerMatchHelper {
	
	void setGameServerMatchListener(GameServerMatchListener listener);
	
	void watchMatch(int withId);
	
	void stopWatchingMatch();
	
	void updateMatch(Match match);

}
