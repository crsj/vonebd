package com.crs.vone.vo.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.crs.scrum.impl.BurndownUtil;
import com.crs.vone.vo.Sprint;

public class TestBurndownutil {

	@Test
	public void testJsonToJava() {
		
		String str = "{\"name\":\"Spine October 2013 Sprint\",\"systemId\":\"2311\",\"startDate\":\"2013-10-06T23:00:00.000 0000\",\"endDate\":\"2013-11-01T00:00:00.000 0000\",\"status\":\"CLSD\"}";
		
		str = str.replaceAll(" 000", "+000");
		
		Sprint sprint = (Sprint) BurndownUtil.jsonToJava(str, Sprint.class);
		
		System.out.println("sprint jsonToJava : " + sprint);
		
		assertEquals("Retrieving timebox value.. jsonToJava ", "2311", sprint.getSystemId());
	}
	
	@Test
	public void testJsonify() {
		
		String str = "{\"name\":\"Spine October 2013 Sprint\",\"systemId\":\"2311\",\"startDate\":\"2013-10-06T23:00:00.000+0000\",\"endDate\":\"2013-11-01T00:00:00.000+0000\",\"status\":\"CLSD\"}";
		
		Sprint sprint = (Sprint) BurndownUtil.jsonToJava(str, Sprint.class);
		
		str = BurndownUtil.jsonify(sprint);
		
		System.out.println("jsonified Sprint :" + str);
		
		assertTrue("testing jsonify ", str.contains("Spine October 2013 Sprint"));
		
		Object obj = new Date();
		
		System.out.println("jsonified obj :" + BurndownUtil.jsonify(obj));
	}

}
