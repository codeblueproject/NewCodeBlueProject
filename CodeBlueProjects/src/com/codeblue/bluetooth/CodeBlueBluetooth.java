package com.codeblue.bluetooth;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.projects.codeblue.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CodeBlueBluetooth extends SherlockFragment {

	private TextView tv;
	TextView txtViewNoDevicesFound;
	private ArrayAdapter<String> cbFoundDevicesArrayAdapter;
	private ArrayList<String> cbAddressPool;
	private BluetoothAdapter cbBluetoothAdapter;
	private ListView listDevicesFound;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_paired_devices,
				container, false);

		tv = (TextView) view.findViewById(R.id.cb_text);
		tv.setText("Devices in range");
		txtViewNoDevicesFound = (TextView) view
				.findViewById(R.id.textViewNoDevices);
		listDevicesFound = (ListView) view.findViewById(android.R.id.list);

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		cbAddressPool = new ArrayList<String>();
		cbBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		cbFoundDevicesArrayAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.codeblue_device_list);
		getActivity().registerReceiver(ActionFoundReceiver,
				new IntentFilter(BluetoothDevice.ACTION_FOUND));
		setHasOptionsMenu(true);

		cbBluetoothAdapter.startDiscovery();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cbBluetoothAdapter.cancelDiscovery();
		getActivity().unregisterReceiver(ActionFoundReceiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		cbBluetoothAdapter.startDiscovery();
		cbFoundDevicesArrayAdapter.clear();
		txtViewNoDevicesFound.setVisibility(View.VISIBLE);
		cbFoundDevicesArrayAdapter.setNotifyOnChange(true);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1) {
			// Toast.makeText(this, "Bluetooth is turned on!",
			// Toast.LENGTH_SHORT)
			// .show();
		}

	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			txtViewNoDevicesFound.setVisibility(View.GONE);
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				cbFoundDevicesArrayAdapter.add(device.getName());
				Log.e("device", "" + device.getName());
				cbAddressPool.add(device.getAddress());
				cbFoundDevicesArrayAdapter.notifyDataSetChanged();
			}
		}
	};

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		listDevicesFound.setAdapter(cbFoundDevicesArrayAdapter);

		if (!cbBluetoothAdapter.isEnabled())
			CheckBlueToothState();

		listDevicesFound.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
		listDevicesFound.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String selected = cbAddressPool.get(arg2);
				String address = selected.substring(selected.length() - 17);
				BluetoothDevice device = cbBluetoothAdapter
						.getRemoteDevice(address);
				scanDevice(device);
			}
		});
	}

	public void scanDevice(BluetoothDevice device) {
		// Intent i = new Intent(this, CodeBlueBluetoothRssi.class);
		/*
		 * i.putExtra("device:name", device.getName());
		 * cbBluetoothAdapter.cancelDiscovery(); startActivity(i);
		 */
	}

	private void CheckBlueToothState() {
		if (cbBluetoothAdapter == null) {
			// Toast.makeText(this, "Your device does not support bluetooth!",
			// Toast.LENGTH_SHORT).show();
		} else {
			if (!cbBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, 1);
				cbFoundDevicesArrayAdapter.clear();
				txtViewNoDevicesFound.setVisibility(View.VISIBLE);
				cbAddressPool.clear();
				cbBluetoothAdapter.startDiscovery();
			}

		}
	}
}
