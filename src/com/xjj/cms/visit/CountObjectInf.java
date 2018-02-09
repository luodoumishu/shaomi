package com.xjj.cms.visit;

import java.util.Date;



public class CountObjectInf {
	protected int totalCount = 0; 
	//日访问量 
	protected int dayCount = 0; 
	//周访问量 
	protected int weekCount = 0; 
	// 月访问量 
	protected int monthCount = 0; 
	//年访问量 
	protected int yearCount = 0; 

	//临时访问量 
	protected int tempCount=0; 

	protected Date date =new Date(System.currentTimeMillis()); 

	/** 
	 * @return 
	 */ 
	public int getDayCount() { 
	return dayCount; 
	} 

	/** 
	 * @return 
	 */ 
	public int getMonthCount() { 
	return monthCount; 
	} 

	/** 
	 * @return 
	 */ 
	public int getTotalCount() { 
	return totalCount; 
	} 

	/** 
	 * @return 
	 */ 
	public int getWeekCount() { 
	return weekCount; 
	} 

	/** 
	 * @return 
	 */ 
	public int getYearCount() { 
	return yearCount; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setDayCount(int i) { 
	dayCount = i; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setMonthCount(int i) { 
	monthCount = i; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setTotalCount(int i) { 
	totalCount = i; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setWeekCount(int i) { 
	  weekCount = i; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setYearCount(int i) { 
	  yearCount = i; 
	} 



	/** 
	 * @return 
	 */ 
	public Date getDate() { 
	  return date; 
	} 

	/** 
	 * @param tedate 
	 */ 
	public void setDate(Date tedate) { 
	  this.date= tedate; 
	} 

	/** 
	 * @return 
	 */ 
	public int getTempCount() { 
	   return tempCount; 
	} 

	/** 
	 * @param i 
	 */ 
	public void setTempCount(int i) { 
	  tempCount = i; 
	} 

}
