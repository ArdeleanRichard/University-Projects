package org.LayerDataAccess;

import java.util.Base64;

public class Player {
	int id;
	String mail;
	String password;
	String name;
	
	public Player(int id, String mail, String password, String name) {
		super();
		this.id = id;
		this.mail = mail;
		this.password = new String(Base64.getEncoder().encode(password.getBytes()));
		this.name = name;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public String getDecodedPassword() {
		return new String(Base64.getDecoder().decode(password.getBytes()));
	}
	
	public void setPassword(String password) {
		this.password = new String(Base64.getEncoder().encode(password.getBytes()));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", mail=" + mail + ", password=" + password + ", name=" + name + "]";
	}
	
	
}
