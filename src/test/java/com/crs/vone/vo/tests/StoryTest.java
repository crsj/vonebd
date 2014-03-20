package com.crs.vone.vo.tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.junit.Ignore;

import com.crs.vone.vo.VOFactory;
import com.crs.vone.vo.Story;
import com.crs.vone.vo.ToDo;
import com.crs.vone.vo.ToDoComparator;

public class StoryTest {

	@Test
	public void testSetToDo() {

		Story story = VOFactory.getStory(FactoryTest.TMS_STORY_NAME);

		Calendar cal = Calendar.getInstance();

		story.setToDo(new ToDo(65.50F, cal.getTime()));
		cal.add(Calendar.DATE, 1);

		story.setToDo(60F, cal.getTime());

		Collections.sort(story.getSumToDo(), new ToDoComparator());

		assertEquals("The first ToDo should be 65.5", story.getSumToDo().get(0)
				.getToDo(), 65.5F, 0.0);
		
		assertEquals("The second ToDo should be 60.0F", story.getSumToDo().get(1)
				.getToDo(), 60.0F, 0.0);
	}

	@Test
	//@Ignore
	public void testSetToDo2() {

		Story story = VOFactory.getStory(FactoryTest.TMS_STORY_NAME);

		Calendar cal = Calendar.getInstance(); 

		story.setToDo(new ToDo(65.50F, cal.getTime()));
		cal.add(Calendar.DATE, 1);

		story.setToDo(60F, cal.getTime());

		Collections.sort(story.getSumToDo(), new ToDoComparator());

		assertEquals("The first ToDo should be 65.5", story.getSumToDo().get(0)
				.getToDo(), 65.50, 0.0);
		
		assertEquals("The second ToDo should be 65.5", story.getSumToDo().get(1)
				.getToDo(), 65.5F, 0.0);


	}

}
