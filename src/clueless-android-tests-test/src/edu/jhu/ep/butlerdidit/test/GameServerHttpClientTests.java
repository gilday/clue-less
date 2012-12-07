package edu.jhu.ep.butlerdidit.test;

import java.io.IOException;

import junit.framework.TestCase;
import edu.jhu.ep.butlerdidit.service.GameServerHttpClient;
import edu.jhu.ep.butlerdidit.service.RestResponse;

/**
 * This integration test expects that the game server is running with 
 * seed data. Not having the right data will cause these tests to fail 
 * Always point these tests at a test instantiation of game server
 * 
 * All tests implicitly check login and logout
 * @author jgilday
 *
 */
public class GameServerHttpClientTests extends TestCase {

	private GameServerHttpClient httpClient;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		httpClient = new GameServerHttpClient();
		doLogin();
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
		
		doLogout();
		httpClient = null;
	}
	
	public void testGetMatch() throws IOException {
		// GIVEN
		// the server's test data has a match with ID 1
		int matchId = 1;
		
		// WHEN
		// http client gets match by ID
		RestResponse response = null;
		response = httpClient.getMatchById(matchId);
		
		// THEN
		// Status is 200
		assertEquals(200, response.getHttpStatusCode());
		// json string returned
		assertNotNull(response.getJsonString());
	}
	
	public void testGetMatches() throws IOException {
		// GIVEN
		// the server's test data has at least one match
		
		// WHEN
		// http client gets all matches
		RestResponse response = null;
		response = httpClient.getMatches();
		
		// THEN
		// Status is 200
		assertNotNull(response);
		assertEquals(200, response.getHttpStatusCode());
		// json string returned
		assertNotNull(response.getJsonString());
	}
	
	private void doLogin() {
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
		assertEquals(true, httpClient.isLoggedIn());
	}
	
	private void doLogout() {
		// GIVEN
		// the http client is logged in as test user
		
		// WHEN
		// http client deletes login
		RestResponse response = null;
		try {
			response = httpClient.logout();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		// THEN
		// server responds 200
		assertNotNull(response);
		assertEquals(200, response.getHttpStatusCode());
		assertNull(response.getJsonString());
		assertEquals(false, httpClient.isLoggedIn());
	}
}
