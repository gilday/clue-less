package edu.jhu.ep.butlerdidit.test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import junit.framework.TestCase;

public class GSMatchTests extends TestCase {
	
	public void testMatchCanDeserializeCustomMatchState() {
		// GIVEN
		// GSMatchUpdateModel has a JSON object
		Gson gson = new Gson();
		
		MatchUpdate update = new MatchUpdate();
		update.name = "Hola mundo";
		
		CustomMatchState state = new CustomMatchState();
		state.number = 2;
		state.string = "hello world";
		
		update.custom = gson.toJsonTree(state);
		
		// WHEN
		// serialize GSMatchUpdateModel
		String json = gson.toJson(update);
		System.out.println(json);
		
		// THEN
		// custom MatchState is preserved as JSON
	}

	class CustomMatchState {
		String string;
		int number;
	}
	
	class MatchUpdate {
		String name;
		JsonElement custom;
	}
}
