package com.codeblue.bluetooth;

import com.projects.codeblue.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class CodeBlueBluetoothRssi extends Activity {
	 private Bundle b; 
	 private BluetoothAdapter cbBluetoothAdapter;
	 private String deviceName;
	 TextView textViewDeviceName;
	 TextView textViewRssi;
	 private updateRssiTask task;
	 @Override
	public void onBackPressed() {
		 super.onBackPressed();
		// TODO Auto-generated method stub
		 	unregisterReceiver(ActionFoundReceiver);
			unregisterReceiver(adapterBroadcastReceiver);
			cbBluetoothAdapter.cancelDiscovery();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    // TODO Auto-generated method stub
	    setContentView(R.layout.codeblue_bluetooth_scanner);
	    cbBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();	
	    textViewRssi = (TextView)findViewById(R.id.textViewRssiInMeters);
	    textViewDeviceName = (TextView)findViewById(R.id.textViewDeviceName);
	    b = new Bundle();
	    b = getIntent().getExtras();
	    deviceName = b.getString("device:name");
	    textViewDeviceName.setText(deviceName);
	    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	    IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		registerReceiver( ActionFoundReceiver , filter); 
		registerReceiver(adapterBroadcastReceiver, filter2);
		discover();
	}
	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			int rssi = 0;
			Log.e("action",action);
			if(BluetoothDevice.ACTION_FOUND.equals(action)) {
				task = new updateRssiTask();
			    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			    if(device.getName().equals(deviceName)){
			    Log.e("scan", ""+device.getName());
	            rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
	            task.execute(rssi);  
				}
	        } 	
		}};
	private final BroadcastReceiver adapterBroadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String acton = intent.getAction();
			if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(acton)){
				Log.e("finished","");
				cbBluetoothAdapter.startDiscovery();
			}
		}
	};

	
public class updateRssiTask extends AsyncTask<Integer ,Void,Float>{
	int rssi = 0;
	float x = 0f;
	float meters = 0f;
	protected Float doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		for(Integer param: params){
			Log.e("task","yeah");
			 rssi = param;
			 Log.e("rssi",""+rssi);
		     if(rssi < 0)
	            {
		    	 	x = 0.0f;
		    	 	meters = 0.0f;
		    	 	x = ((rssi/(float)100));
		    	 	meters = (10*(x/4));
		    	 	meters *= -1;

	            }
		}
		return meters;
	}

	@Override
	protected void onPostExecute(Float result) {
		// TODO Auto-generated method stub

		cbBluetoothAdapter.cancelDiscovery();
		textViewRssi.setText(String.format("%.6f", result));
	}

	
	
}

public void discover(){
	if(cbBluetoothAdapter.isDiscovering()){
    	cbBluetoothAdapter.cancelDiscovery();
    }
  cbBluetoothAdapter.startDiscovery();
	  Log.e("isDiscovering","yes!");

}

}
