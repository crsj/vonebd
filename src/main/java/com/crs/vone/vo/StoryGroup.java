package com.crs.vone.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoryGroup {
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Story> getStoryList() {
		return storyBase;
	}

	public void setStoryList(Map<String, Story> storyList) {
		this.storyBase = storyList;
	}

	public void addStory(Story story) {
		storyBase.put(story.getName(), story);
	}
	
	public List<String> getStoryGroupNames() {
		return storyGroupNames;
	}

	public void setStoryGroupNames(List<String> storyGroupNames) {
		this.storyGroupNames = storyGroupNames;
	}
	
	public void addToDo(ToDo todo) {
		this.toDoList.add(todo);
	}
	
	public List<ToDo> getToDo() {
		return toDoList;
	}

	public void setToDo(List<ToDo> toDo) {
		this.toDoList = toDo;
	}
	
	List<ToDo> toDoList = new ArrayList<ToDo>();
	
	String name;
	
	List<String> storyGroupNames;
	
	Map<String, Story> storyBase = new HashMap<String, Story>();
	
}
