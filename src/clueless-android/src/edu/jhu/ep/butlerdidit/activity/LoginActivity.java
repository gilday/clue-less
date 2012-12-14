package edu.jhu.ep.butlerdidit.activity;

import java.util.regex.Pattern;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.inject.Inject;

import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.service.AuthenticationBroadcastReceiver;
import edu.jhu.ep.butlerdidit.service.AuthenticationChangedListener;
import edu.jhu.ep.butlerdidit.service.GSHttpClient;
import edu.jhu.ep.butlerdidit.service.GSLocalPlayerHolder;
import edu.jhu.ep.butlerdidit.service.GSMatchService;
import edu.jhu.ep.butlerdidit.service.api.GSConstants;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends RoboActivity implements AuthenticationChangedListener {
	
	private static final String PREF_SERVER = "pref_server";
	private static final String PREF_USER = "pref_user";

	private final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
	          "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
	          "\\@" +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
	          "(" +
	          "\\." +
	          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
	          ")+"
	      );

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;
	private String mServerUri;

	// UI references.
	@InjectView(R.id.email)					private EditText mEmailView;
	@InjectView(R.id.password)				private EditText mPasswordView;
	@InjectView(R.id.server)				private EditText mServerUriView;
	@InjectView(R.id.login_form)			private View mLoginFormView;
	@InjectView(R.id.login_status) 			private View mLoginStatusView;
	@InjectView(R.id.login_status_message)	private TextView mLoginStatusMessageView;
	
	@Inject	private GSLocalPlayerHolder localPlayerHolder;
	
	private AuthenticationBroadcastReceiver authBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		
		findViewById(R.id.skip_login_button).setOnClickListener(
				new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// Don't log in, just go to PlayGameActivity
						Intent playGameIntent = new Intent(LoginActivity.this.getApplicationContext(), PlayGameActivity.class);
						LoginActivity.this.startActivity(playGameIntent);
					}
				});
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String user = prefs.getString(PREF_USER, "");
		String serverUri = prefs.getString(PREF_SERVER, "http://10.0.2.2:3000");
		mEmailView.setText(user);
		mServerUriView.setText(serverUri);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// Register authentication listener
		authBroadcastReceiver = new AuthenticationBroadcastReceiver(localPlayerHolder);
		authBroadcastReceiver.registerListener(this);
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		lbm.registerReceiver(authBroadcastReceiver, new IntentFilter(GSConstants.BROADCAST_AUTHENTICATION_SUCCESS));
		lbm.registerReceiver(authBroadcastReceiver, new IntentFilter(GSConstants.BROADCAST_AUTHENTICATION_FAILED));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		// Unregister authentication listener
		authBroadcastReceiver.unregisterListener(this);
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		lbm.unregisterReceiver(authBroadcastReceiver);
		authBroadcastReceiver = null;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor editor = prefs.edit();
		editor.putString(PREF_USER, mEmail);
		editor.putString(PREF_SERVER, mServerUri);
		editor.apply();
	}
	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mServerUri = mServerUriView.getText().toString();
		

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!validateEmailString(mEmail)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		// Check for valid server uri
		if (TextUtils.isEmpty(mServerUri)) {
			mServerUriView.setError(getString(R.string.error_invalid_server));
			focusView = mServerUriView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			GSHttpClient.gameServerEndpoint = mServerUri;
			// Start login
			Intent loginIntent = new Intent(this, GSMatchService.class);
			loginIntent.setAction(GSConstants.ACTION_LOGIN);
			loginIntent.putExtra(GSConstants.PARM_EMAIL, mEmail);
			loginIntent.putExtra(GSConstants.PARM_PASSWORD, mPassword);
			startService(loginIntent);
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
	private boolean validateEmailString(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}

	@Override
	public void onUserAuthenticated(String email) {
		showProgress(false);
		Log.v(LoginActivity.class.getName(), String.format("%s logged in to game server", email));
		
		Intent watchIntent = new Intent(getApplicationContext(), PlayGameActivity.class);
		watchIntent.putExtra(PlayGameActivity.PARM_MATCHID, 1);
		startActivity(watchIntent);
	}

	@Override
	public void onUserLoggedOff() {
		// Not interested
	}

	@Override
	public void onAuthenticationError(String errorMessage) {
		showProgress(false);
		mPasswordView.setError(errorMessage);
		mPasswordView.requestFocus();
	}
}
