package com.crs.vone.vo.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import com.crs.scrum.IScrumRepo;
import com.crs.scrum.impl.ScrumInMemoryRepo;
import com.crs.vone.vo.StoryGroup;
import com.crs.vone.vo.VOFactory;

public class TestVOneInMemoryRepo {

	@Test
	public void testSaveAndGetStory() {

		IScrumRepo repo = new ScrumInMemoryRepo();

		repo.saveStory(VOFactory.getStory(FactoryTest.TMS_STORY_NAME));

		assertEquals("Fetched story should be same ",
				repo.getStory(FactoryTest.TMS_STORY_NAME).getName(),
				FactoryTest.TMS_STORY_NAME);
	}

	@Test
	public void testAddToDoToStory() {

		IScrumRepo repo = new ScrumInMemoryRepo();

		repo.saveStory(VOFactory.getStory(FactoryTest.TMS_STORY_NAME));

		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME, 60.50F, new Date());

		assertEquals("Fetched ToDo should be 60.5 ",
				repo.getStory(FactoryTest.TMS_STORY_NAME).getSumToDo().get(0)
						.getToDo(), 62.5F, 0.0);

	}

	@Test
	public void testGetStoryGroupForSubsystems() {

		IScrumRepo repo = new ScrumInMemoryRepo();

		repo.saveStory(VOFactory.getStory(FactoryTest.TMS_STORY_NAME));
		
		repo.saveStory(VOFactory.getStory(FactoryTest.TMS_STORY_NAME2));

		repo.saveStory(VOFactory.getStory(FactoryTest.ETP_STORY_NAME));

		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME, 60.50F, new Date());
		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME, 50.50F,
				new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME, 40.50F,
				new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		
		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME2, 60.50F, new Date());
		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME2, 50.50F,
				new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		repo.addToDoToStory(FactoryTest.TMS_STORY_NAME2, 40.50F,
				new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));

		repo.addToDoToStory(FactoryTest.ETP_STORY_NAME, 50.50F, new Date());
		repo.addToDoToStory(FactoryTest.ETP_STORY_NAME, 40.50F,
				new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		repo.addToDoToStory(FactoryTest.ETP_STORY_NAME, 30.50F,
				new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));

		StoryGroup sg = repo.getStoryGroupForSubsystems(Arrays.asList("TMS",
				"ETP"));

		String checkString = FactoryTest.TMS_STORY_NAME
				+ FactoryTest.ETP_STORY_NAME;

		assertEquals("Returned group name should be ETP_TMS", sg.getName(),
				"ETP_TMS");

		assertEquals("Should return group should have todo 111.0", 171.50F, sg.getToDo()
				.get(0).getToDo(), 0.0);

		assertEquals("Should return group should have todo 111.0", 141.50F, sg.getToDo()
				.get(1).getToDo(),  0.0);

		assertEquals("Should return group should have todo 111.0", 111.50F, sg.getToDo()
				.get(2).getToDo(),  0.0);
		
		assertEquals("Fetched ToDo should be 60.5 ", 60.5F,
				repo.getStory(FactoryTest.TMS_STORY_NAME).getSumToDo().get(0)
						.getToDo(), 0.0);
		
		assertEquals("Fetched ToDo should be 50.50F ", 50.50F,
				repo.getStory(FactoryTest.ETP_STORY_NAME).getSumToDo().get(0)
						.getToDo(), 0.0);

	}

	@Test
	public void testGetStoriesBySubsystem() {

		IScrumRepo repo = new ScrumInMemoryRepo();

		repo.saveStory(VOFactory.getStory(FactoryTest.TMS_STORY_NAME));

		repo.saveStory(VOFactory.getStory(FactoryTest.ETP_STORY_NAME));

		assertEquals("Fetched story should be same ", repo
				.getStoriesBySubsystem("TMS").get(0).getName(),
				FactoryTest.TMS_STORY_NAME);

		assertEquals("Fetched story should be same ", repo
				.getStoriesBySubsystem("ETP").get(0).getName(),
				FactoryTest.ETP_STORY_NAME);

	}

}
