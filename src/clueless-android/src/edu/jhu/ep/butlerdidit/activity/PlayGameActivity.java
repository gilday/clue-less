package edu.jhu.ep.butlerdidit.activity;

import java.util.List;
import java.util.Vector;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinatorFactory;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.Room;
import edu.jhu.ep.butlerdidit.domain.json.ClueMatchState;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.api.GSMatch;
import edu.jhu.ep.butlerdidit.service.api.GSMatchHelper;
import edu.jhu.ep.butlerdidit.service.api.GSMatchListener;
import edu.jhu.ep.butlerdidit.service.api.GSUpdateMatchModel;

@ContentView(R.layout.activity_play_game)
public class PlayGameActivity extends RoboActivity implements GSMatchListener 
{
	public static final String PARM_MATCHID = "edu.jhu.ep.butlerdidit.playgame.matchid";
	private static final String TAG = PlayGameActivity.class.getName();

	// Inject application services
	@Inject private GSMatchHelper gsHelper;
	@Inject private ClueGameCoordinatorFactory coordinatorFactory;
	@Inject private GSLocalPlayerHolder lpHolder;
	
	private int currentMatchId = 0;
	private GameBoard game;
	private ClueGameCoordinator coordinator;
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
		
		gsHelper.setGameServerMatchListener(this);
		gsHelper.setCurrentMatchById(currentMatchId);
		gsHelper.startWatchingMatch();
	}
	
	// TODO scaffold a coordinator
	public void scaffoldTestData() 
	{
		fakePlayer = new CluePlayer();
		fakePlayer.setClueCharacter(ClueCharacter.MsScarlett);
		fakePlayer.setLocation("Library"); //hacked up to gain access to the class
		
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
		void makeRoomPawnVisible(ClueCharacter character, String to, String oldRoom, View view) 
		{
			String pawnIdString = PlayGameUtils.translateToRoomPawnId(character.getName(), to);
			int pawnID = getResources().getIdentifier(pawnIdString, "id", "edu.jhu.ep.butlerdidit");
			ImageView pawnImage = (ImageView) findViewById(pawnID);
			pawnImage.setVisibility(View.VISIBLE);
		}
		
		// TODO I don't know how this method is supposed to work because I'm not sure how 
		// pawns on hallways are implemented
		// I do know this needs to be fixed because these if statements will always be true
		// maybe you mean
		/*
		 * if(character == ClueCharacter.MsScarlett)
		 */
		void makeHallPawnVisible(ClueCharacter character, String to, String oldRoom, View view) 
		{
			String PawnIdString = PlayGameUtils.translateToHallwayPawnId(to);
			Log.d(TAG, "PawnId: " + PawnIdString);
			int PawnID = getResources().getIdentifier(PawnIdString, "id", "edu.jhu.ep.butlerdidit");
			ImageView PawnImage = (ImageView) findViewById(PawnID);
			System.out.println("The Pawn ID is " + PawnIdString);
			PawnImage.setVisibility(View.VISIBLE);
			
			ImageView HallPawn = (ImageView) findViewById(R.id.Study_To_Hall);

			if("Ms. Scarlett".equals(ClueCharacter.MsScarlett.getName())){
				HallPawn.setImageResource(R.drawable.scarlett_pawn);
				HallPawn.setVisibility(View.VISIBLE);}			
			if("Col. Mustard".equals(ClueCharacter.ColMustard.getName())){
				HallPawn.setImageResource(R.drawable.mustard_pawn);
				HallPawn.setVisibility(View.VISIBLE);
			}
			if("Mr. Green".equals(ClueCharacter.MrGreen.getName())){
				HallPawn.setImageResource(R.drawable.mr_green_pawn);
				HallPawn.setVisibility(View.VISIBLE);}
			if("Mrs. Peacock".equals(ClueCharacter.MrsPeacock.getName())){
				HallPawn.setImageResource(R.drawable.mrs_peacock_pawn);
				HallPawn.setVisibility(View.VISIBLE);
			}
			if("Mrs. White".equals(ClueCharacter.MrsWhite.getName())){
				HallPawn.setImageResource(R.drawable.mrs_white_pawn);
				HallPawn.setVisibility(View.VISIBLE);
			}
			if("Prof. Plum".equals(ClueCharacter.ProfPlum.getName())){
				HallPawn.setImageResource(R.drawable.plum_pawn);
				HallPawn.setVisibility(View.VISIBLE);
			}
		}
	}

	public void moveToSpace (View view)
	{
		ViewHelpers viewhelp = new ViewHelpers();
		// Storing the domain ID of the space in the tag
		String spaceId = (String) view.getTag();
		if(spaceId == null)
			Log.e(TAG, "No space ID set in View's tag");
		GameBoardSpace space = game.getSpaceById(spaceId);
		
		//Make sure to keep track of the old room to turn it off.
		String oldPlace = fakePlayer.getLocation();
		
		if(game.isPlayerAbleToMoveToSpace(fakePlayer, space)) {
			// Turn on pawn at new location and set new location for player
			Log.d(TAG, String.format("You can move to the %s", spaceId));
			fakePlayer.setLocation(spaceId);
			// Although moving logic is the same, UI code is different for rooms and hallways
			if(space instanceof Room)
				viewhelp.makeRoomPawnVisible(fakePlayer.getClueCharacter(), spaceId, oldPlace, view);
			else
				viewhelp.makeHallPawnVisible(fakePlayer.getClueCharacter(), spaceId, oldPlace, view);
		} else {
			Log.e(TAG, "Player cannot move to this spot! Should not be given the option to");
		}
	
	}
	
	// TODO
	private void updatePawnsWithNewLocations() {
		// foreach player
		for(CluePlayer player : coordinator.getPlayers()) {
			// update pawn
		}
	}
	
	// TODO Implement this for real by showing the user a message somehow
	private void notifyUser(String message) {
		Log.d(TAG, String.format("Notification: %s", message));
	}

	// TODO Finish this stubbed out method and link to UI
	private void endTurn() {
		// Update game server with new state
		coordinator.endTurn();
		ClueMatchState matchState = new ClueMatchState(coordinator);
		GSUpdateMatchModel updateModel = new GSUpdateMatchModel();
		updateModel.setId(currentMatchId);
		updateModel.setStatus("playing");
		updateModel.setMessage(String.format("%s has ended their turn", lpHolder.getLocalPlayerEmail()));
		updateModel.setMatchData(matchState.toJSON());
		gsHelper.updateMatch(updateModel);
	}

	// TODO Finish method by reporting back to the user what should happen now
	@Override
	public void enterNewGame(GSMatch match) {
		coordinator = coordinatorFactory.newGameFromMatch(match);
		if(coordinator.isLocalPlayersTurn()) {
			Log.v(TAG, "New game - it is our turn so do something");
		} else {
			Log.v(TAG, "New game - not our turn so sit tight");
		}
	}

	/**
	 * Update the game state and update the UI to reflect new state
	 * Notify the user whose turn it is (not ours, see takeTurn)
	 */
	@Override
	public void layoutMatch(GSMatch match) {
		coordinator = coordinatorFactory.loadGameFromMatch(match);
		notifyUser(match.getMessage());
		updatePawnsWithNewLocations();
	}

	/**
	 * Update the game state and update the UI to reflect new state
	 * Notify the player that it is their turn
	 */
	@Override
	public void takeTurn(GSMatch match) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Game over, someone won
	 */
	@Override
	public void receiveEndGame(GSMatch match) {
		// TODO Auto-generated method stub
		
	}
}
