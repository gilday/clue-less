package edu.jhu.ep.butlerdidit.test;

import android.content.Intent;
import android.test.mock.MockContext;
import edu.jhu.ep.butlerdidit.service.MatchDataBroadcastReceiver;
import edu.jhu.ep.butlerdidit.service.api.GSParticipant;
import edu.jhu.ep.butlerdidit.service.api.GSConstants;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSMatchDataListener;
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
		String action = GSConstants.BROADCAST_MATCHRECEIVED_SUCCESS;
		String json = 
			"{ " +
				"\"id\":1," +
				"\"updated_at\": \"2012-12-05T23:41:01Z\"," +
				"\"min_players\":2," +
				"\"max_players\":6," +
				"\"status\":\"this is a status\"," +
				"\"message\":\"this is a message\"," +
				"\"match_data\":\"[1,2,3,4,5]\"," +
				"\"participants\": [{" +
					"\"id\": 1, \"match_id\": 1, \"player_id\": 1, " +
						"\"player\": {" +
							"\"created_at\": \"2012-12-05T23:41:01Z\"," +
							"\"updated_at\": \"2012-12-05T23:41:01Z\"," +
							"\"id\":1," +
							"\"email\":\"testPlayer@example.com\"" +
						"}" +
				"}]" +
			"}";
		System.out.println(json);
		Intent intent = new Intent(action);
		intent.putExtra(GSConstants.PARM_JSON, json);
		
		// WHEN
		// the broadcast receiver is given intent valid match json
		broadcastReceiver.onReceive(new MockContext(), intent);
		
		// THEN
		// MatchAssertion doesn't complain
	}
	
	class MatchAssertion implements GSMatchDataListener {
		@Override
		public void matchReceived(GSMatch match) {
			assertNotNull(match.getMessage());
			assertNotNull(match.getStatus());
			assertNotNull(match.getRawMatchData());
			//assertNotNull(match.getUpdatedAt());
			assertTrue(match.getMinPlayers() > 1);
			assertTrue(match.getMaxPlayers() > 1);
			assertNotNull(match.getParticipants());
			GSParticipant participant = (GSParticipant) match.getParticipants().toArray()[0];
			assertEquals("testPlayer@example.com", participant.getGamePlayer().getEmail());
		}
	}
}
