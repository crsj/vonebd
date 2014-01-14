package com.crs.scrum.impl.vone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.crs.scrum.IScrumFuns;
import com.crs.scrum.IScrumRepo;
import com.crs.scrum.SCRUMException;
import com.crs.vone.vo.Sprint;
import com.crs.vone.vo.Story;
import com.versionone.apiclient.Asset;
import com.versionone.apiclient.Attribute;

public class ScrumVOneImpl implements IScrumFuns {

	@Override
	public void populateRepoForDates(IScrumRepo repo, Date startDate,
			Map<String, String> requestProeprties) throws SCRUMException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Story> findStoriesToDoForRange(Date from, Date to,
			Map<String, String> requestProeprties) throws SCRUMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Story> findStoriesForDays(Date start, int days,
			Map<String, String> requestProeprties) throws SCRUMException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sprint> getAllSprints() throws SCRUMException {
		
		List<Sprint> sprints = new ArrayList<Sprint>();
		
		try {

		SimpleDateFormat yyymmdd = //new SimpleDateFormat("yyyy-MM-dd");
				 new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);

		VOneHelper gs = new VOneHelper();

		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.YEAR, 2013);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 7);

		Asset[] list = gs.listOfSprints();

		Map<String, Attribute> assetAttribs = null;
		
		String val = "";
		
		Sprint sprint = null;

			for (Asset assets : list) {
	
				sprint = new Sprint();
				
				assetAttribs = assets.getAttributes();			
				
				System.out.print(assets.getOid().getKey());
				
				sprint.setSystemId(String.valueOf(assets.getOid().getKey()));
	
			
				for (String attrKey : assetAttribs.keySet()) {
					
					val = VOneHelper.getStringVal(assetAttribs.get(attrKey));
					
					if ("Timebox.Name".equalsIgnoreCase(attrKey)) {
						sprint.setName(val);
					}
					else if("Timebox.BeginDate".equalsIgnoreCase(attrKey)) {
						
						sprint.setStartDate(yyymmdd.parse(val));
					}
					else if("Timebox.EndDate".equalsIgnoreCase(attrKey)) {
						
						sprint.setEndDate(yyymmdd.parse(val));
					}
					else if("Timebox.State.Code".equalsIgnoreCase(attrKey)) {
						sprint.setStatus(val);
					}
				}
				
				sprints.add(sprint);
			}
		} catch(Exception e ) {
			throw new SCRUMException("Couldnot load sprints");
		}
		
		return sprints;
	}
}
