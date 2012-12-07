package edu.jhu.ep.butlerdidit.test;

import android.content.Intent;
import android.test.mock.MockContext;
import edu.jhu.ep.butlerdidit.domain.Match;
import edu.jhu.ep.butlerdidit.service.MatchDataBroadcastReceiver;
import edu.jhu.ep.butlerdidit.service.MatchDataListener;
import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;
import junit.framework.TestCase;

public class MatchDataBroadcastReceiverTests extends TestCase {

	private MatchDataBroadcastReceiver broadcastReceiver;
	
	protected void setUp() throws Exception {
		broadcastReceiver = new MatchDataBroadcastReceiver();
	}
	
	protected void tearDown() throws Exception {
		broadcastReceiver = null;
	}
	
	public void testMatchReceived() {
		// GIVEN
		// a match receiver that will ensure the match was created
		broadcastReceiver.registerListener(new MatchAssertion());
		String action = GameServerConstants.ACTION_MATCH_RECEIVED;
		String json = 
			"{ " +
				"created_at:\"2012-12-05T23:41:01Z\"," +
				"id:1," +
				"min_players:2," +
				"max_players:6," +
				"status:\"this is a status\"," +
				"message:\"this is a message\"," +
				"match_data:\"[1,2,3,4,5]\"," +
				"player: {" +
					"created_at: \"2012-12-05T23:41:01Z\"," +
					"updated_at: \"2012-12-05T23:41:01Z\"," +
					"id:1," +
					"email:\"testPlayer@example.com\"" +
				"}" +
			"}";
		Intent intent = new Intent(action);
		intent.putExtra(GameServerConstants.PARM_JSON, json);
		
		// WHEN
		// the broadcast receiver is given intent valid match json
		broadcastReceiver.onReceive(new MockContext(), intent);
		
		// THEN
		// MatchAssertion doesn't complain
	}
	
	class MatchAssertion implements MatchDataListener {
		@Override
		public void matchReceived(Match match) {
			assertNotNull(match.getMessage());
			assertNotNull(match.getStatus());
			assertNotNull(match.getRawMatchData());
			assertTrue(match.getMinPlayers() > 1);
			assertTrue(match.getMaxPlayers() > 1);
		}
	}
}
