package edu.jhu.ep.butlerdidit.service;

import edu.jhu.ep.butlerdidit.domain.Match;

public interface MatchDataListener {
	
	void matchReceived(Match match);
}
