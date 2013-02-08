package com.projects.codeblue;

import com.codeblue.geolocation.CodeBlueCurrentLocation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CodeBluePagerAdapter extends FragmentPagerAdapter{

	public CodeBluePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Fragment resultFragment = null;
		switch (position) {
		case 4:
			resultFragment = new CodeBlueCurrentLocation();
			break;

		default:
			break;
		}
		
		return resultFragment;
	}

	@Override
	public int getCount() {
		return 0;
	}

}
