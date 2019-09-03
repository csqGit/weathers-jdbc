package com.bozpower.entity;

public class Device {
	private int id;
	private String deviceName;
	private Company companyId;
	private String deviceId;
	private int deviceState;
	private String remarks;
	public Device() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	

	public int getDeviceState() {
		return deviceState;
	}

	public void setDeviceState(int deviceState) {
		this.deviceState = deviceState;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceName=" + deviceName + ", companyId=" + companyId + ", deviceId=" + deviceId
				+ ", deviceState=" + deviceState + ", remarks=" + remarks + "]";
	}

	

	
	
}
