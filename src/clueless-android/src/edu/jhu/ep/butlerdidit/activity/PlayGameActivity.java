package edu.jhu.ep.butlerdidit.activity;

import java.util.List;
import java.util.Vector;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.Room;

@ContentView(R.layout.activity_play_game)
public class PlayGameActivity extends RoboActivity 
{
	public static final String PARM_MATCHID = "edu.jhu.ep.butlerdidit.playgame.matchid";
	
	private static final String TAG = PlayGameActivity.class.getName();

	private int currentMatchId = 0;
	private GameBoard game;
	
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
	
	private void scaffoldTestData() {
		CluePlayer fakePlayer = new CluePlayer();
		fakePlayer.setClueCharacter(ClueCharacter.MsScarlet);
		fakePlayer.setLocation(Room.STUDY);
		
		List<CluePlayer> fakePlayers = new Vector<CluePlayer>();
		fakePlayers.add(fakePlayer);
		game = new GameBoard(fakePlayers);
	}
		class ViewHelpers {

		/**
		 * Just deals with UI. Unhides and hides space for pawn
		 * @param from ID of space to move from e.g. "Study"
		 * @param to ID of space to move pawn to e.g. "Hall"
		 */
			//==============================
			//*****START HERE TOMORROW *****
			//==============================
		void movePawn(CluePlayer player, String from, String to) {
			String fromPawnId = translateToPawnId(player.getClueCharacter().getName(), from);
			// get space pawn ImageView by ID
			// TODO Finish this method
		}
		
		// TODO Finish method
		private String translateToPawnId(String characterName, String spaceId) {
			String character = null;
			String space = null;
			
			// translate character
			if(characterName.equals(ClueCharacter.MsScarlet.getName()))
				character = "scarlet";
			
			// translate room
			if(spaceId.equals(Room.STUDY))
				space = "study";
			
			return String.format("%s-%s", character, space);
		}
	}

	/*public void SetPlayerStartSpots(int NumberOfPlayers,Player player)
	{
		while(NumberOfPlayers > 0)
		{
			player(NumberOfPlayers)
		}
	}
	public void UpdateNextMove()
	{
		// GIVEN
		// a game board with Professor Plum at his start spot
		Player profPlum = new Player();
		profPlum.setClueCharacter(ClueCharacter.ProfPlum);
		profPlum.setLocation(profPlum.getClueCharacter().getName());
		
		List<Player> players = new Vector<Player>();
		GameBoard gameBoard = new GameBoard(players);
		
		// WHEN
		// system asks board where Plum can go
		List<GameBoardSpace> possibleMoves = gameBoard.getPossibleMoves(profPlum);
	}*/
	
	public void UpdatePlayerLocationOnBoard(CluePlayer player)
	{
		String MsScarletID = "Ms. Scarlet";
		String ColMustardID = "Col. Mustard";
		String MrsWhiteID = "Mrs. White";
		String MrGreenID = "Mr. Green";
		String MrsPeacockID = "Mrs. Peacock";
		String ProfPlumID = "Prof. Plum"; 
	   
		if(MsScarletID.equals(player.getClueCharacter().getName()))
		{
		}
		else if(ColMustardID.equals(player.getClueCharacter().getName()))
		{
		}
		else if(MrsWhiteID.equals(player.getClueCharacter().getName()))
		{
		}
		else if(MrGreenID.equals(player.getClueCharacter().getName()))
		{
		}
		else if(MrsPeacockID.equals(player.getClueCharacter().getName()))
		{
		}
		else if(ProfPlumID.equals(player.getClueCharacter().getName()))
		{
		}
	}

	public void rbGroupSuspect(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.miss_scarlett:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.prof_plum:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.mrs_peacock:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.mr_green:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.col_mustard:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.mrs_white:
	            if (checked)
	            	//Do Something
	            break;
	    }
	}
	
	public void rbGroupWeapon(View view){
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rope:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.revolover:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.wrench:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.candlestick:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.knife:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.lead_pipe:
	            if (checked)
	            	//Do Something
	            break;
	    }
	} 

	public void rbGroupRoom(View view){
		 // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.study_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.hall_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.lounge_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.library_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.billiard_room_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.dining_room_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.conservatory_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.ballroom_small:
	            if (checked)
	            	//Do Something
	            break;
	        case R.id.kitchen_small:
	        	if (checked)
	        		//Do Something
	        	break;
	    }
	}

	public void accuse(View view){
	
	}
}
