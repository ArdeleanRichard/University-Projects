package com.mkyong.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	public int userid;
	@Column(name = "username")
	public String username;
	@Column(name = "password")
	public String password;
	@Column(name = "name")
	public String name;
	
	public User() {
	}


	public User(int id, String username, String password, String name) {
		super();
		this.userid = id;
		this.username = username;
		this.password = password;
		this.name = name;
	}
	

	public int getUserid() {
		return userid;
	}


	public void setUserid(int id) {
		this.userid = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
}
