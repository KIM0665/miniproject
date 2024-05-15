package com.kky2;

public class join {
	String name;
	String userid;
	String password;
	String email;
	String phone;
	
	
	
	public join(String name,  String password, String email, String phone,String userid){
		super();
		this.name = name;
		this.userid = userid;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}
	

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
