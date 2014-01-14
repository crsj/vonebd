package com.crs.scrum.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.crs.scrum.IScrumRepo;
import com.crs.vone.vo.Story;
import com.crs.vone.vo.StoryGroup;
import com.crs.vone.vo.ToDo;
import com.crs.vone.vo.ToDoComparator;
import com.crs.vone.vo.VOFactory;

public class ScrumInMemoryRepo implements IScrumRepo {

	Map<String, Story> storyBase = new HashMap<String, Story>();

	Map<String, StoryGroup> subsystemBase = new HashMap<String, StoryGroup>();

	public void saveStory(Story story) {

		storyBase.put(story.getName(), story);

		StoryGroup sg = subsystemBase.get(story.getModule());

		if (sg == null) {

			sg = new StoryGroup();
			sg.setName(story.getModule());
			subsystemBase.put(story.getModule(), sg);
		}

		sg.addStory(story);
	}

	public List<Story> getStoriesBySubsystem(String subsystem) {

		if (subsystemBase.get(subsystem) == null) {
			return null;
		}

		return Collections.list(Collections.enumeration((subsystemBase.get(
				subsystem).getStoryList().values())));
	}

	public StoryGroup getStoryGroupForSubsystems(List<String> subsystems) {
		// TODO Auto-generated method stub
		
		Iterator<String> i = subsystems.iterator();
		String nxt = "";
		List<String> correctedList = new ArrayList<String>();
		while (i.hasNext()) {

			nxt = i.next();
			if (nxt.contains("*")) {
				correctedList = Arrays.asList(subsystemBase.keySet().toArray(
						new String[0]));
				break;
			} else {
				if (subsystemBase.get(nxt) == null) {
					System.out.println("There is no subsystem named : *"
							+ nxt
							+ "*. Excluding this from the group.");
				}
				else {
					correctedList.add(nxt);
				}
			}
			
		}
		subsystems = correctedList;

		Collections.sort(subsystems);

		String key = getGroupKey(subsystems);

		List<StoryGroup> storyGroupList = new ArrayList<StoryGroup>();

		for (String module : subsystems) {

			if (subsystemBase.get(module) != null)
				storyGroupList.add(subsystemBase.get(module));
		}

		return groupStoryGroups(key, storyGroupList, subsystems);
	}

	private StoryGroup groupStoryGroups(String key,
			List<StoryGroup> storyGroupList, List<String> subsystems) {

		ToDoComparator todoComp = new ToDoComparator();

		StoryGroup sg = new StoryGroup();

		sg.setName(key);

		List<ToDo> moduleToDo = null;

		List<Story> moduleStoryList = null;

		Map<String, ToDo> resultToDoMap = new HashMap<String, ToDo>();

		ToDo groupedTodo = null;

		String toDoKey = "";

		for (String module : subsystems) {

			moduleStoryList = getStoriesBySubsystem(module);

			for (Story story : moduleStoryList) {

				moduleToDo = story.getSumToDo();

				for (ToDo toDo : moduleToDo) {

					toDoKey = getDateKey(toDo.getAsOf());

					groupedTodo = resultToDoMap.get(toDoKey);

					if (groupedTodo == null) {
						groupedTodo = toDo.clone();
						resultToDoMap.put(toDoKey, groupedTodo);
					} else {
						groupedTodo.setToDo(groupedTodo.getToDo()
								+ toDo.getToDo());
					}
				}
			}
		}

		moduleToDo = Collections.list(Collections.enumeration(resultToDoMap
				.values()));

		Collections.sort(moduleToDo, todoComp);

		sg.setToDo(moduleToDo);

		return sg;
	}

	private static SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

	private String getDateKey(Date date) {

		// Calendar cal = Calendar.getInstance();
		//
		// cal.setTime(date);

		return formatter.format(date);

		// return String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "-"
		// + String.valueOf(cal.get(Calendar.MONTH)) + "-"
		// + String.valueOf(cal.get(Calendar.YEAR));
	}

	private String getGroupKey(List<String> subsystems) {

		Collections.sort(subsystems);
		StringBuilder sb = new StringBuilder();

		for (String string : subsystems) {

			if (sb.toString().length() > 0)
				sb.append("_");

			sb.append(string);

		}

		return sb.toString();
	}

	public void addToDoToStory(String name, ToDo toDo) {

		Story story = storyBase.get(name);

		if (story == null) {
			story = VOFactory.getStory(name);
			saveStory(story);
		}

		story.setToDo(toDo);

	}

	public void addToDoToStory(String name, float toDo, Date asOf) {
		// TODO Auto-generated method stub
		addToDoToStory(name, new ToDo(toDo, asOf));
	}

	public Story getStory(String name) {
		// TODO Auto-generated method stub
		return storyBase.get(name);
	}

	public List<String> getAllSubsystems() {

		return Collections
				.list(Collections.enumeration(subsystemBase.keySet()));
	}

}
