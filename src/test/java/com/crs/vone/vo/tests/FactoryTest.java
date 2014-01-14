package com.crs.vone.vo.tests;

import org.junit.Test;

import static org.junit.Assert.*;



import com.crs.vone.vo.VOFactory;

public class FactoryTest {
	
	public static String TMS_STORY_NAME = "TMS CCN 2284";
	public static String TMS_STORY_NAME2 = "TMS PR 4130";
	public static String ETP_STORY_NAME = "ETP PR 4865";

	@Test
	public void testGetModule() {
		
		assertEquals("Extracted subsystem should be TMS ", VOFactory.getModule(TMS_STORY_NAME), "TMS");
	}
	
	@Test
	public void testGetStory() {
		
		assertEquals("Get Story ", VOFactory.getStory(TMS_STORY_NAME).getModule(), "TMS");
	}

}
