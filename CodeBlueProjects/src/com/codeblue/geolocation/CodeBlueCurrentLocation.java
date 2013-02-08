package com.codeblue.geolocation;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.projects.codeblue.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CodeBlueCurrentLocation extends SherlockFragment {

	private GoogleMap cbMap;
	private GPSTracker gps;
	private double currentLatitude;
	private double currentLongitude;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_location, container,
				false);

		gps = new GPSTracker(getActivity());

		// check if GPS enabled
		if (gps.canGetLocation()) {

			currentLatitude = gps.getLatitude();
			currentLongitude = gps.getLongitude();

		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}

		// showing the map
		if (cbMap == null) {
			// if map is null, initialize it
			cbMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

			// check if successfull in initializing it
			if (cbMap != null) {
				cbMap.setMyLocationEnabled(true);
				cbMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						currentLatitude, currentLongitude), 15));

			}
		}

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}
