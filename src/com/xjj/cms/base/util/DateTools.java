package com.xjj.cms.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
  
/**     
 * @Description:   时间操作工具类
 * @author fengjunkong      
 * @created 2014-9-3 下午5:05:22    
 */      
public class DateTools {
	
	
	

	public static final String FULL_YMDHMS = "yyyy-MM-dd HH:mm:ss";	
	public static final String FULL_YMDHM = "yyyy-MM-dd HH:mm";
	public static final String FULL_YMD = "yyyy-MM-dd";
	
	
	public static String dateToString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);		
	}
	
	public static Date stringToDate(String strDate,String pattern){
		
		if(StringUtils.isEmpty(strDate)){
			return null;
		}else{
			SimpleDateFormat df = null;
			Date date = null;
			df = new SimpleDateFormat(pattern , java.util.Locale.CHINESE);
			try{
				date = df.parse(strDate);
			}catch(ParseException e){
				e.printStackTrace();
			}
			return date;
		}

	}
	
	/**
	 * 获取当前日期时间
	 * @return
	 */
	public static Date getCurDateTime(){
		return new Date();
	}
	/**
	 * 获取当前日期 yyyy-MM-dd
	 * @return
	 */
	public static Date getCurDate(){
		Date adate = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = df.format(adate);
		Date rtnDate = null;
		try{
			rtnDate = df.parse(strDate);
		}catch(Exception e){
			rtnDate = adate;
		}
		return rtnDate;
	}
	
	/**
     * 计算两个日期之间相差的天数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int diffdates(Date date1, Date date2) {
        int result = 0;

        GregorianCalendar gc1 = new GregorianCalendar();
        GregorianCalendar gc2 = new GregorianCalendar();

        gc1.setTime(date1);
        gc2.setTime(date2);
        
    	gc1.clear(Calendar.MILLISECOND);
    	gc1.clear(Calendar.SECOND);
    	gc1.clear(Calendar.MINUTE);
    	gc1.clear(Calendar.HOUR_OF_DAY);

    	gc2.clear(Calendar.MILLISECOND);
    	gc2.clear(Calendar.SECOND);
    	gc2.clear(Calendar.MINUTE);
    	gc2.clear(Calendar.HOUR_OF_DAY);

    	if(gc1.before(gc2)){
        	while (gc1.before(gc2)) {
    	    	gc1.add(Calendar.DATE, 1);
    	    	result++;
        	}
    	}else{
        	while (gc2.before(gc1)) {
    	    	gc1.add(Calendar.DATE, -1);
    	    	result -- ;
        	}
    	}
    	return result;
    }
    
    
	/**
	 * 根据传入的日期取得参数对应月份的最大日期
	 * @param adate
	 * @return
	 */
	public Date rtnMaxDateInMonth(Date adate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(adate);
		cal.set(cal.DAY_OF_MONTH, 1);
		cal.roll(cal.DATE, false);
		return cal.getTime();
	}
	/**
	 * 根据传入的日期取得参数对应月份的最小日期
	 * @param adate
	 * @return
	 */
	public Date rtnMinDateInMonth(Date adate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(adate);
		cal.set(cal.DATE, 1);
		return cal.getTime();
	}
	
	public static Long getCurTime(){
		Date curDate = new Date();
		return curDate.getTime();
	}
	
	/**
	 * 取得当前日期的头一天的日期
	 * @return
	 */
	public static Date getYestoday(){
		Date curDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(cal.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 根据参数日期返回上月的日期，只保证结果为curDate的上月，日期不能保证
	 * @param curDate
	 * @return
	 */
	public static Date rtnLastDate(Date curDate){
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(cal.MONTH, -1);
		return cal.getTime();
	}

	public static Date formatLongToDate(long alongdate){
		Calendar c =  Calendar.getInstance();
		c.setTimeInMillis(alongdate);
		return c.getTime();
	}
	
	
	public static String formatLongToStrDate(long alongdate){
		Calendar c =  Calendar.getInstance();
		c.setTimeInMillis(alongdate);
		return dateToString(c.getTime() , FULL_YMDHMS);
	}
	
	public static Date getDateAfterHour(Date adate , Integer ahour){
		if(adate == null){
			adate = new Date();
		}
		Calendar c =  Calendar.getInstance();
		c.setTime(adate);
		c.add(Calendar.HOUR, ahour);
		Date anothorDate = c.getTime();
		return anothorDate;
		
	}
	
	
	public static Date getDateAfterMin(Date adate , int amin){
		if(adate == null){
			adate = new Date();
		}
		Calendar c =  Calendar.getInstance();
		c.setTime(adate);
		c.add(Calendar.MINUTE, amin);
		Date anothorDate = c.getTime();
		return anothorDate;
		
	}
	
	/**
	 * 获取基于adate日期的day天之前的日期
	 * @param adate
	 * @param day
	 * @return
	 */
	public static Date getDateAfterDay(Date adate , Integer day){
		if(adate == null){
			adate = new Date();
		}
		Calendar c =  Calendar.getInstance();
		c.setTime(adate);
		c.add(Calendar.DAY_OF_YEAR, day);
		Date anothorDate = c.getTime();
		return anothorDate;
		
	}	
	
	public static void main(String[] args){
		DateTools dt = new DateTools();
		Date a = dt.stringToDate("2010-02-20 15:00:00" , FULL_YMDHMS);
		System.out.println(a.before(new Date()));
		
	}
}
