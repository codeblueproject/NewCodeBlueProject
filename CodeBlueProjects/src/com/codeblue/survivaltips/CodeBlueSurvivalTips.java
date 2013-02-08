package com.codeblue.survivaltips;

import com.actionbarsherlock.app.SherlockFragment;
import com.projects.codeblue.CodeBluePagerAdapter;
import com.projects.codeblue.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CodeBlueSurvivalTips extends SherlockFragment {

	private CodeBluePagerAdapter cbPager;
	private ViewPager viewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_survivaltips, container,
				false);

		cbPager = new CodeBluePagerAdapter(getFragmentManager());
		viewPager = (ViewPager) view.findViewById(R.id.pager);
		viewPager.setAdapter(cbPager);

		return view;
	}
}
