package com.bozpower.entity;



public class Weathers {
    private Integer id;
    private Double dm;//平均风向
    private Double sm;//平均风速
    private Double ta;//温度
    private Double ua;//湿度
    private Double pa;//压强
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String time;//时间
    private Company companyId;
    private Device deviceId;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Weathers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getDm() {
		return dm;
	}

	public void setDm(Double dm) {
		this.dm = dm;
	}


	public Double getSm() {
		return sm;
	}

	public void setSm(Double sm) {
		this.sm = sm;
	}

	public Double getTa() {
		return ta;
	}

	public void setTa(Double ta) {
		this.ta = ta;
	}

	public Double getUa() {
		return ua;
	}

	public void setUa(Double ua) {
		this.ua = ua;
	}

	public Double getPa() {
		return pa;
	}

	public void setPa(Double pa) {
		this.pa = pa;
	}

	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public Device getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Device deviceId) {
		this.deviceId = deviceId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Weathers [id=" + id + ", dm=" + dm + ", sm=" + sm + ", ta=" + ta + ", ua=" + ua + ", pa=" + pa
				+ ", time=" + time + ", companyId=" + companyId + ", deviceId=" + deviceId + ", remarks=" + remarks
				+ "]";
	}




	
}