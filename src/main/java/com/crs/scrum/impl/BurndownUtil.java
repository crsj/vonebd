package com.crs.scrum.impl;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.crs.scrum.IScrumRepo;
import com.crs.vone.vo.BurnDownVO;
import com.crs.vone.vo.StoryGroup;
import com.crs.vone.vo.ToDo;
import com.crs.vone.vo.ToDoComparator;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class BurndownUtil {

	private static Calendar cal = Calendar.getInstance();

	
	private static List<Date> getDaysList(Date start, Date end) {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			
			cal.set(Calendar.HOUR_OF_DAY, 23);
			
			List<Date> daysList = new ArrayList<Date>();
			
			while (cal.getTime().compareTo(end) < 1) {
				daysList.add(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			return daysList;
			
		}

	public static Map<String, BurnDownVO> getBurdownMap(IScrumRepo repo,
			Date startDay, Date endDay) {

		Calendar tempCal = Calendar.getInstance();

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDay);

		tempCal.setTime(startDay);

		List<Date> dayArray = getDaysList(startDay, endDay);

		List<String> subsys = repo.getAllSubsystems();

		StoryGroup sg = null;

		Map<String, BurnDownVO> burnDownMap = new HashMap<String, BurnDownVO>();

		sg = repo.getStoryGroupForSubsystems(Arrays.asList("*"));

		burnDownMap.put(sg.getName(),
				getBurndownVO(sg, startDay, endDay, dayArray));

	/*	sg = repo.getStoryGroupForSubsystems(Arrays.asList("TMS", "ETP", "PDS",
				"ERS"));
		burnDownMap.put(sg.getName(),
				getBurndownVO(sg, startDay, endDay, dayArray));

		sg = repo
				.getStoryGroupForSubsystems(Arrays.asList("CMS", "DSA", "UIM"));
		burnDownMap.put(sg.getName(),
				getBurndownVO(sg, startDay, endDay, dayArray));*/

		for (String string : subsys) {

			sg = repo.getStoryGroupForSubsystems(Arrays.asList(string));
			burnDownMap.put(sg.getName(),
					getBurndownVO(sg, startDay, endDay, dayArray));
		}

		return burnDownMap;
	}

	private static SimpleDateFormat dd_DayFormatter = new SimpleDateFormat("dd-MMM-yyyy");

	private static ToDoComparator todoComp = new ToDoComparator();

	private static BurnDownVO getBurndownVO(StoryGroup sg, Date startDate,
			Date endDate, List<Date> dayList) {
		
		BurnDownVO bvo = new BurnDownVO();
		bvo.setName(sg.getName());
		
		if(sg.getToDo().size() == 0) {
			bvo.setWork(new ArrayList<List<Object>>(0));
			bvo.setIdealWork(new ArrayList<List<Object>>(0));
			return bvo;
		}

		Collections.sort(sg.getToDo(), todoComp);

		List<List<Object>> work = new ArrayList<List<Object>>(sg.getToDo().size());

		float maxToDo = sg.getToDo().get(0).getToDo();

		List<Object> tWork = null;

		for (int i = 0; i < sg.getToDo().size(); i++) {

			tWork = new ArrayList<Object>(2);
			tWork.add(0,sg.getToDo()
					.get(i).getAsOf());
			
			tWork.add(1,sg.getToDo().get(i).getToDo());

			work.add(i, tWork);

			if (maxToDo < sg.getToDo().get(i).getToDo())
				maxToDo = sg.getToDo().get(i).getToDo();
		}

		float idealDayToDo = maxToDo / (dayList.size() -1);

		List<List<Object>> idealTodo = new ArrayList<List<Object>>(sg.getToDo().size());

		tWork = new ArrayList<Object>(2);

		tWork.add(0, dayList.get(0));
		tWork.add(1, maxToDo);

		idealTodo.add(0, tWork);

		for (int i = 1; i < dayList.size(); i++) {

			tWork = new ArrayList<Object>(2);
			
			tWork.add(0, dayList.get(i));
			tWork.add(1, maxToDo - (idealDayToDo * i));

			idealTodo.add(i, tWork);
		}

		bvo.setIdealWork(idealTodo);
		bvo.setWork(work);

		return bvo;

	}

	private static int lastDayOfMonth() {

		Date today = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String getHTMLBurndown(Map<String, String> burnDownMap) {

		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head><body><div>");

		Iterator<Entry<String, String>> burdownIter = burnDownMap.entrySet()
				.iterator();

		Entry<String, String> e = null;
		while (burdownIter.hasNext()) {
			e = burdownIter.next();

			sb.append(getSubsystemDiv(e.getKey(), e.getValue()));
		}

		sb.append("</div></body></html>");

		return sb.toString();

	}

	private static String getSubsystemDiv(String name, String url) {
		return "<div><h3>" + name + "</h3><div><img src=\"" + url
				+ "\"/></div></div>";
	}

	public static String getURLString(List<ToDo> toDo) {

		return "http://apps.vanpuffelen.net/charts/burndown.jsp?days="
				+ getToDOString(toDo) + "&work=" + getWorkString(toDo);
	}

	public static void browse(String html) {

		try {
			File f = File.createTempFile("Burndown", ".html");

			FileOutputStream fos = new FileOutputStream(f);

			fos.write(html.getBytes());
			fos.flush();

			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String jsonify(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonStr;
	}

	public static Object jsonToJava(String str, Class type) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);

		Object sprint = null;
		try {
			sprint = mapper.readValue(str, type);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sprint;

	}

	public static String getToDOString(List<ToDo> toDo) {

		StringBuilder sb = new StringBuilder(",");

		int maxDay = 0;

		for (ToDo toDo2 : toDo) {

			cal.setTime(toDo2.getAsOf());

			maxDay = cal.get(Calendar.DAY_OF_MONTH);

			sb.append(",").append(maxDay);
		}

		for (int i = maxDay + 1; i <= 31; i++) {
			sb.append(",").append(i);
		}

		return sb.toString().substring(2);
	}

	public static String getWorkString(List<ToDo> toDo) {

		StringBuilder sb = new StringBuilder(",");

		// Collections.sort(toDo, new ToDoComparator());

		for (ToDo toDo2 : toDo) {

			cal.setTime(toDo2.getAsOf());
			sb.append(",").append((int) toDo2.getToDo());
		}

		return sb.toString().substring(2);
	}

}
