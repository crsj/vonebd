package com.crs.vone.vo;

import java.util.Comparator;

public class ToDoComparator implements Comparator<ToDo> {

	public int compare(ToDo me, ToDo you) {
		
		return me.getAsOf().compareTo(you.getAsOf());
	}
}
