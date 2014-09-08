package de.ur.mi.android.ting.app.activities;

import javax.inject.Inject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends BaseActivity {

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	@Inject
	public LoginController controller;

	// Values for email and password at the time of the login attempt.
	private String mUserName;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_login);

		// Set up the login form.
		this.mUserName = this.getIntent().getStringExtra(EXTRA_EMAIL);
		this.mEmailView = (EditText) this.findViewById(R.id.email);
		this.mEmailView.setText(this.mUserName);

		this.mPasswordView = (EditText) this.findViewById(R.id.password);
		this.mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							LoginActivity.this.attemptLogin();
							return true;
						}
						return false;
					}
				});

		this.mLoginFormView = this.findViewById(R.id.login_form);
		this.mLoginStatusView = this.findViewById(R.id.login_status);
		this.mLoginStatusMessageView = (TextView) this.findViewById(R.id.login_status_message);

		this.findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LoginActivity.this.attemptLogin();
					}
				});
	}
	
	
	private void showAlertNoInternetConnection() {
		AlertDialog.Builder connectionDialog = new AlertDialog.Builder(this);
		connectionDialog.setTitle(this.getString(R.string.login_error_title));
		connectionDialog.setMessage(this.getString(R.string.login_error_content));
		connectionDialog.setNeutralButton(R.string.button_back, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				LoginActivity.this.finish();
				}
		});
		connectionDialog.setCancelable(true);
		connectionDialog.setIcon(android.R.drawable.ic_dialog_alert);
		connectionDialog.show();
	}
	
	private boolean checkInternetConnection() {
		
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		this.getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
		
		// Reset errors.
		this.mEmailView.setError(null);
		this.mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		this.mUserName = this.mEmailView.getText().toString();
		this.mPassword = this.mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(this.mPassword)) {
			this.mPasswordView.setError(this.getString(R.string.error_field_required));
			focusView = this.mPasswordView;
			cancel = true;
		} else if (this.mPassword.length() < 4) {
			this.mPasswordView.setError(this.getString(R.string.error_invalid_password));
			focusView = this.mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(this.mUserName)) {
			this.mEmailView.setError(this.getString(R.string.error_field_required));
			focusView = this.mEmailView;
			cancel = true;
		} 

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			this.mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			this.showProgress(true);
			this.controller.login(this.mUserName, this.mPassword, new SimpleDoneCallback<LoginResult>() {
				
				@Override
				public void done(LoginResult result) {
					LoginActivity.this.showProgress(false);
					boolean success = result.getIsRightLogin();
					if (success) {
						LoginActivity.this.finish();
					} else {
						LoginActivity.this.mPasswordView
								.setError(LoginActivity.this.getString(R.string.error_incorrect_password));
						LoginActivity.this.mPasswordView.requestFocus();
					}
				}
			});
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
			int shortAnimTime = this.getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			this.mLoginStatusView.setVisibility(View.VISIBLE);
			this.mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							LoginActivity.this.mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			this.mLoginFormView.setVisibility(View.VISIBLE);
			this.mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							LoginActivity.this.mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			this.mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			this.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
