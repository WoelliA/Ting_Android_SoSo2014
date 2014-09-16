package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;

import com.parse.ParseFacebookUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.fragments.ForgotPWFragment;
import de.ur.mi.android.ting.app.fragments.LoginFragment;
import de.ur.mi.android.ting.app.fragments.LoginFragmentBase;
import de.ur.mi.android.ting.app.fragments.RegisterFragment;


public class LoginActivity extends BaseActivity implements ILoginHandler {
	
	private ArrayList<LoginFragmentBase> fragmentList = new ArrayList<LoginFragmentBase>();
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		this.fillFragemtnList();
		this.pager = (ViewPager) this.findViewById(R.id.login_pager);
		FragmentManager supportFragmentManager = this.getSupportFragmentManager();
		this.pager.setAdapter(new LoginFragmentAdapter(supportFragmentManager));
		

	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onResume() {
		this.injectView();
		super.onResume();
	}
	
	
	private void injectView() {
		for (LoginFragmentBase fragment : this.fragmentList) {
			fragment.setHandler(this);
		}
		
	}

	private void fillFragemtnList (){
		this.fragmentList.add(new LoginFragment());
		this.fragmentList.add(new RegisterFragment());
		this.fragmentList.add(new ForgotPWFragment());
		
	}
	
	private class LoginFragmentAdapter extends FragmentStatePagerAdapter {

		public LoginFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return LoginActivity.this.fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			
			return LoginActivity.this.fragmentList.size();
		}
		
	}

	@Override
	public void showRegister() {
		this.pager.setCurrentItem(1,true);
		
	}

	@Override
	public void showForgotPW() {
		this.pager.setCurrentItem(2, true);
		
	}

}
