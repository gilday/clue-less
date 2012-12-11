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
		void MakePawnVisible(CluePlayer player, String to, View view) 
		{
			String PawnIdString = PlayGameUtils.translateToPawnId(player.getClueCharacter().getName(), to);
			int PawnID = getResources().getIdentifier(PawnIdString, "id", "edu.jhu.ep.butlerdidit");
			ImageView PawnImage = (ImageView) findViewById(PawnID);
			Log.d(TAG, "The Pawn ID is " + PawnIdString);
			PawnImage.setVisibility(View.VISIBLE);
		}
	}

	// TODO Should rename to MoveToSpace since this could be used for hallways
	private void moveToRoom (View view)
	{
		ViewHelpers viewhelp = new ViewHelpers();
		String roomId = PlayGameUtils.roomIdToName(view.getId());
		// translate from ui name to domain name
		roomId = PlayGameUtils.lowCaseRoomtoUpCaseRoom(roomId);
		GameBoardSpace space = game.getSpaceById(roomId);
		Log.d(TAG, space.getSpaceId());
		
		if(game.isPlayerAbleToMoveToSpace(fakePlayer, space))
		{
			Log.d(TAG, "you can move to the room1");
			viewhelp.MakePawnVisible(fakePlayer, roomId, view);
		}
		else if(!game.isPlayerAbleToMoveToSpace(fakePlayer, space))
		{
			Log.d(TAG, "Plese select another room");
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
