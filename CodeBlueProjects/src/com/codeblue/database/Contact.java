package com.codeblue.database;

public class Contact {
	
	int id;
	String name;
	String phone_num;
	//constructors --------------------------
	public Contact(){
		
	}
	public Contact(int _id, String _name, String _num){
		this.id = _id;
		this.name = _name;
		this.phone_num = _num;
	}
	
	public Contact(String _name, String _num){
		this.name = _name;
		this.phone_num = _num;
	}
	
	
	//setters=========================
	public void setID(int _id){
		this.id = _id;
	}
	public void setName(String _name){
		this.name = _name;
	}
	public void setNum(String _num){
		this.phone_num = _num;
	}
	//getters ======================= 
	public int getID(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getNum(){
		return this.phone_num;
	}
}
