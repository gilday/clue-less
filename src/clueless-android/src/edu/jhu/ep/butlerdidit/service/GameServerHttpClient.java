package edu.jhu.ep.butlerdidit.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONObject;

import roboguice.inject.ContextSingleton;
import android.util.Log;

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
	
	public RestResponse login(String email, String password) throws IOException {
		URL loginUrl = new URL(gameServerEndpoint + "/session");
		
		HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");

		HashMap<String,String> keyVals = new HashMap<String, String>();
		keyVals.put("email", email);
		keyVals.put("password", password);
		JSONObject json = new JSONObject(keyVals);
		
		Log.d("JSON",json.toString());

		OutputStream out = new BufferedOutputStream(connection.getOutputStream());
		PrintWriter writer = new PrintWriter(out);
		writer.write(json.toString());
		writer.close();

		int status = connection.getResponseCode();
		return new RestResponse(status);
	}
	
//	private String convertStreamToString(InputStream is) {
//	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
//	    return s.hasNext() ? s.next() : "";
//	}
}
