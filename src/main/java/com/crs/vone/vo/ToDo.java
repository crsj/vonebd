package com.crs.vone.vo;

import java.util.Date;

public class ToDo {
	
	public float getToDo() {
		return toDo;
	}
	public void setToDo(float toDo) {
		this.toDo = toDo;
	}
	public Date getAsOf() {
		return asOf;
	}
	public void setAsOf(Date asOf) {
		this.asOf = asOf;
	}
	public ToDo(float toDo, Date asOf) {
		super();
		this.toDo = toDo;
		this.asOf = asOf;
	}
	
	public ToDo clone() {
		return new ToDo(this.getToDo(), this.getAsOf());
	}
	
	float toDo;
	private Date asOf;

}
