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
			roomName = roomId.substring(0, roomId.indexOf(' ') + 0);
		}
		// squash together and lowercase
		return String.format("%s_%s", surname, roomName).toLowerCase(Locale.US);
	}
	
	static String translateToHallwayPawnId(String characterName)
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
		String surname = characterName.substring(characterName.lastIndexOf(' ') + 1);
		return String.format("%s_%s", surname, "pawn").toLowerCase(Locale.US);
	}
	static String translateToHallwayId(String Room)
	{
		//Tried to do this without an if-else statement and using the getViewWithTag. It just would not work...
		if("Study-Hall".equals(Room))
			return "Study_To_Hall";
		else if("Hall-Lounge".equals(Room))
			return "Hall_To_Lounge";
		else if("Lounge-Dining Room".equals(Room))
			return "Lounge_To_Diningroom";
		else if("Dining Room-Kitchen".equals(Room))
			return "Diningroom_To_Kitchen";
		else if("Kitchen-Ball Room".equals(Room))
			return "Kitchen_To_Ballroom";
		else if("Conservatory-Library".equals(Room))
			return "Conservatory_To_Library";
		else if("Library-Study".equals(Room))
			return "Library_To_Study";
		else if("Billiard Room-Library".equals(Room))
			return "Billiardroom_To_Library";
		else if("Billiard Room-Hall".equals(Room))
			return "Billiardroom_To_Hall";
		else if("Billiard Room-Dining Room".equals(Room))
			return "Billiardroom_To_Diningroom";
		else if("Billiard Room-Ball Room".equals(Room))
			return "Billiardroom_To_Ballroom";
		else if("Ball Room-Conservatory".equals(Room))
			return "Ballroom_To_Conservatory";
		return "NULL";
	}
}
