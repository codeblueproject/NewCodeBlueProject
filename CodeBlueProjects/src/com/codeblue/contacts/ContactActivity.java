package com.codeblue.contacts;

import com.codeblue.database.Contact;
import com.codeblue.database.DatabaseHandler;
import com.projects.codeblue.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends Activity implements OnClickListener {

	private EditText name;
	private EditText number;
	private Button add;
	private Button clear;

	DatabaseHandler dh;
	Contact contact;
	Intent i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		name = (EditText) findViewById(R.id.nameEditTxt);
		number = (EditText) findViewById(R.id.numEditTxt);
		add = (Button) findViewById(R.id.btnAdd);
		clear = (Button) findViewById(R.id.btnClear);

		add.setOnClickListener(this);
		clear.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_contact, menu);
		return true;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		dh = new DatabaseHandler(this);
		i = new Intent(getApplicationContext(), CodeBlueContacts.class);
		if (v == add) {
			dh.addContact(new Contact(name.getText().toString(), number
					.getText().toString()));
			startActivity(i);

		} else if (v == clear) {
			name.setText("");
			number.setText("");
			Toast.makeText(this, "Cleared.", Toast.LENGTH_SHORT).show();
		}

	}
}
