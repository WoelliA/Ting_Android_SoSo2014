package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import java.util.List;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.controllers.LoginController;
import de.ur.mi.android.ting.app.fragments.ForgotPWFragment;
import de.ur.mi.android.ting.app.fragments.FragmentBase;
import de.ur.mi.android.ting.app.fragments.LoginFragment;
import de.ur.mi.android.ting.app.fragments.RegisterFragment;
import de.ur.mi.android.ting.model.IUserService;
import de.ur.mi.android.ting.model.primitives.LoginResult;
import de.ur.mi.android.ting.utilities.SimpleDoneCallback;


public class LoginActivity extends FragmentActivityBase {
	
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		fillFragemtnList();
		ViewPager pager = (ViewPager) findViewById(R.id.login_pager);
		FragmentManager supportFragmentManager = this.getSupportFragmentManager();
		pager.setAdapter(new LoginFragmentAdapter(supportFragmentManager));
		

	
	}
	
	private void fillFragemtnList (){
		fragmentList.add(new LoginFragment());
		fragmentList.add(new RegisterFragment());
		fragmentList.add(new ForgotPWFragment());
		
	}
	
	private class LoginFragmentAdapter extends FragmentStatePagerAdapter {

		public LoginFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			
			return fragmentList.size();
		}
		
	}

}
