package com.it.zzg.modules.utils;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	/**
	 * date2比date1多的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) // 同一年
		{
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else // 不同年
		{
			System.out.println("判断day2 - day1 : " + (day2 - day1));
			return day2 - day1;
		}
	}

	/*
	 * 获取月份总天数
	 */
	public static String getAllDate(String date) {
		//int d = Integer.valueOf(date.substring(8));
		int m = Integer.valueOf(date.substring(5, 7));
		int y = Integer.valueOf(date.substring(0, 4));
		int days = getDaysByYearMonth(y, m);
		if(days>9){
			return String.valueOf(days);
		}else{
			return "0"+days;
		}
		
	}

	/**
	 * 获取当月的 天数
	 */
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 根据年 月 获取对应的月份 天数
	 */
	public static int getDaysByYearMonth(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 通过时间秒毫秒数判断两个时间的间隔
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	public static void main(String[] args) {
		/*String dateStr = "2018-07-15";
		String dateStr2 = "2018-07-17";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date2 = format.parse(dateStr2);
			Date date = format.parse(dateStr);

			System.out.println("两个日期的差距：" + differentDays(date, date2));
			System.out.println("两个日期的差距：" + differentDaysByMillisecond(date, date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		String dt ="2018-02-08";
		System.out.println(getAllDate(dt));
	}
}
