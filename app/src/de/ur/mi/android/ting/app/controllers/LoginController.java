package de.ur.mi.android.ting.app.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.IChangeListener;
import de.ur.mi.android.ting.app.fragments.Gender;
import de.ur.mi.android.ting.app.fragments.RegisterRequest;
import de.ur.mi.android.ting.app.fragments.Service;
import de.ur.mi.android.ting.app.fragments.ServiceLoginResultType;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.LocalUser;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.IConnectivity;
import de.ur.mi.android.ting.utilities.IDoneCallback;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;
import de.ur.mi.android.ting.utilities.view.Notify;
import de.ur.mi.android.ting.utilities.view.Notify.LoadingContext;

public class LoginController implements IChangeListener<LoginResult> {

	private IUserService userService;
	private IConnectivity connectivity;
	private LocalUser user;

	public LoginController(IUserService userService,
			IConnectivity connectivity, LocalUser user) {
		this.userService = userService;
		this.connectivity = connectivity;
		this.user = user;
		this.user.addLoginChangeListener(this);
	}

	public void login(String userName, String password,
			final IDoneCallback<LoginResult> callback) {
		if (!this.connectivity.hasWebAccess(true)) {
			callback.fail(new NoInternetException());
		}
		final LoadingContext loading = Notify.current().showLoading(
				R.string.login_progress_signing_in);
		this.userService.login(userName, password,
				new SimpleDoneCallback<LoginResult>() {

					@Override
					public void done(LoginResult result) {
						loading.close();
						callback.done(result);
					}
				});
	}

	public boolean checkIsLoggedIn() {
		return this.userService.checkIsLoggedIn();
	}

	@Override
	public void onChange(LoginResult loginres) {
		if (loginres.getIsRightLogin()) {
			Notify.current().showToast("Welcome " + this.user.getName());
		} else {
			Notify.current().showToast(R.string.logged_out);
		}
	}

	public void logout() {
		this.userService.logout();
		this.user.setIsLoggedIn(false);
	}

	public void register(RegisterRequest registerRequest,
			final SimpleDoneCallback<Boolean> callback) {
		if (!this.connectivity.hasWebAccess(true)) {
			callback.fail(new NoInternetException());
		}
		final LoadingContext showLoading = Notify.current().showLoading(
				R.string.registering);
		this.userService.register(registerRequest,
				new SimpleDoneCallback<Boolean>() {

					@Override
					public void done(Boolean result) {
						showLoading.close();
						callback.done(result);
					}

					@Override
					public void fail(Exception e) {
						showLoading.close();
						Notify.current().showToast(e.getMessage());
					}

				});
	}

	public void getGenders(IDoneCallback<Collection<Gender>> callback) {

		List<Gender> genderList = new ArrayList<Gender>();
		genderList.add(new Gender("4aA196ySIV", "Transgender"));
		genderList.add(new Gender("7uUFa5pgwW", "Female"));
		genderList.add(new Gender("vUjCdGSa2k", "Male"));
		callback.done(genderList);

	}

	public void loginThirdParty(Service service,
			Activity activity, IDoneCallback<ServiceLoginResultType> callback) {
		if (!this.connectivity.hasWebAccess(true)) {
		}
		this.userService.loginThirdParty(service, activity, callback);
	}
}
