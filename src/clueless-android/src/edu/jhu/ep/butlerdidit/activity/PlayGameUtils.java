package edu.jhu.ep.butlerdidit.activity;

import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.Room;

public class PlayGameUtils {

	// TODO Get rid of all the if's and do this by convention
	// 1) Characters: Drop the first part of the string and make the result lowercase e.g. "Mrs. Scarlett" -> "scarlett"
	// 2) Rooms: use toLowerCase and get rid of white space
	public static String translateToPawnId(String characterName, String spaceId) 
	{
		String character = null;
		String space = lowCaseRoomtoUpCaseRoom(spaceId);
		
		// translate character
		if(characterName.equals(ClueCharacter.MsScarlet.getName()))
			character = "scarlett";
		if(characterName.equals(ClueCharacter.ColMustard.getName()))
			character = "mustard";
		if(characterName.equals(ClueCharacter.MrGreen.getName()))
			character = "green";
		if(characterName.equals(ClueCharacter.MrsPeacock.getName()))
			character = "peacock";	
		if(characterName.equals(ClueCharacter.MrsWhite.getName()))
			character = "white";
		if(characterName.equals(ClueCharacter.ProfPlum.getName()))
			character = "plum";		
		
		// translate room
		if(space.equals(Room.STUDY))
			space = "study";
		if(space.equals(Room.BALLROOM))
			space = "ballroom";
		if(space.equals(Room.BILLIARDROOM))
			space = "billiard";
		if(space.equals(Room.CONSERVATORY))
			space = "conservatory";
		if(space.equals(Room.DININGROOM))
			space = "dining";
		if(space.equals(Room.HALL))
			space = "hall";
		if(space.equals(Room.KITCHEN))
			space = "kitchen";
		if(space.equals(Room.LOUNGE))
			space = "lounge";
		if(space.equals(Room.LIBRARY))
			space = "library";
		
		return String.format("%s_%s", character, space);
	}
	
	// TODO Get rid of if's and do by convention
	// 1) make "ballroom" and "billiard" into "ball_room" and "billiard_room"
	// 2) algo replaces all '_' with white space and captilizes each word
    public static String lowCaseRoomtoUpCaseRoom(String lowRoom)
    {
		if(lowRoom.equals("study"))
		{
			return "Study";
		}
		else if(lowRoom.equals("hall"))
		{
			return "Hall";
		}
		else if(lowRoom.equals("lounge"))
		{
			return "Lounge";
		}
		else if(lowRoom.equals("library"))
		{
			return "Library";
		}
		else if(lowRoom.equals("billiard"))
		{
			return "Billiard Room";
		}
		else if(lowRoom.equals("dining"))
		{
			return "Dining Room";
		}
		else if(lowRoom.equals("conservatory"))
		{
			return "Conservatory";
		}
		else if(lowRoom.equals("ballroom"))
		{
			return "Ball Room";
		}
		else if(lowRoom.equals("kitchen"))
		{
			return "Kitchen";
		}
		return "nothing";
	}
    
    // TODO Use static reference instead of value
	public static String roomIdToName(int RoomID)
	{
		if(RoomID == 2131296266)
		{
			return "ballroom";
		}
		else if(RoomID == 2131296268)
		{
			return "billiard";
		}
		else if(RoomID == 2131296271)
		{
			return "conservatory";
		}
		else if(RoomID == 2131296270)
		{
			return "dining";
		}
		else if(RoomID == 2131296264)
		{
			return "hall";
		}
		else if(RoomID == 2131296267)
		{
			return "kitchen";
		}
		else if(RoomID == 2131296272)
		{
			return "library";
		}
		else if(RoomID == 2131296269)
		{
			return "lounge";
		}
		else if(RoomID == 2131296265)
		{
			return "study";
		}
		return "nothing";
	}
}
