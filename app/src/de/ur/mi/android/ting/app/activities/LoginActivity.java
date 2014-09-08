package de.ur.mi.android.ting.app.activities;

import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import de.ur.mi.android.ting.R;
import de.ur.mi.android.ting.app.fragments.ForgotPWFragment;
import de.ur.mi.android.ting.app.fragments.LoginFragment;
import de.ur.mi.android.ting.app.fragments.RegisterFragment;


public class LoginActivity extends FragmentActivityBase {
	
	private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		this.fillFragemtnList();
		ViewPager pager = (ViewPager) this.findViewById(R.id.login_pager);
		FragmentManager supportFragmentManager = this.getSupportFragmentManager();
		pager.setAdapter(new LoginFragmentAdapter(supportFragmentManager));
		

	
	}
	
	private void fillFragemtnList (){
		this.fragmentList.add(new LoginFragment());
		this.fragmentList.add(new RegisterFragment());
		this.fragmentList.add(new ForgotPWFragment());
		
	}
	
	private class LoginFragmentAdapter extends FragmentStatePagerAdapter {

		public LoginFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
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

}
