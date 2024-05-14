package com.zzg.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <p>
 * 日期操作工具类
 * </p>
 *
 */
public class DateUtil {
	/**
	 * 
	 * 格式化日期
	 * @param sdate
	 *            日期字符串
	 * @param format
	 *            要格式化的日期格式
	 * @return 格式化后的日期字符串
	 */
	public static String format(String sdate, String format) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(format);
		try {
			date = df.parse(sdate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return df.format(date);
	}

	/**  
	* @Title: stringToNewFormat  
	* @Description: 转化为新的日期字符串
	* @param: @param sdate
	* @param: @param oldFormat
	* @param: @param newFormat
	* @param: @return  
	* @return: java.lang.String
	* @date: 2019年2月1日 下午2:42:19
	*/
	public static String stringToNewFormat(String sdate, String oldFormat, String newFormat) {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(oldFormat);
		try {
			date = df.parse(sdate);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		 df = new SimpleDateFormat(newFormat);
		return df.format(date);
	}
	
	
	/**
	 * 
	 * 一个日期是否是指定的日期格式
	 * @param dateStr
	 * 
	 *            日期字符串
	 * @param pattern
	 *            验证的日期格式
	 * @return 是否是指定的日期格式
	 */
	public static boolean isValidDate(String dateStr, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			df.setLenient(false);
			df.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 将字符串按指定的格式转换为日期类型
	 * @param str
	 *            日期字符串
	 * @param format
	 *            指定格式
	 * @return 格式化后的日期对象
	 */
	public static Date strToDate(String str, String format) {
		SimpleDateFormat dtFormat = null;
		try {
			dtFormat = new SimpleDateFormat(format);
			return dtFormat.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对一个日期进行偏移
	 * @param date
	 *            日期
	 * @param offset
	 *            偏移两
	 * @return 偏移后的日期
	 */
	public static Date addDayByDate(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_YEAR);
		cal.set(Calendar.DAY_OF_YEAR, day + offset);
		return cal.getTime();
	}

	/**
	 * 
	 * 将日期格式化为<字符串类型>
	 */
	public static String dataToStr(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 得到当前日期<字符串类型>
	 */
	public static String getCurrDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前日期<字符串类型>
	 */
	public static String getCurrDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前日期<java.util.Date类型>
	 * @param dateFormat
	 *            日期格式
	 * @return 当前日期<java.util.Date类型>
	 */
	public static Date getCurrentDate(String dateFormat) {
		return strToDate(getCurrDate(dateFormat), dateFormat);
	}

	/**
	 * 将一个日期转换为指定格式的日期类型
	 * @param date
	 *            要转换的日期
	 * @param dateFormat
	 *            日期格式
	 * @return 转换后的日期对象
	 */
	public static Date formatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return strToDate(sdf.format(date), dateFormat);
	}

	/**
	 * 对格式为20080101类型的字符串进行日期格式化
	 * @param dateStr
	 *            要格式化的字符串
	 * @param formatChar
	 *            连接字符
	 * @param dateFormat
	 *            日期格式
	 * @return 格式后的日期字符串
	 */
	public static String format(String dateStr, String formatChar,String dateFormat) {
		try {
			dateStr = dateStr.substring(0, 4) + formatChar
			+ dateStr.substring(4, 6) + formatChar
			+ dateStr.substring(6, 8);
			return format(dateStr, dateFormat);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 对格式为20080101类型的字符串进行日期格式化
	 * @param dateStr
	 *            要格式化的字符串
	 * @param formatChar
	 *            连接字符
	 * @param dateFormat
	 *            日期格式
	 * @return 格式后的日期对象
	 */
	public static Date formatDate(String dateStr, String formatChar,String dateFormat) {
		try {
			dateStr = dateStr.substring(0, 4) + formatChar
			+ dateStr.substring(4, 6) + formatChar
			+ dateStr.substring(6, 8);
			return strToDate(dateStr, dateFormat);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得某一个月份的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayByMonth(Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return formatDate(gc.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获得某一个月份的最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDayByMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 设为当前月的1号
		cal.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		cal.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return formatDate(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获得指定日期的年份
	 * @param date
	 * @return
	 */
	public static int getYearByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获得指定日期的月份
	 * @param date
	 * @return
	 */
	public static int getMonthByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 获得指定日期的所在月份当前的天数
	 * @param date
	 * @return
	 */
	public static int getDayInMonthByDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获得当前传入日期的上一个月份的当前日期
	 * @param date
	 * @return
	 */
	public static Date getPreviousDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		return formatDate(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获得当前传入日期的下一个月份的当前日期
	 * @param date
	 * @return
	 */
	public static Date getLastMonthDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		return formatDate(cal.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 计算两个日期相差的月数（具体细分到天数的差别）
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDiffMonths(Date date1, Date date2) {
		int iMonth = 0;
		int flag = 0;
		try {
			Calendar objCalendarDate1 = Calendar.getInstance();
			objCalendarDate1.setTime(date1);
			Calendar objCalendarDate2 = Calendar.getInstance();
			objCalendarDate2.setTime(date2);
			if (objCalendarDate2.equals(objCalendarDate1)) {
				return 0;
			}
			if (objCalendarDate1.after(objCalendarDate2)) {
				Calendar temp = objCalendarDate1;
				objCalendarDate1 = objCalendarDate2;
				objCalendarDate2 = temp;
			}
			if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1.get(Calendar.DAY_OF_MONTH)) {
				flag = 1;
			}
			if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1.get(Calendar.YEAR)) {
				iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1.get(Calendar.YEAR))
						* 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
						- objCalendarDate1.get(Calendar.MONTH);
			} else {
				iMonth = objCalendarDate2.get(Calendar.MONTH)- objCalendarDate1.get(Calendar.MONTH) - flag;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iMonth;
	}

	/**
	 * 
	 * 计算两个日期月数的差别，不计算详细到天数的差别
	 * 
	 * 日期大的为参数一，结果为正数，反之为负数
	 * 
	 * @return
	 */
	public static int getDiffMonth(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(date2);
		int diffyaer = calendar1.get(Calendar.YEAR)
				- calendar2.get(Calendar.YEAR);
		int diffmonth = calendar1.get(Calendar.MONTH)
				- calendar2.get(Calendar.MONTH);
		return diffyaer * 12 + diffmonth;
	}
	
	/**
	 * 
	 * 计算两个时间相差的秒数
	 * 
	 * @return
	 */
	public static long getDiffTime(String date1,String date2) {
		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 格式化时间
		try {  
            long result = ((d.parse(date1).getTime() - d.parse(date2) .getTime()) / 1000);	// 这个的除以1000得到秒，相应的60000得到分，3600000得到小时       
            return result;
		} catch (ParseException e) {  
            e.printStackTrace();  
        }
		return 0;
	}
	
	/**
	 * @Title: getDate
	 * @Description: 根据时间字符串获取时间
	 * @param: @param dateStr
	 * @param: @param dateFormat
	 * @param: @return
	 * @return: Date
	 * @date: 2019年6月14日 上午11:11:26
	 */
	public static Date getDate(String dateStr, String dateFormat) {
		if (StringUtils.isEmpty(dateFormat)) {
			dateFormat = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		ParsePosition pos = new ParsePosition(0);
		Date date = formatter.parse(dateStr, pos);
		return date;
	}

	public static String getCurrDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static void main(String[] args){
		Date date = getDate("2019-06-14 11:12:00", null);
		System.out.println(date.toString());
	}
	
	public static long getDifferDays(String srateDate,String endDate){

			Date date1 = strToDate(srateDate,"yyyy-MM-dd HH");
			Date date2 = strToDate(endDate,"yyyy-MM-dd HH");
			long days = (long) ((date2.getTime() - date1.getTime()) / (3600 * 24 * 1000) + 0.5);
			return days+1;

		
	}
	/*
	 * 将2020-08-26 11:12:29格式的日期字符串转换为2020年08月26日
	 */
	public static String format(String sdate){
		sdate = format(sdate,"yyyy-MM-dd");
		sdate = sdate.substring(0, 4)+"年"+sdate.substring(5, 7)+"月"+sdate.substring(8)+"日";
		return sdate;
	}
	
}

