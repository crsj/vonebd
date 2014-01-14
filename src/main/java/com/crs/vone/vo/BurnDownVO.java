package com.crs.vone.vo;

import java.util.Date;
import java.util.List;

public class BurnDownVO {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<List<Object>>  getWork() {
		return work;
	}
	public void setWork(List<List<Object>>  work2) {
		this.work = work2;
	}
	public int getStartDay() {
		return startDay;
	}
	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}
	public int getEndDay() {
		return endDay;
	}
	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}
	
	String name;
	String description;
	List<List<Object>>  work;
	List<List<Object>>  idealWork;
	
	public List<List<Object>>  getIdealWork() {
		return idealWork;
	}
	public void setIdealWork(List<List<Object>>  idealTodo) {
		this.idealWork = idealTodo;
	}

	
	int startDay;
	int endDay;
}
