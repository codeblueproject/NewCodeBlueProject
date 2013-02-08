package com.codeblue.geolocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;
import com.projects.codeblue.R;

import android.os.Bundle;

public class CodeBlueViewLocation extends
		android.support.v4.app.FragmentActivity {

	private GoogleMap cbMap;
	// st.ezekiel moreno
	private double receiverLat = 10.289976;
	private double receiverLong = 123.861891;
	// usj-r main
	/*
	 * private double destLat = 10.295413; //private double destLong =
	 * 123.862059;
	 */

	private double senderLat, senderLong;

	private GeoPoint originGeoPoint;
	private GeoPoint destGeoPoint;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.codeblue_location);

		// get the location from the extras
		Bundle bundle = getIntent().getExtras();
		senderLat = bundle.getDouble("senderLatitude");
		senderLong = bundle.getDouble("senderLongitude");
		
		if (cbMap == null) {
			// if map is null, initialize it
			cbMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if successfull in initializing it
			if (cbMap != null) {
				cbMap.setMyLocationEnabled(true);
				cbMap.addMarker(new MarkerOptions().position(
						new LatLng(receiverLat, receiverLong)).title("Marker"));
				cbMap.addMarker(new MarkerOptions().position(
						new LatLng(senderLat, senderLong)).title("Marker"));
				cbMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						receiverLat, receiverLong), 15));

				// getting the geopoints of each locations
				originGeoPoint = new GeoPoint((int) (receiverLat * 1E6),
						(int) (receiverLong * 1E6));
				destGeoPoint = new GeoPoint((int) (senderLat * 1E6),
						(int) (senderLong * 1E6));

				// calls the DrawPath class to draw the routing path
				CodeBlueDrawPath cbPath = new CodeBlueDrawPath(this,
						originGeoPoint, destGeoPoint);
				cbPath.execute(cbMap);

			}
		}

	}

}
