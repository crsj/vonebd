package com.crs.vone.vo;

import java.util.HashMap;
import java.util.Map;

public class VOFactory {
	
	private static Map<String,Story> storyList = new HashMap<String,Story>();

	public static Story getStory(String storyName) {
		
		Story story = storyList.get(storyName);
		
		if (story == null) {
			story = new Story();
			story.setName(storyName);
			story.setModule(getModule(storyName));
		}
		
		return story;
	}
	
	public static String getModule(String storyName) {
		return storyName.substring(0, 3);
	}
}
