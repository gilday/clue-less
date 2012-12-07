package edu.jhu.ep.butlerdidit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import edu.jhu.ep.butlerdidit.R;

public class PlayGameActivity extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_play_game, menu);
		return true;
	}
	
	public void study_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
		System.out.println("You hit study button");
	}
	public void hall_button(View view)
	//lounge button. Let's just see if it registers if I click it.
	{
	    System.out.println("You hit hall button");
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
}
