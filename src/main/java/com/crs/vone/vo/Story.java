package com.crs.vone.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Story {

	public Story(String name, String module, String owner) {
		super();
		this.name = name;
		this.module = module;
		this.owner = owner;
		sumToDo = new ArrayList<ToDo>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<ToDo> getSumToDo() {
		return sumToDo;
	}

	public void setSumToDo(List<ToDo> sumToDo) {
		this.sumToDo = sumToDo;
	}

	public Story() {
		this("Not Set", "Not Set", "Not Set");
	}
	
	public void setToDo(ToDo toDo) {
		sumToDo.add(toDo);
	}
	
	public void setToDo(float toDoHrs, Date asOf) {
		sumToDo.add(new ToDo(toDoHrs, asOf));
	}

	String name;
	String module;
	String owner;
	
	List<ToDo> sumToDo;
	
}
