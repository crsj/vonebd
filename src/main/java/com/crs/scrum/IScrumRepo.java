package com.crs.scrum;

import java.util.Date;
import java.util.List;

import com.crs.vone.vo.Story;
import com.crs.vone.vo.StoryGroup;
import com.crs.vone.vo.ToDo;

public interface IScrumRepo {
	
	void saveStory(Story story);
	
	List<Story> getStoriesBySubsystem(String subsystem);
	
	StoryGroup getStoryGroupForSubsystems(List<String> subsystems);
	
	void addToDoToStory(String name, ToDo toDo);
	
	void addToDoToStory(String name, float toDo, Date asOf);
	
	Story getStory(String name);
	
	List<String> getAllSubsystems();
}
