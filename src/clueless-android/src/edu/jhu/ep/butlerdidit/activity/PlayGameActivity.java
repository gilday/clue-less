package edu.jhu.ep.butlerdidit.activity;

import java.util.List;
import java.util.Vector;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.Room;

@ContentView(R.layout.activity_play_game)
public class PlayGameActivity extends RoboActivity 
{
	public static final String PARM_MATCHID = "edu.jhu.ep.butlerdidit.playgame.matchid";
	
	private static final String TAG = PlayGameActivity.class.getName();

	private int currentMatchId = 0;
	private GameBoard game;
	CluePlayer fakePlayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		currentMatchId = getIntent().getIntExtra(PARM_MATCHID, 0);
		// If the intent that starts this activity has not sent a match ID to use,
		if(currentMatchId <= 0) {
			// Then use the test data
			scaffoldTestData();
			return;
			
		}
	}
	
	public void scaffoldTestData() 
	{
		fakePlayer = new CluePlayer();
		fakePlayer.setClueCharacter(ClueCharacter.MsScarlet);
		fakePlayer.setLocation("Library-Study"); //hacked up to gain access to the class
		
		List<CluePlayer> fakePlayers = new Vector<CluePlayer>();
		fakePlayers.add(fakePlayer);
		game = new GameBoard(fakePlayers);
	}
	
	class ViewHelpers 
	{
		/**
		 * Just deals with UI. Unhides and hides space for pawn
		 * @param from ID of space to move from e.g. "Study"
		 * @param to ID of space to move pawn to e.g. "Hall"
		 */
				
		// TODO Finish method
		private String translateToPawnId(String characterName, String spaceId) 
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
		
	    private String lowCaseRoomtoUpCaseRoom(String lowRoom)
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
		
		void MakePawnVisible(CluePlayer player, String to, View view) 
		{
			String PawnIdString = translateToPawnId(player.getClueCharacter().getName(), to);
			int PawnID = getResources().getIdentifier(PawnIdString, "id", "edu.jhu.ep.butlerdidit");
			ImageView PawnImage = (ImageView) findViewById(PawnID);
			System.out.println("The Pawn ID is " + PawnIdString);
			PawnImage.setVisibility(View.VISIBLE);
		}
		
		String RoomIdToName(int RoomID)
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

	public void MoveToRoom (View view)
	{
		ViewHelpers viewhelp = new ViewHelpers();
		String Room = viewhelp.RoomIdToName(view.getId());
		GameBoardSpace space = new GameBoardSpace(Room);
		System.out.println(space.getSpaceId());
		
		if(game.isPlayerAbleToMoveToSpace(fakePlayer, space))
		{
			System.out.println("you can move to the room1");
			viewhelp.MakePawnVisible(fakePlayer, Room, view);
		}
		else if(!game.isPlayerAbleToMoveToSpace(fakePlayer, space))
		{
			System.out.println("Plese select another room");
		}
	}
}
