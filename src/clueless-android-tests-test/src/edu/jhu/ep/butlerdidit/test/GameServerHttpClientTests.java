package edu.jhu.ep.butlerdidit.test;

import java.io.IOException;

import junit.framework.TestCase;
import edu.jhu.ep.butlerdidit.service.GameServerHttpClient;
import edu.jhu.ep.butlerdidit.service.RestResponse;

/**
 * This integration test expects that the game server is running with 
 * seed data. Not having the right data will cause these tests to fail 
 * Always point these tests at a test instantiation of game server
 * @author jgilday
 *
 */
public class GameServerHttpClientTests extends TestCase {

	private GameServerHttpClient httpClient;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		httpClient = new GameServerHttpClient();
	}

	public void testLogin() {
		// GIVEN
		// the server's test data has a player with the following credentials 
		String email = "testPlayer@example.com";
		String password = "password";
		
		// WHEN
		// http client posts login
		RestResponse response = null;
		try {
			response = httpClient.login(email, password);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		// THEN
		// the HTTP Response is 201 with empty entity
		assertNotNull(response);
		assertEquals(201, response.getHttpStatusCode());
		assertNull(response.getJsonString());
	}
}
