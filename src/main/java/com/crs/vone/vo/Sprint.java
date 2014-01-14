package com.crs.vone.vo;

import java.util.Date;


public class Sprint {
	
	@Override
	public String toString() {
		return "Sprint [systemId=" + systemId + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", name=" + name + "]";
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String systemId;
	private Date startDate;
	private Date endDate;
	private String name;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
