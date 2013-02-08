package com.projects.codeblue;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.codeblue.ws.CodeBlueWS;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class CodeBlueEmergency extends SherlockFragmentActivity implements
		LocationListener {

	private double currentLatitude, currentLongitude;
	private LocationManager locManager;
	private PendingIntent sentPI, deliveredPI;
	private BroadcastReceiver sendBroadcastReceiver, deliverBroadcastReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.codeblue);

		// first initialization
		if (savedInstanceState == null) {
			Fragment emergencyFragment = new CodeBlueEmergencyFragment();
			FragmentTransaction ft = getSupportFragmentManager()
					.beginTransaction();
			ft.add(R.id.codebluelayout, emergencyFragment).commit();
		}

		//getLocation();
		registerBroadcastReceivers();
	}

	@Override
	protected void onStop() {
		unregisterReceiver(sendBroadcastReceiver);
		unregisterReceiver(deliverBroadcastReceiver);
		super.onStop();
	}

	@Override
	public void onLocationChanged(Location location) {
		currentLatitude = location.getLatitude();
		currentLongitude = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	/** METHODS **/
	// get the location
	private void getLocation() {

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locManager.getBestProvider(criteria, true);
		Location location = locManager.getLastKnownLocation(provider);

		if (location != null) {
			// getLocation();
			onLocationChanged(location);
		}
	}

	// send SMS
	private void sendSMS(String phoneNo) {
		sentPI = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), 0);

		deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(
				"SMS_DELIVERED"), 0);

		String location = "%$CODE^&BLUE$%," + this.currentLatitude + ","
				+ this.currentLongitude;

		Toast.makeText(this, location, Toast.LENGTH_SHORT).show();
		SmsManager sms = SmsManager.getDefault();

		sms.sendTextMessage(phoneNo, null, location, sentPI, deliveredPI);

		AsyncTask<Void, Void, Void> cbws = new CodeBlueWS(currentLongitude,
				currentLatitude);
		cbws.execute();

	}

	private void registerBroadcastReceivers() {

		sendBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

			}
		};

		deliverBroadcastReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

			}
		};

		registerReceiver(deliverBroadcastReceiver, new IntentFilter(
				"SMS_DELIVERED"));
		registerReceiver(sendBroadcastReceiver, new IntentFilter("SMS_SENT"));

	}
}
