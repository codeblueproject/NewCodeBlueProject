package com.projects.codeblue;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.codeblue.bluetooth.CodeBlueBluetooth;
import com.codeblue.contacts.CodeBlueContacts;
import com.codeblue.geolocation.CodeBlueCurrentLocation;
import com.codeblue.survivaltips.CodeBlueSurvivalTips;
import com.projects.codeblue.R;

public class CodeBlueDashboard extends SherlockFragment implements
		OnClickListener {

	private ImageButton imgA, imgB, imgC, imgD;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_dashboard, container,
				false);

		GradientDrawable background = (GradientDrawable) getResources()
				.getDrawable(R.drawable.rounded_rect);

		background.setColor(getResources().getColor(R.color.honeycombish_blue));

		imgA = (ImageButton) view.findViewById(R.id.survivalTipsDash);
		imgB = (ImageButton) view.findViewById(R.id.bluetoothDash);
		imgC = (ImageButton) view.findViewById(R.id.contactDash);
		imgD = (ImageButton) view.findViewById(R.id.locationDash);

		imgA.setBackgroundDrawable(background);
		imgB.setBackgroundDrawable(background);
		imgC.setBackgroundDrawable(background);
		imgD.setBackgroundDrawable(background);

		imgA.setOnClickListener(this);
		imgB.setOnClickListener(this);
		imgC.setOnClickListener(this);
		imgD.setOnClickListener(this);

		return view;
	}

	private void setFragment(Fragment fragment) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.codebluelayout, fragment);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onClick(View view) {

		Fragment fragment = null;
		
		switch (view.getId()) {
		case R.id.survivalTipsDash:
			fragment = new CodeBlueSurvivalTips();
			break;
		case R.id.bluetoothDash:
			fragment = new CodeBlueBluetooth();
			break;
		case R.id.contactDash:
			fragment = new CodeBlueContacts();
			break;
		case R.id.locationDash:
			fragment = new CodeBlueCurrentLocation();
			break;
		}
		
		setFragment(fragment);

	}

}
