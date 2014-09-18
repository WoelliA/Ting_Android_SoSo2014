package de.ur.mi.android.ting.app.fragments;

import javax.inject.Inject;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.LoginController;

public class ForgotPWFragment extends LoginFragmentBase implements OnClickListener {
	private EditText emailView;

	@Inject
	LoginController loginController;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_passwordrestore, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.findViewById(R.id.passwordrestore_button).setOnClickListener(this);
		this.emailView = (EditText)this.findViewById(R.id.email);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.passwordrestore_button:
			String email = this.emailView.getText().toString();
			if (TextUtils.isEmpty(email)) {
				this.emailView.setError(this
						.getString(R.string.error_field_required));
				return;
			}
			if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
				this.emailView.setError(this.getString(R.string.error_invalid_email));
				return;
			}			
			this.loginController.restorePassword(email);			
			break;
		}
		
	}
}
