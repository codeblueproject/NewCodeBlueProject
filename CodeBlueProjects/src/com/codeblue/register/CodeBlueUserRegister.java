package com.codeblue.register;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.projects.codeblue.R;

public class CodeBlueUserRegister extends Activity {
	
	private EditText uname; 
	private EditText pword;
	private EditText fname;
	private EditText lname;
	private EditText conName1;
	private EditText conName2;
	private EditText conNum1;
	private EditText conNum2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeblue_register);
        
        uname = (EditText)findViewById(R.id.unameTxt);
        pword = (EditText)findViewById(R.id.passwrdTxt);
        fname = (EditText)findViewById(R.id.firstNameTxt);
        lname = (EditText)findViewById(R.id.lastNameTxt);
        conName1 = (EditText)findViewById(R.id.contactName1);
        conName2 = (EditText)findViewById(R.id.contactName2);
        conNum1 = (EditText)findViewById(R.id.contactNo1);
        conNum2 = (EditText)findViewById(R.id.contactNo2);
    }

}
