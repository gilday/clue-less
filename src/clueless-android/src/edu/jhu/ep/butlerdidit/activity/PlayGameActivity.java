package edu.jhu.ep.butlerdidit.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinatorFactory;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.HallwaySpace;
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
	private boolean isPlayersHandDisplayed = false;
	private ClueGameCoordinator coordinator;
	
	private ViewHelpers viewHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		viewHelper = new ViewHelpers();
		
		currentMatchId = getIntent().getIntExtra(PARM_MATCHID, 0);
		// If the intent that starts this activity has not sent a match ID to use,
		if(currentMatchId <= 0) {
			// Then use the test data
			coordinator = coordinatorFactory.coordinatorForTesting();
			lpHolder.setLocalPlayerEmail(coordinator.getCurrentPlayer().getGamePlayer().getEmail());
			displayLocalPlayersHand();
			
			return;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// if not test mode
		if(currentMatchId > 0) {
			gsHelper.setGameServerMatchListener(this);
			gsHelper.setCurrentMatchById(currentMatchId);
			gsHelper.startWatchingMatch();
		}
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		if(gsHelper != null) {
			gsHelper.stopWatchingMatch();
		}
	}
	
	class ViewHelpers 
	{
		void changePawnVisibility(ClueCharacter character, GameBoardSpace space, boolean visible) {
			if(space instanceof Room)
				changeRoomPawnVisibility(character, (Room)space, visible);
			else if(space instanceof HallwaySpace)
				changeHallPawnVisibility(character, (HallwaySpace)space, visible);
		}
		
		/**
		 * This method will loop through the players' locations and show or hide their pawns 
		 * This is called when we get a new match state from the server with new locations. 
		 * Will hide all the old locations then show all the new
		 */
		void changeAllPawnVisibility(boolean visible) {
			// foreach player
			for(CluePlayer player : coordinator.getPlayers()) {
				// update pawn
				GameBoardSpace space = coordinator.getGameBoard().getSpaceById(player.getLocation());
				viewHelper.changePawnVisibility(player.getClueCharacter(), space, visible);
			}
		}
		
		/**
		 * Just deals with UI. Unhides and hides space for pawn
		 * @param from ID of space to move from e.g. "Study"
		 * @param roomId ID of space to move pawn to e.g. "Hall"
		 */
		private void changeRoomPawnVisibility(ClueCharacter character, Room room, boolean visible) 
		{
			String pawnIdString = PlayGameUtils.translateToRoomPawnId(character.getName(), room.getSpaceId());
			int pawnID = getResources().getIdentifier(pawnIdString, "id", "edu.jhu.ep.butlerdidit");
			ImageView pawnImage = (ImageView) findViewById(pawnID);
			Log.d(TAG, "The pawnIdString:" + pawnIdString + "PawnID: " + pawnID);
			int visibility = visible ? View.VISIBLE : View.INVISIBLE;
			pawnImage.setVisibility(visibility);

		}
		
		private void changeHallPawnVisibility(ClueCharacter character, HallwaySpace hallway, boolean visible) 
		{
			String pawnIdString = PlayGameUtils.translateToHallwayPawnId(character.getName());
			String hallwayString = PlayGameUtils.translateToHallwayId(hallway.getSpaceId());
			Log.d(TAG, "PawnId: " + pawnIdString + "HallwayString: " + hallwayString);
			int pawnID = getResources().getIdentifier(pawnIdString, "drawable", "edu.jhu.ep.butlerdidit");
			int hallwayID = getResources().getIdentifier(hallwayString,"id","edu.jhu.ep.butlerdidit");
			Log.d(TAG, "PawnID: " + pawnID + " and the Hallway is " + hallway.getSpaceId());
			ImageView hallPawn = (ImageView) findViewById(hallwayID);
			hallPawn.setImageResource(pawnID);
			int visibility = visible ? View.VISIBLE : View.INVISIBLE;
			hallPawn.setVisibility(visibility);
		}			
	}

	public void moveToSpaceButtonHandler (View view)
	{
		GameBoard game = coordinator.getGameBoard();
		CluePlayer localPlayer = coordinator.getLocalPlayer();
		// Storing the domain ID of the space in the tag
		String spaceId = (String) view.getTag();
		if(spaceId == null)
			Log.e(TAG, "No space ID set in View's tag");
		GameBoardSpace space = game.getSpaceById(spaceId);
		
		if(game.isPlayerAbleToMoveToSpace(localPlayer, space)) {
			// Turn on pawn at new location and set new location for player
			Log.d(TAG, String.format("You can move to the %s", spaceId));
			// Although moving logic is the same, UI code is different for rooms and hallways
			GameBoardSpace oldSpace = game.getSpaceById(localPlayer.getLocation());
			viewHelper.changePawnVisibility(localPlayer.getClueCharacter(), oldSpace, false);
			
			localPlayer.setLocation(spaceId);
			
			viewHelper.changePawnVisibility(localPlayer.getClueCharacter(), space, true);
		} else {
			Log.e(TAG, "Player cannot move to this spot! Should not be given the option to");
		}
	}
	
	private void notifyUser(String message) {
		Log.d(TAG, String.format("Notification: %s", message));
		// There's not much more room for an additional widget on the screen so I'm just 
		// going to update the activity title. We could make PlayGameActivity a fullscreen 
		// activity to free up some more room but this messes up the pawn and game board 
		// space placement
		setTitle(message);
	}
	
	private void displayLocalPlayersHand() {
		
		if(!isPlayersHandDisplayed) {
			// will need a counter in addition to the foreach to find car placeholder images in the layout
			int i = 1;
			for(ClueCard card : coordinator.getLocalPlayer().getHand()) {
				String cardPlaceHolder = "card" + i;
				// Fetch the ID of the image resource associated with this card
				int cardID = getResources().getIdentifier(card.getCard(), "drawable", "edu.jhu.ep.butlerdidit");
				// Fetch the ID of the card ImageView that we are replacing with the real cards.
				int cardPlaceHolderID = getResources().getIdentifier(cardPlaceHolder, "id", "edu.jhu.ep.butlerdidit");
				ImageView cardLocation = (ImageView) findViewById(cardPlaceHolderID);
				cardLocation.setImageResource(cardID);
				i++;
			}
			isPlayersHandDisplayed = true;
		}
	}

	public void endTurnButtonHandler(View view) {
		Gson gson = new Gson();
		// Update game server with new state
		String lastCharacter = coordinator.getCurrentPlayer().getClueCharacter().getName();
		coordinator.endTurn();
		ClueMatchState matchState = new ClueMatchState(coordinator);
		GSUpdateMatchModel updateModel = new GSUpdateMatchModel();
		updateModel.setId(currentMatchId);
		updateModel.setCurrentPlayer(coordinator.getCurrentPlayer().getGamePlayer().getEmail());
		updateModel.setStatus("playing");
		updateModel.setMessage(String.format("%s is done. %s's turn", lastCharacter, coordinator.getCurrentPlayer().getClueCharacter().getName()));
		updateModel.setMatchData(gson.toJsonTree(matchState));
		gsHelper.updateMatch(updateModel);
	}

	@Override
	public void enterNewGame(GSMatch match) {
		coordinator = coordinatorFactory.newGameFromMatch(match);
		if(coordinator.isLocalPlayersTurn()) {
			notifyUser(String.format("Welcome, %s! It's your turn", coordinator.getCurrentPlayer().getClueCharacter().getName()));
		} else {
			notifyUser(String.format("Welcome, %s! It's %s's turn", coordinator.getLocalPlayer().getClueCharacter().getName(), coordinator.getCurrentPlayer().getClueCharacter().getName()));
		}
		displayLocalPlayersHand();
	}

	/**
	 * Update the game state and update the UI to reflect new state
	 * Notify the user whose turn it is (not ours, see takeTurn)
	 */
	@Override
	public void layoutMatch(GSMatch match) {
		// Hide all pawns
		if(coordinator != null)
			viewHelper.changeAllPawnVisibility(false);
		coordinator = coordinatorFactory.loadGameFromMatch(match);
		viewHelper.changeAllPawnVisibility(true);
		
		notifyUser(match.getMessage());
		displayLocalPlayersHand();
	}

	/**
	 * Update the game state and update the UI to reflect new state
	 * Notify the player that it is their turn
	 */
	@Override
	public void takeTurn(GSMatch match) {
		// Careful, coordinator could be null right now if this is a new activity
		if(coordinator != null) 
			viewHelper.changeAllPawnVisibility(false);
		coordinator = coordinatorFactory.loadGameFromMatch(match);
		viewHelper.changeAllPawnVisibility(true);
		
		notifyUser(match.getMessage());
		displayLocalPlayersHand();
	}

	/**
	 * Game over, someone won
	 */
	@Override
	public void receiveEndGame(GSMatch match) {
		// TODO Auto-generated method stub
		
	}
}
