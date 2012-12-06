package edu.jhu.ep.butlerdidit.activity;

import java.util.regex.Pattern;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import edu.jhu.ep.butlerdidit.R;
import edu.jhu.ep.butlerdidit.service.AuthenticationBroadcastReceiver;
import edu.jhu.ep.butlerdidit.service.AuthenticationChangedListener;
import edu.jhu.ep.butlerdidit.service.GameServerMatchService;
import edu.jhu.ep.butlerdidit.service.api.GameServerConstants;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends RoboActivity implements AuthenticationChangedListener {

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

	// UI references.
	@InjectView(R.id.email)					private EditText mEmailView;
	@InjectView(R.id.password)				private EditText mPasswordView;
	@InjectView(R.id.login_form)			private View mLoginFormView;
	@InjectView(R.id.login_status) 			private View mLoginStatusView;
	@InjectView(R.id.login_status_message)	private TextView mLoginStatusMessageView;
	
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
	}
	
	@Override
	public void onResume() {
		super.onResume();
		// Register authentication listener
		authBroadcastReceiver = new AuthenticationBroadcastReceiver(this);
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		lbm.registerReceiver(authBroadcastReceiver, new IntentFilter(GameServerConstants.BROADCAST_AUTHENTICATION_SUCCESS));
		lbm.registerReceiver(authBroadcastReceiver, new IntentFilter(GameServerConstants.BROADCAST_AUTHENTICATION_FAILED));
	}
	
	@Override
	public void onPause() {
		super.onPause();
		// Unregister authentication listener
		authBroadcastReceiver.unRegisterListener(this);
		LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
		lbm.unregisterReceiver(authBroadcastReceiver);
		authBroadcastReceiver = null;
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

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			// Start login
			Intent loginIntent = new Intent(this, GameServerMatchService.class);
			loginIntent.setAction(GameServerConstants.ACTION_LOGIN);
			loginIntent.putExtra(GameServerConstants.PARM_EMAIL, mEmail);
			loginIntent.putExtra(GameServerConstants.PARM_PASSWORD, mPassword);
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
