package edu.jhu.ep.butlerdidit.service.api;

import edu.jhu.ep.butlerdidit.domain.Match;

public interface GameServerMatchListener {
	
	void enterNewGame(Match match);
	
	void layoutMatch(Match match);
	
	void takeTurn(Match match);
	
	void receiveEndGame(Match match);
	
	//void sendNotice(String notice, Match match);
}
