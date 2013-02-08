package com.codeblue.database;


import java.util.ArrayList;
import java.util.List;



import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "LocalContact.db";
	
	private static final String TABLE_NAME = "LocalContact";
	
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_NUM = "number";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = "Create Table "+TABLE_NAME+"("+
		KEY_ID+" INTEGER PRIMARY KEY, "+KEY_NAME+" TEXT UNIQUE, "+
		KEY_NUM+" TEXT)";
		
		db.execSQL(CREATE_TABLE);
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void addContact(Contact contact){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new  ContentValues();
		
		cv.put(KEY_NAME, contact.getName());
		cv.put(KEY_NUM, contact.getNum());
		
		db.insert(TABLE_NAME, null, cv);
		
		
		db.close();
	}
		
	
	public List<Contact> getContacts(){
		List<Contact> contactList = new ArrayList<Contact>();
		String selectQuery = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+KEY_NAME+" ASC";
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		
		if(c.moveToFirst()){
			do{
				Contact contact = new Contact();
				contact.setID(Integer.parseInt(c.getString(0)));
				contact.setName(c.getString(1));
				contact.setNum(c.getString(2));
				contactList.add(contact);
			}while(c.moveToNext());
		}
//		c.close();
//		db.close();
		return contactList;
	}
	
	public int updateContact(Contact contact){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, contact.getName());
		cv.put(KEY_NAME, contact.getNum());
		
		
		return db.update(TABLE_NAME, cv, KEY_ID +" = ?", new String[] {String.valueOf(contact.getID())});
		
	}
	
	public void deleteContact(Contact contact){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, KEY_ID +" = ?", new String[] {String.valueOf(contact.getID())});
		db.close();
	}
	
	public void ShowContacts(Context cont){
		int count = 0;
		List<Contact> contactz = this.getContacts();
		
		ArrayList<String> namesList = new ArrayList<String>();
		ArrayList<String> phoneList = new ArrayList<String>();
		boolean copyCounter;
		
		
		ContentResolver cr = cont.getContentResolver();
		
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		
		if(cur.getCount() >0)
		{
			
			while(cur.moveToNext()){
				count+= 1;
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				 if(Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
	                   //Query phone here.  Covered next
					
	                 Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", 
	                 new String[]{id}, null);
	                  while (pCur.moveToNext()) {
	                	
	                // Do something with phones
	                    String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                    namesList.add(name); 
	                    phoneList.add(phoneNo); 
	                } 
	                pCur.close();                 
	        }
			}
			Log.e("NUMBER OF PHONE CONTACTS",count+"" );
			
		}
		Log.e("zzzz",contactz.size()+"");
		Log.e("yyyy",namesList.size()+"");
		
		
		if(namesList.isEmpty()){
			for(int a = 0; a < contactz.size(); a++){
				this.addContact(new Contact(namesList.get(a), phoneList.get(a)));
			}
		}
		else if(!(namesList.isEmpty())){
			for(int i = 0; i < namesList.size(); i++){
				copyCounter = true;
				//Log.e(phoneList.get(i), namesList.get(i));
				for(int j = 0; j< contactz.size(); j++){
					
					
					
					if(!(namesList.get(i).equals(contactz.get(j).getName()))){
						copyCounter = true;
						//db.addContact(new Contact(namesList.get(i), phoneList.get(i)));
					}
					else{
						copyCounter = false;
						break;
					}
				}
				if(copyCounter){
					try{
					this.addContact(new Contact(namesList.get(i), phoneList.get(i)));
					Log.e("Successfully Copied", namesList.get(i));
					}
					catch (SQLiteConstraintException e) {
						Log.e("Successfully Copied", namesList.get(i));
					}
				}
				
			
			}
		}
		
			this.close();
			cur.close();
			
		
	}
	
	
	
	

}