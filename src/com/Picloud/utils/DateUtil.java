package com.Picloud.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil {

	/**
	 * 将时间按照格式进行转换
	 * @param date 时间
	 * @param format 时间格式
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			result = sdf.format(date);
		}
		return result;
	}

	/**
	 * 将String时间转换成某时间格式
	 * @param str 时间
	 * @param format 格式
	 * @return
	 * @throws Exception
	 */
	public static Date formatString(String str, String format) throws Exception {
		if ((str == null)||(str.equals(""))){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(str);
	}

	/**
	 * 获取当前日期时间，24小时制
	 * @return String类型的时间
	 * @throws Exception
	 */
	public static String getCurrentDateStr() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	
	public static String getCurrentDateMS() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	
	/**
	 * 获取当前时间的前一天
	 * @return
	 */
	public static String getPereviousDayMS() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	
	/**
	 * 获取距离今日的时间统计
	 */
	public static String getLastTime(String lastTime){
		String tLast = lastTime.substring(6, 8);
		String tNow = DateUtil.getCurrentDateStr().substring(6, 8);
		System.out.println(lastTime.substring(6, 8));
		System.out.println(tNow);
		int lastDays = Integer.getInteger(tNow)-Integer.getInteger(tLast);
		if(lastDays > 0){
			return lastDays + "天前";
		}
		else if(lastDays > 30)
			return lastDays + "很久以前";
		else 
			return lastTime;
	}
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}
}


