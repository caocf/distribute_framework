package com.appleframework.oss.boss.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TaskUtil {

	/***
	 * 得到执行时间
	 * 
	 * 规则：如果为NA则默认前天，否则取出入的值
	 * @param execType
	 * @return
	 */
	public static String getDate(String dateStr) {
		if (StringBase.CheckStr(dateStr, "NA") || dateStr == null) {
			dateStr = DateUtil.getDateString(DateUtil.getYesterdayDate());
		}
		return dateStr;
	}
	
	/**
	 * 获得指定日期的前一天 lisk
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		String dayBefore = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			try {
				date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - 1);

			dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (Exception e) {

		}
		return dayBefore;
	}
	
	/**
	 * 获得指定日期的前N天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getDayBeforeByDays(String specifiedDay,int d) {
		String dayBefore = "";
		try {
			Calendar c = Calendar.getInstance();
			Date date = null;
			try {
				date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - d);

			dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		} catch (Exception e) {

		}
		return dayBefore;
	}
	
	/***
	 * 计算2个日期之间的天数
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long getQuot(String time1, String time2) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = ft.parse(time1);
			Date date2 = ft.parse(time2);
			quot = date1.getTime() - date2.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return quot;
	}
}
