package com.crs.vonebd.apiclient.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.crs.scrum.IScrumFuns;
import com.crs.scrum.IScrumRepo;
import com.crs.scrum.impl.BurndownUtil;
import com.crs.scrum.impl.ScrumInMemoryRepo;
import com.crs.scrum.impl.vone.ScrumVOneImpl;
import com.crs.scrum.impl.vone.VOneHelper;
import com.crs.vone.vo.Sprint;
import com.crs.vone.vo.Story;
import com.crs.vone.vo.VOFactory;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attribute;

public class GettingStartedDriver {

	public static void main(String[] args) throws Exception {

		System.out.println("Start");

		System.out.println("Press any key to start..."); System.in.read();
		VOneHelper gs = new VOneHelper();

		// Asset[] list = gs.FindListOfAssets();

//		System.out.println(getStories());
//		getSprints();
		IScrumFuns funs = new ScrumVOneImpl();
		
		Sprint sprint = (Sprint) BurndownUtil
				.jsonToJava(
						"{\"name\":\"Spine October 2013 Sprint\",\"systemId\":\"2311\","
								+ "\"startDate\":\"2013-10-06T23:00:00.000+0000\","
								+ "\"endDate\":\"2013-11-01T00:00:00.000+0000\",\"status\":\"CLSD\"}",
						Sprint.class);
		
		System.out.println("getting burndowns for sprint " + sprint);
		
//		System.out.println(BurndownUtil.jsonify(funs.getAllSprints()));
		System.out.println(getStories(sprint));
		
		// getTasks();

		System.out.println("End");

	}
	
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

	public static String getStories(Sprint sprint) throws Exception {

		// TODO Auto-generated method stub
		SimpleDateFormat yyymmdd = new SimpleDateFormat("yyyy-MM-dd");
		VOneHelper gs = new VOneHelper();
		
		List<Date> daysList = getDaysList(sprint.getStartDate(), sprint.getEndDate());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		Date endDate = cal.getTime();
		
		cal = Calendar.getInstance();
		cal.setTime(sprint.getStartDate());
		
		Calendar end = Calendar.getInstance();
		end.setTime(sprint.getEndDate());
		
		end.set(Calendar.HOUR_OF_DAY, 23);
		
		cal.set(Calendar.HOUR_OF_DAY, 22);
		
		sprint.setEndDate(end.getTime());
		
		if (sprint.getEndDate().compareTo(endDate) < 1)
			endDate = sprint.getEndDate();

		Story story = null;

		String sumToDo = "";

		IScrumRepo repo = new ScrumInMemoryRepo();

		Story repoStory = null;
		
		Date asOf = null;

		while (cal.getTime().compareTo(endDate) < 1) {

			asOf = cal.getTime();
			Asset[] list = gs.OIDListOfAssets(asOf, sprint.getSystemId());

			Map<String, Attribute> assetAttribs = null;

			String strVal = "";

			for (Asset assets : list) {

				assetAttribs = assets.getAttributes();
//				System.out.print("\n" + yyymmdd.format(d));

				story = new Story();

				for (String attrKey : assetAttribs.keySet()) {

					strVal = getStringVal(assetAttribs.get(attrKey));

					if ("Story.Name".equals(attrKey)) {
						story.setName(strVal);
					} else if ("Story.Children.ToDo.@Sum".equals(attrKey)) {

						story.setToDo(
								Float.parseFloat(( strVal == null || strVal.trim()
										.length() == 0 || strVal.equals("null")
										) ? "0.0" : strVal), asOf);
					}

//					System.out.print("," + getStringVal(assetAttribs.get(attrKey)));
				}
				repoStory = repo.getStory(story.getName());

				if (repoStory == null) {

					repoStory = VOFactory.getStory(story.getName());
					repoStory.setToDo(story.getSumToDo().get(0));
					repo.saveStory(repoStory);
				} else {
					repo.addToDoToStory(repoStory.getName(), story.getSumToDo()
							.get(0));
				}

			}
			
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 22);
		}
		
		return BurndownUtil.jsonify((BurndownUtil
				.getBurdownMap(repo, sprint.getStartDate(), sprint.getEndDate()).values()));

	}

	public static void getTasks() throws Exception {

		SimpleDateFormat yyymmdd = new SimpleDateFormat("YYYY-MM-dd");

		VOneHelper gs = new VOneHelper();

		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 7);

		Asset[] list = gs.GetTasksOfTimebox();

		Map<String, Attribute> assetAttribs = null;


		for (Asset assets : list) {

			assetAttribs = assets.getAttributes();

			for (String attrKey : assetAttribs.keySet()) {

				System.out.print(",");
					System.out.print(getStringVal(assetAttribs.get(attrKey)));
			}
			System.out.println();
		}

	}
	
	public static void getSprints() throws Exception {

		SimpleDateFormat yyymmdd = new SimpleDateFormat("yyyy-MM-dd");

		VOneHelper gs = new VOneHelper();

		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 7);

		Asset[] list = gs.listOfSprints();

		Map<String, Attribute> assetAttribs = null;


		for (Asset assets : list) {

			assetAttribs = assets.getAttributes();			
			
			System.out.print(assets.getOid().getKey());

			for (String attrKey : assetAttribs.keySet()) {

				System.out.print(",");
					System.out.print(getStringVal(assetAttribs.get(attrKey)));
			}
			System.out.println();
		}
	}
	
	

	public static String getStringVal(Object val) {

		return val != null ? val.toString().replaceAll("\\{value=", "")
				.replaceAll("\\}", "").replace("\n", "") : "";
	}

}
