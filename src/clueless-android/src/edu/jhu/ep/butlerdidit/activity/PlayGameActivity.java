package edu.jhu.ep.butlerdidit.activity;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.domain.ClueCharacter;
import edu.jhu.ep.butlerdidit.domain.GameBoard;
import edu.jhu.ep.butlerdidit.domain.GameBoardSpace;
import edu.jhu.ep.butlerdidit.domain.Player;

public class PlayGameActivity extends Activity 
{

	//Button Lounge_Button = (Button) findViewById(R.id.button1);
	GameBoard game;
	Player player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		player = new Player();
		List<Player> players = new Vector<Player>();
		players.add(player);
		game = new GameBoard(players);
		player.setLocation("Library-Study");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
		
		//Make characters in the study room invisible.
		ImageView scarlett_study = (ImageView) findViewById(R.id.scarlett_study);
		scarlett_study.setVisibility(View.INVISIBLE);
		ImageView plum_study = (ImageView) findViewById(R.id.plum_study);
		plum_study.setVisibility(View.INVISIBLE);
		ImageView peacock_study = (ImageView) findViewById(R.id.peacock_study);
		peacock_study.setVisibility(View.INVISIBLE);
		ImageView mustard_study = (ImageView) findViewById(R.id.mustard_study);
		mustard_study.setVisibility(View.INVISIBLE);
		ImageView white_study = (ImageView) findViewById(R.id.white_study);
		white_study.setVisibility(View.INVISIBLE);
		ImageView green_study = (ImageView) findViewById(R.id.green_study);
		green_study.setVisibility(View.INVISIBLE);
		
		//Make weapons in the study room invisible.
		ImageView rope_study = (ImageView) findViewById(R.id.rope_study);
		rope_study.setVisibility(View.INVISIBLE);
		ImageView revolver_study = (ImageView) findViewById(R.id.revolver_study);
		revolver_study.setVisibility(View.INVISIBLE);
		ImageView wrench_study = (ImageView) findViewById(R.id.wrench_study);
		wrench_study.setVisibility(View.INVISIBLE);
		ImageView candlestick_study = (ImageView) findViewById(R.id.candlestick_study);
		candlestick_study.setVisibility(View.INVISIBLE);
		ImageView knife_study = (ImageView) findViewById(R.id.knife_study);
		knife_study.setVisibility(View.INVISIBLE);
		ImageView leadpipe_study = (ImageView) findViewById(R.id.leadpipe_study);
		leadpipe_study.setVisibility(View.INVISIBLE);
		
		//Make characters in the hall room invisible.
		ImageView scarlett_hall = (ImageView) findViewById(R.id.scarlett_hall);
		scarlett_hall.setVisibility(View.INVISIBLE);
		ImageView plum_hall = (ImageView) findViewById(R.id.plum_hall);
		plum_hall.setVisibility(View.INVISIBLE);
		ImageView peacock_hall = (ImageView) findViewById(R.id.peacock_hall);
		peacock_hall.setVisibility(View.INVISIBLE);
		ImageView mustard_hall = (ImageView) findViewById(R.id.mustard_hall);
		mustard_hall.setVisibility(View.INVISIBLE);
		ImageView white_hall = (ImageView) findViewById(R.id.white_hall);
		white_hall.setVisibility(View.INVISIBLE);
		ImageView green_hall = (ImageView) findViewById(R.id.green_hall);
		green_hall.setVisibility(View.INVISIBLE);
		
		//Make weapons in the hall room invisible.
		ImageView rope_hall = (ImageView) findViewById(R.id.rope_hall);
		rope_hall.setVisibility(View.INVISIBLE);
		ImageView revolver_hall = (ImageView) findViewById(R.id.revolver_hall);
		revolver_hall.setVisibility(View.INVISIBLE);
		ImageView wrench_hall = (ImageView) findViewById(R.id.wrench_hall);
		wrench_hall.setVisibility(View.INVISIBLE);
		ImageView candlestick_hall = (ImageView) findViewById(R.id.candlestick_hall);
		candlestick_hall.setVisibility(View.INVISIBLE);
		ImageView knife_hall = (ImageView) findViewById(R.id.knife_hall);
		knife_hall.setVisibility(View.INVISIBLE);
		ImageView leadpipe_hall = (ImageView) findViewById(R.id.leadpipe_hall);
		leadpipe_hall.setVisibility(View.INVISIBLE);
		
		//Make characters in the lounge room invisible.
		ImageView scarlett_lounge = (ImageView) findViewById(R.id.scarlett_lounge);
		scarlett_lounge.setVisibility(View.INVISIBLE);
		ImageView plum_lounge = (ImageView) findViewById(R.id.plum_lounge);
		plum_lounge.setVisibility(View.INVISIBLE);
		ImageView peacock_lounge = (ImageView) findViewById(R.id.peacock_lounge);
		peacock_lounge.setVisibility(View.INVISIBLE);
		ImageView mustard_lounge = (ImageView) findViewById(R.id.mustard_lounge);
		mustard_lounge.setVisibility(View.INVISIBLE);
		ImageView white_lounge = (ImageView) findViewById(R.id.white_lounge);
		white_lounge.setVisibility(View.INVISIBLE);
		ImageView green_lounge = (ImageView) findViewById(R.id.green_lounge);
		green_lounge.setVisibility(View.INVISIBLE);
		
		//Make weapons in the lounge room invisible.
		ImageView rope_lounge = (ImageView) findViewById(R.id.rope_lounge);
		rope_lounge.setVisibility(View.INVISIBLE);
		ImageView revolver_lounge = (ImageView) findViewById(R.id.revolver_lounge);
		revolver_lounge.setVisibility(View.INVISIBLE);
		ImageView wrench_lounge = (ImageView) findViewById(R.id.wrench_lounge);
		wrench_lounge.setVisibility(View.INVISIBLE);
		ImageView candlestick_lounge = (ImageView) findViewById(R.id.candlestick_lounge);
		candlestick_lounge.setVisibility(View.INVISIBLE);
		ImageView knife_lounge = (ImageView) findViewById(R.id.knife_lounge);
		knife_lounge.setVisibility(View.INVISIBLE);
		ImageView leadpipe_lounge = (ImageView) findViewById(R.id.leadpipe_lounge);
		leadpipe_lounge.setVisibility(View.INVISIBLE);
		
		//Make characters in the library room invisible.
		ImageView scarlett_library = (ImageView) findViewById(R.id.scarlett_library);
		scarlett_library.setVisibility(View.INVISIBLE);
		ImageView plum_library = (ImageView) findViewById(R.id.plum_library);
		plum_library.setVisibility(View.INVISIBLE);
		ImageView peacock_library = (ImageView) findViewById(R.id.peacock_library);
		peacock_library.setVisibility(View.INVISIBLE);
		ImageView mustard_library = (ImageView) findViewById(R.id.mustard_library);
		mustard_library.setVisibility(View.INVISIBLE);
		ImageView white_library = (ImageView) findViewById(R.id.white_library);
		white_library.setVisibility(View.INVISIBLE);
		ImageView green_library = (ImageView) findViewById(R.id.green_library);
		green_library.setVisibility(View.INVISIBLE);
		
		//Make weapons in the library room invisible.
		ImageView rope_library = (ImageView) findViewById(R.id.rope_library);
		rope_library.setVisibility(View.INVISIBLE);
		ImageView revolver_library = (ImageView) findViewById(R.id.revolver_library);
		revolver_library.setVisibility(View.INVISIBLE);
		ImageView wrench_library = (ImageView) findViewById(R.id.wrench_library);
		wrench_library.setVisibility(View.INVISIBLE);
		ImageView candlestick_library = (ImageView) findViewById(R.id.candlestick_library);
		candlestick_library.setVisibility(View.INVISIBLE);
		ImageView knife_library = (ImageView) findViewById(R.id.knife_library);
		knife_library.setVisibility(View.INVISIBLE);
		ImageView leadpipe_library = (ImageView) findViewById(R.id.leadpipe_library);
		leadpipe_library.setVisibility(View.INVISIBLE);
		
		//Make characters in the billiard room invisible.
		ImageView scarlett_billiard = (ImageView) findViewById(R.id.scarlett_billiard);
		scarlett_billiard.setVisibility(View.INVISIBLE);
		ImageView plum_billiard = (ImageView) findViewById(R.id.plum_billiard);
		plum_billiard.setVisibility(View.INVISIBLE);
		ImageView peacock_billiard = (ImageView) findViewById(R.id.peacock_billiard);
		peacock_billiard.setVisibility(View.INVISIBLE);
		ImageView mustard_billiard = (ImageView) findViewById(R.id.mustard_billiard);
		mustard_billiard.setVisibility(View.INVISIBLE);
		ImageView white_billiard = (ImageView) findViewById(R.id.white_billiard);
		white_billiard.setVisibility(View.INVISIBLE);
		ImageView green_billiard = (ImageView) findViewById(R.id.green_billiard);
		green_billiard.setVisibility(View.INVISIBLE);
		
		//Make weapons in the billiard room invisible.
		ImageView rope_billiard = (ImageView) findViewById(R.id.rope_billiard);
		rope_billiard.setVisibility(View.INVISIBLE);
		ImageView revolver_billiard = (ImageView) findViewById(R.id.revolver_billiard);
		revolver_billiard.setVisibility(View.INVISIBLE);
		ImageView wrench_billiard = (ImageView) findViewById(R.id.wrench_billiard);
		wrench_billiard.setVisibility(View.INVISIBLE);
		ImageView candlestick_billiard = (ImageView) findViewById(R.id.candlestick_billiard);
		candlestick_billiard.setVisibility(View.INVISIBLE);
		ImageView knife_billiard = (ImageView) findViewById(R.id.knife_billiard);
		knife_billiard.setVisibility(View.INVISIBLE);
		ImageView leadpipe_billiard = (ImageView) findViewById(R.id.leadpipe_billiard);
		leadpipe_billiard.setVisibility(View.INVISIBLE);
		
		//Make characters in the dining room invisible.
		ImageView scarlett_dining = (ImageView) findViewById(R.id.scarlett_dining);
		scarlett_dining.setVisibility(View.INVISIBLE);
		ImageView plum_dining = (ImageView) findViewById(R.id.plum_dining);
		plum_dining.setVisibility(View.INVISIBLE);
		ImageView peacock_dining = (ImageView) findViewById(R.id.peacock_dining);
		peacock_dining.setVisibility(View.INVISIBLE);
		ImageView mustard_dining = (ImageView) findViewById(R.id.mustard_dining);
		mustard_dining.setVisibility(View.INVISIBLE);
		ImageView white_dining = (ImageView) findViewById(R.id.white_dining);
		white_dining.setVisibility(View.INVISIBLE);
		ImageView green_dining = (ImageView) findViewById(R.id.green_dining);
		green_dining.setVisibility(View.INVISIBLE);
		
		//Make weapons in the dining room invisible.
		ImageView rope_dining = (ImageView) findViewById(R.id.rope_dining);
		rope_dining.setVisibility(View.INVISIBLE);
		ImageView revolver_dining = (ImageView) findViewById(R.id.revolver_dining);
		revolver_dining.setVisibility(View.INVISIBLE);
		ImageView wrench_dining = (ImageView) findViewById(R.id.wrench_dining);
		wrench_dining.setVisibility(View.INVISIBLE);
		ImageView candlestick_dining = (ImageView) findViewById(R.id.candlestick_dining);
		candlestick_dining.setVisibility(View.INVISIBLE);
		ImageView knife_dining = (ImageView) findViewById(R.id.knife_dining);
		knife_dining.setVisibility(View.INVISIBLE);
		ImageView leadpipe_dining = (ImageView) findViewById(R.id.leadpipe_dining);
		leadpipe_dining.setVisibility(View.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_play_game, menu);
		return true;
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
	
	public void UpdatePlayerLocationOnBoard(Player player)
	{
		String MsScarletID = "Ms. Scarlet";
		String ColMustardID = "Col. Mustard";
		String MrsWhiteID = "Mrs. White";
		String MrGreenID = "Mr. Green";
		String MrsPeacockID = "Mrs. Peacock";
		String ProfPlumID = "Prof. Plum"; 
	   
		if(MsScarletID.equals(player.getPlayerId()))
		{
		}
		else if(ColMustardID.equals(player.getPlayerId()))
		{
		}
		else if(MrsWhiteID.equals(player.getPlayerId()))
		{
		}
		else if(MrGreenID.equals(player.getPlayerId()))
		{
		}
		else if(MrsPeacockID.equals(player.getPlayerId()))
		{
		}
		else if(ProfPlumID.equals(player.getPlayerId()))
		{
		}
	}

	public void study_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
		String study = "study";
		
		for(GameBoardSpace space : game.getPossibleMoves(player))
		{
			if(study.equals(space.getSpaceId()))
			{
				System.out.println("Congrats! You can enter hall room");
			}
		}
	}
	
	public void hall_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	}
	public void lounge_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit lounge button");
	}
	public void library_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit library button");
	}
	public void billiard_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit billiard button");
	}
	public void dining_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit dining button");
	}
	public void conservatory_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit conservatory button");
	}
	public void ballroom_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit ballroom button");
	}
	public void kitchen_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit kitchen button");
	}
	public void LoungeToHall(View view)
	{
		System.out.println("You Hit Lounge to Hall hallway");
	}
	public void HallTohall (View view)
	{
		System.out.println("You Hit Hall to hall hallway");
	}
	public void StudyToLibrary (View view)
	{
		System.out.println("You Hit Study to Library hallway");
	}
	public void HallToBilliard (View view)
	{
		System.out.println("You Hit hall to billiard hallway");
	}
	public void LoungeToDining (View view)
	{
		System.out.println("You Hit Lounge to Dining hallway");
	}
	public void BilliardToLibrary (View view)
	{
		System.out.println("You Hit Billiard to Library hallway");
	}
	public void DiningToBilliard (View view)
	{
		System.out.println("You Hit Dining to Billiard hallway");
	}
	public void LibraryToConservatory(View view)
	{
		System.out.println("You Hit Library To Conservatory hallway");
	}
	public void BilliardToBallroom (View view)
	{
		System.out.println("You Hit Billiard to Ballroom hallway");
	}
	public void DiningToKitchen (View view)
	{
		System.out.println("You Hit Dining to Kitchen hallway");
	}
	public void BallroomToConservatory (View view)
	{
		System.out.println("You Hit Ballroom to Conservatory hallway");
	}
	public void KitchenToBallroom (View view)
	{
		System.out.println("You Hit Kitchen to Ballroom hallway");
	}
}
