package edu.jhu.ep.butlerdidit.activity;

import java.util.List;
import java.util.Vector;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.os.Bundle;
import android.util.Log;
import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinator;
import edu.jhu.ep.butlerdidit.domain.ClueGameCoordinatorFactory;
import edu.jhu.ep.butlerdidit.domain.CluePlayer;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.ClueMatchState;
import edu.jhu.ep.butlerdidit.domain.Room;
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

	// TODO Finish this stubbed out method and link to UI
	private void endTurn() {
		// Update game server with new state
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

	@Override
	public void layoutMatch(GSMatch match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void takeTurn(GSMatch match) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveEndGame(GSMatch match) {
		// TODO Auto-generated method stub
		
	}
}
