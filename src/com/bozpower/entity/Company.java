package com.bozpower.entity;

public class Company {
	private int id;
	private String companyName;
	private int deviceNumber;
	private String remarks;
	
	public Company(int id, String companyName, int deviceNumber, String remarks) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.deviceNumber = deviceNumber;
		this.remarks = remarks;
	}
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(int deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", deviceNumber=" + deviceNumber + ", remarks="
				+ remarks + "]";
	}
	
	
}
