package com.crs.scrum;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.crs.vone.vo.Sprint;
import com.crs.vone.vo.Story;

public interface IScrumFuns {

	void populateRepoForDates(IScrumRepo repo, Date startDate,
			Map<String, String> requestProeprties) throws SCRUMException;

	List<Story> findStoriesToDoForRange(Date from, Date to,
			Map<String, String> requestProeprties) throws SCRUMException;

	List<Story> findStoriesForDays(Date start, int days,
			Map<String, String> requestProeprties) throws SCRUMException;
	
	List<Sprint> getAllSprints() throws SCRUMException;

}
