package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import com.parse.ParseFacebookUtils;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.activities.Constants;
import de.ur.mi.android.ting.app.activities.EditProfileActivity;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends LoginFragmentBase implements OnClickListener {

	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	@Inject
	public LoginController controller;

	// Values for email and password at the time of the login attempt.
	private String mUserName;
	private String mPassword;

	// UI references.
	private EditText mUsernameView;
	private EditText mPasswordView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		this.mUsernameView = (EditText) this.findViewById(R.id.username);
		this.mUsernameView.setText(this.mUserName);
		Button registerButton = (Button) this
				.findViewById(R.id.action_register);
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginFragment.this.handler.showRegister();

			}
		});

		Button forgotPWButton = (Button) this
				.findViewById(R.id.action_forgot_password);
		forgotPWButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LoginFragment.this.handler.showForgotPW();

			}
		});

		this.mPasswordView = (EditText) this.findViewById(R.id.password);
		this.mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							LoginFragment.this.attemptLogin();
							return true;
						}
						return false;
					}
				});

		this.findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LoginFragment.this.attemptLogin();
					}
				});

		this.findViewById(R.id.action_twitter_login).setOnClickListener(this);
		this.findViewById(R.id.action_facebook_login).setOnClickListener(this);
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.action_facebook_login: 
			this.controller.loginThirdParty(Service.Facebook, this.getActivity());
			break;
		case R.id.action_twitter_login:
			this.controller.loginThirdParty(Service.Twitter, this.getActivity());
			break;
		} 		
	}
	
	public void attemptLogin() {

		// Reset errors.
		this.mUsernameView.setError(null);
		this.mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		this.mUserName = this.mUsernameView.getText().toString();
		this.mPassword = this.mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(this.mPassword)) {
			this.mPasswordView.setError(this
					.getString(R.string.error_field_required));
			focusView = this.mPasswordView;
			cancel = true;
		} else if (this.mPassword.length() < 4) {
			this.mPasswordView.setError(this
					.getString(R.string.error_invalid_password));
			focusView = this.mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(this.mUserName)) {
			this.mUsernameView.setError(this
					.getString(R.string.error_field_required));
			focusView = this.mUsernameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			this.controller.login(this.mUserName, this.mPassword,
					new SimpleDoneCallback<LoginResult>() {

						@Override
						public void done(LoginResult result) {

							boolean success = result.getIsRightLogin();
							if (success) {
								LoginFragment.this.getActivity().finish();
							} else {
								LoginFragment.this.mPasswordView.setError(LoginFragment.this
										.getString(R.string.error_incorrect_password));
								LoginFragment.this.mPasswordView.requestFocus();
							}
						}
					});
		}
	}


}
