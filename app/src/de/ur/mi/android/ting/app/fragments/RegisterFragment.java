package de.ur.mi.android.ting.app.fragments;

import java.util.Collection;

import javax.inject.Inject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.adapters.ViewCreationDelegatingListAdapter;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.ViewResolver;

public class RegisterFragment extends LoginFragmentBase {
	@Inject
	public LoginController controller;
	private EditText emailView;
	private EditText passwordView;
	private EditText userNameView;
	private ViewCreationDelegatingListAdapter<Gender> adapter;
	private Spinner spinner;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.controller.setContext(this.getActivity());
		Button registerButton = (Button) this
				.findViewById(R.id.action_register);
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String userName = RegisterFragment.this.userNameView.getText()
						.toString();
				String email = RegisterFragment.this.emailView.getText()
						.toString();
				String password = RegisterFragment.this.passwordView.getText()
						.toString();
				Gender gender = (Gender) RegisterFragment.this.spinner
						.getSelectedItem();

				if (userName.trim().length() == 0) {
					RegisterFragment.this.userNameView
							.setError(RegisterFragment.this.getActivity()
									.getString(R.string.error_field_required));
					return;
				}
				if (password.trim().length() <= 6) {
					RegisterFragment.this.passwordView
							.setError(RegisterFragment.this.getActivity()
									.getString(R.string.error_invalid_password));
					return;
				}
				
				if(!TextUtils.isEmpty(email)){
					if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
						RegisterFragment.this.emailView.setError(RegisterFragment.this.getString(R.string.error_invalid_email));
						return;
					}
				}				
				
				RegisterFragment.this.controller.register(new RegisterRequest(
						userName, email, password, gender),
						new SimpleDoneCallback<Boolean>() {

							@Override
							public void done(Boolean result) {
								if (result) {
									RegisterFragment.this.getActivity()
											.finish();
								}
							}
						});
			}
		});
		this.userNameView = (EditText) this.findViewById(R.id.username);
		this.emailView = (EditText) this.findViewById(R.id.email);
		this.passwordView = (EditText) this.findViewById(R.id.password);
		this.initSpinner();
	}

	private void initSpinner() {
		this.spinner = (Spinner) this.findViewById(R.id.genderSelection);
		ViewResolver<Gender> resolver = new ViewResolver<Gender>(
				android.R.layout.simple_list_item_1, this.getActivity()) {

			@Override
			protected void decorateView(View view, Gender gender,
					ViewGroup parent) {
				TextView item1 = (TextView) this.findViewById(view,
						android.R.id.text1);
				item1.setText(gender.getName());
			}
		};
		this.adapter = new ViewCreationDelegatingListAdapter<Gender>(
				this.getActivity(), resolver);
		this.spinner.setAdapter(this.adapter);
		this.controller
				.getGenders(new SimpleDoneCallback<Collection<Gender>>() {

					@Override
					public void done(Collection<Gender> result) {
						if (result != null) {
							RegisterFragment.this.adapter.add(new Gender(null,
									"Please select gender"));
							RegisterFragment.this.adapter.addAll(result);
						}

					}
				});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_register, container, false);
	}

}
