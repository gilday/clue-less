package edu.jhu.ep.butlerdidit.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import roboguice.inject.ContextSingleton;

import com.google.gson.Gson;

/**
 * Group all the HTTP Calls in here so they're easier to unit test
 * Unit testing GameServerMatchService, or any IntentService, is a nightmare
 * @author jgilday
 *
 */
@ContextSingleton
public class GameServerHttpClient {
	
	static {
		CookieHandler.setDefault(new CookieManager());
	}
	
	// 10.0.2.2 is a special address for the host development machine
	private final String gameServerEndpoint = "http://10.0.2.2:3000";
	
	private boolean loggedIn = false;
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public RestResponse login(String email, String password) throws IOException {
		URL loginUrl = new URL(gameServerEndpoint + "/session");
		
		HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		HashMap<String,String> keyVals = new HashMap<String, String>();
		keyVals.put("email", email);
		keyVals.put("password", password);
		JSONObject json = new JSONObject(keyVals);
		
		OutputStream out = new BufferedOutputStream(connection.getOutputStream());
		PrintWriter writer = new PrintWriter(out);
		writer.write(json.toString());
		writer.close();

		int status = connection.getResponseCode();
		if(status == HttpURLConnection.HTTP_CREATED) 
			loggedIn = true;
		
		return new RestResponse(status);
	}
	
	public RestResponse logout() throws IOException {
		URL logoutUrl = new URL(gameServerEndpoint + "/session");
		
		HttpURLConnection connection = (HttpURLConnection) logoutUrl.openConnection();
		connection.setRequestMethod("DELETE");
		
		int status = connection.getResponseCode();
		if(status == HttpURLConnection.HTTP_OK)
			loggedIn = false;
		return new RestResponse(status);
	}

	public RestResponse getMatchById(int matchId) throws IOException {
		URL getMatchUrl = new URL(String.format("%s/matches/%d.json", gameServerEndpoint, matchId));
		
		return getJson(getMatchUrl);
	}
	
	public RestResponse getMatches() throws IOException {
		URL getMatchesUrl = new URL(String.format("%s/matches", gameServerEndpoint));
		
		return getJson(getMatchesUrl);
	}
	
	public RestResponse getPlayerByEmail(String email) throws IOException {
		URL getPlayerUrl = new URL(String.format("%s/players/%s.json", gameServerEndpoint, email));
		
		return getJson(getPlayerUrl);
	}
	
	public RestResponse updateMatch(UpdateMatchModel match) throws IOException {
		URL putMatchUrl = new URL(String.format("%s/matches/%d.json", gameServerEndpoint, match.getId()));
		
		Gson gson = new Gson();
		String json = gson.toJson(match);
		
		HttpURLConnection connection = (HttpURLConnection) putMatchUrl.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("PUT");
		
		OutputStream out = new BufferedOutputStream(connection.getOutputStream());
		PrintWriter writer = new PrintWriter(out);
		writer.write(json);
		writer.close();
		
		int status = connection.getResponseCode();
		
		return new RestResponse(status);
	}
	
	private String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	private RestResponse getJson(URL url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		int status = connection.getResponseCode();
		InputStream is = connection.getInputStream();
		
		String json = convertStreamToString(is);
		return new RestResponse(status, json);
	}
}
