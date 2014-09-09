package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends FragmentBase{
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		this.mEmailView = (EditText) this.findViewById(R.id.email);
		this.mEmailView.setText(this.mUserName);

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

		this.mLoginFormView = this.findViewById(R.id.login_form);
		this.mLoginStatusView = this.findViewById(R.id.login_status);
		this.mLoginStatusMessageView = (TextView) this.findViewById(R.id.login_status_message);

		this.findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						LoginFragment.this.attemptLogin();
					}
				});
	}

	private View findViewById(int id) {
		return this.getView().findViewById(id);
	}
	
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
					LoginFragment.this.showProgress(false);
					boolean success = result.getIsRightLogin();
					if (success) {
						LoginFragment.this.getActivity().finish();
					} else {
						LoginFragment.this.mPasswordView
								.setError(LoginFragment.this.getString(R.string.error_incorrect_password));
						LoginFragment.this.mPasswordView.requestFocus();
					}
				}
			});
		}
	}

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
						LoginFragment.this.mLoginStatusView.setVisibility(show ? View.VISIBLE
								: View.GONE);
					}
				});

		this.mLoginFormView.setVisibility(View.VISIBLE);
		this.mLoginFormView.animate().setDuration(shortAnimTime)
				.alpha(show ? 0 : 1)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						LoginFragment.this.mLoginFormView.setVisibility(show ? View.GONE
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
