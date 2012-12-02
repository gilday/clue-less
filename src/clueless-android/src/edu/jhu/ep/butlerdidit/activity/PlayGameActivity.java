package edu.jhu.ep.butlerdidit.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.jhu.ep.butlerdidit.R;

public class PlayGameActivity extends Activity {

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

}
