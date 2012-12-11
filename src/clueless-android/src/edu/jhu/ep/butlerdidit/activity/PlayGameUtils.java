package edu.jhu.ep.butlerdidit.activity;

import java.util.Locale;

public class PlayGameUtils {

	static String translateToPawnId(String characterName, String roomId) 
	{
		// Chop off the character's title to get surname
		String surname = characterName.substring(characterName.lastIndexOf(' ') + 1);
		// Get the name of the room minus 'room'
		String roomName = roomId.substring(0, roomId.indexOf(' ') + 1);
		// squash together and lowercase
		return String.format("%s_%s", surname, roomName).toLowerCase(Locale.US);
	}
}
