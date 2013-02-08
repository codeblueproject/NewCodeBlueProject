package com.projects.codeblue;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.projects.codeblue.R;

public class CodeBlueEmergencyFragment extends SherlockFragment {

	private ImageButton ebutton, dashboard;

	private OnClickListener emergencyButton = new OnClickListener() {
		public void onClick(View v) {

		}
	};

	private OnClickListener dashboardButton = new OnClickListener() {
		public void onClick(View v) {
			Fragment dashboardFragment = new CodeBlueDashboard();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.codebluelayout, dashboardFragment);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.addToBackStack(null);
			ft.commit();
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_emergency, container,
				false);
		GradientDrawable background = (GradientDrawable) getResources()
				.getDrawable(R.drawable.rounded_rect);

		background.setColor(getResources().getColor(R.color.honeycombish_blue));
		ebutton = (ImageButton) view.findViewById(R.id.emergebutton);
		dashboard = (ImageButton) view.findViewById(R.id.dashboardbutton);

		ebutton.setBackgroundDrawable(background);
		// dashboard.setBackgroundDrawable(background);

		ebutton.setOnClickListener(emergencyButton);
		dashboard.setOnClickListener(dashboardButton);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

}
