package com.crs.scrum;

import java.util.List;

import com.crs.vone.vo.BurnDownVO;

public interface IBurnDown {
	
	BurnDownVO generateBurnDown(List<String> subsystems);
	
}
