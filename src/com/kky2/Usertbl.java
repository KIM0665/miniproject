package com.kky2;


public class Usertbl {
	private String name;
	private String id;
	private String phone;
	private String password;
	private String email;
	public Usertbl( String id,String password) {
		this.id = id;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public Usertbl(String id,String name,String password,  String phone,  String email) {
		super();
		this.name = name;
		this.id = id;
		this.phone = phone;
		this.password = password;
		this.email = email;
	}
	@Override
	public String toString() {
		return "Usertbl [name=" + name + ", id=" + id + ", phone=" + phone + ", password=" + password + ", email="
				+ email + "]";
	}
	
}
