package com.bozpower.entity;

public class User {
	
	private int id;
	private String username;
	private String password;
	private Company companyId;
	private String remarks;
	
	public User() {
	}
	public User(int id, String username, String password, String remarks) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.remarks = remarks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Company getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", companyId=" + companyId
				+ ", remarks=" + remarks + "]";
	}

	

}
