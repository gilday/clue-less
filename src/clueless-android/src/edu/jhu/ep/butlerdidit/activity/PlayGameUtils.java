package edu.jhu.ep.butlerdidit.activity;

import java.util.Locale;

public class PlayGameUtils {

	static String translateToRoomPawnId(String characterName, String roomId) 
	{
		// Chop off the character's title to get surname
		String surname = characterName.substring(characterName.lastIndexOf(' ') + 1);
		String roomName = roomId;
		// if this is a room that ends in "room"
		if(roomId.contains(" ")) {
			// Get the name of the room minus 'room'
			roomName = roomId.substring(0, roomId.indexOf(' ') + 1);
		}
		// squash together and lowercase
		return String.format("%s_%s", surname, roomName).toLowerCase(Locale.US);
	}
	
	static String translateToHallwayPawnId(String hallwayId)
	{
		/* TODO so the way the pawns in the rooms are named is standard so we can use 
		 * an algorithm to transform character id and room id to pawn id.
		 * Could come up with a similar algorithm for hallway pawns. Probably don't want 
		 * to use a giant if else again because there are a lot of hallways
		 * 
		 * Suggest the following to do it in one line
		 * make the ID for each pawn ImageView pawn_hallwayid
		 * and use
		 * 
		 * return "pawn_" + hallwayId.replace('-', '_').toLowerCase();
		 * 
		 */ 
	}
}
