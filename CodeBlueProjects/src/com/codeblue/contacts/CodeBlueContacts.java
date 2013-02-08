package com.codeblue.contacts;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockFragment;
import com.codeblue.database.Contact;
import com.codeblue.database.DatabaseHandler;
import com.projects.codeblue.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CodeBlueContacts extends SherlockFragment implements
		OnClickListener {

	private ListView lv;
	private Button addButton, syncButton;
	private ArrayList<String> names;
	private ArrayAdapter<String> adapter;
	private DatabaseHandler dh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.codeblue_contacts, container,
				false);

		//addButton = (Button) view.findViewById(R.id.btnAddContact);
		//syncButton = (Button) view.findViewById(R.id.btnSyncContact);
		lv = (ListView) view.findViewById(R.id.nameList);

		//addButton.setOnClickListener(this);
		//syncButton.setOnClickListener(this);

		dh = new DatabaseHandler(getActivity());
		names = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, names);
		showList();

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	public void onClick(View v) {
		if (v == addButton) {
			startActivity(new Intent(getActivity(), ContactActivity.class));
			showList();
		} else if (v == syncButton) {
			dh.ShowContacts(getActivity());
			showList();
		}

		dh.close();
	}

	public void showList() {
		List<Contact> contact = dh.getContacts();
		names.clear();
		for (Contact con : contact) {
			names.add(con.getName());
		}
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

}