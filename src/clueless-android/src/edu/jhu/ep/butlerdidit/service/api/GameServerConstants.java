package edu.jhu.ep.butlerdidit.service.api;

public class GameServerConstants {

	public static final String BROADCAST_AUTHENTICATION_FAILED = "edu.jhu.ep.butlerdidit.broadcast.authfailed";
	public static final String BROADCAST_AUTHENTICATION_SUCCESS = "edu.jhu.ep.butlerdidit.broadcast.authsuccess";
	public static final String BROADCAST_MATCHUPDATE_SUCCESS = "edu.jhu.ep.butlerdidit.broadcast.matchupdatesuccess";
	public static final String BROADCAST_MATCHUPDATE_FAILURE = "edu.jhu.ep.butlerdidit.broadcast.matchupdatefailure";
	public static final String BROADCAST_MATCHRECEIVED_SUCCESS = "edu.jhu.ep.butlerdidit.broadcast.matchreceivedsuccess";
	public static final String BROADCAST_MATCHRECEIVED_FAILURE = "edu.jhu.ep.butlerdidit.broadcast.matchreceivedfailure";
	
	public static final String ACTION_LOGIN = "edu.jhu.ep.butlerdidit.actions.login";
	public static final String ACTION_GET_MATCH = "edu.jhu.ep.butlerdidit.actions.getmatch";
	public static final String ACTION_MATCH_UPDATE = "edu.jhu.ep.butlerdidit.actions.updatematch";
	
	public static final String PARM_ERROR_MESSAGE = "error_message";
	public static final String PARM_EMAIL = "email";
	public static final String PARM_PASSWORD = "password";
	public static final String PARM_JSON = "json";
	public static final String PARM_ID = "matchId";
	public static final String PARM_UPDATEMATCH = "updateMatch";
}
