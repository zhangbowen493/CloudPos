package cn.wonders.pos_qdg.util;

/**
 * 时间long值 格式化
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.format.DateFormat;

public class TimerTools {

	public TimerTools() {
		super();
	}

	public static long getTime(String msg){
		LogUtil.e(msg+"开始时间："+System.currentTimeMillis());
		return System.currentTimeMillis();
	}
	public static void timeDuration(long firstTime,String msg){
		LogUtil.e("TIME",msg+"持续时间："+(System.currentTimeMillis()-firstTime));
	}

	/**
	 * 判断当前时间是否在区间内
	 * 
	 * @param start_time
	 *            1：00 格式
	 * @param end_time
	 *            1：00 格式
	 * @return
	 */
	public static boolean isEate(String start_time, String end_time) {
		int year = getYear();
		int month = getMonth();
		int day = getDay();
		int h = getH();
		int uPmonthDays = UPmonthDays();
		int monthDays = monthDays();// 多少天

		String[] start_split = start_time.split(":");
		int startInt = Integer.parseInt(start_split[0]);

		String[] end_split = end_time.split(":");
		int endInt = Integer.parseInt(end_split[0]);
		Long now = now();
		String start_Str;
		String end_Str;
		if (startInt > endInt) {
			if (h >= startInt) {
				start_Str = year + "-" + month + "-" + day + " " + start_time;
				day += 1;
				if (day > monthDays) {
					month += 1;
					day = 1;
					if (month > 12) {
						year += 1;
						month = 1;
					}
				}
				end_Str = year + "-" + month + "-" + day + " " + end_time;
			} else {
				end_Str = year + "-" + month + "-" + day + " " + end_time;
				int daytemp = day - 1;
				if (daytemp < 1) {
					int monthTemp = month - 1;
					if (monthTemp < 1) {
						year -= 1;
						month = 12;
					} else {
						month -= 1;
						day = uPmonthDays;
					}
				} else {
					day -= 1;
				}
				start_Str = year + "-" + month + "-" + day + " " + start_time;
			}

		} else {
			start_Str = year + "-" + month + "-" + day + " " + start_time;
			end_Str = year + "-" + month + "-" + day + " " + end_time;
		}

		if (start_Str != null && end_Str != null) {
			long start_Long = date2long(start_Str, "yyyy-MM-dd HH:mm");
			long end_Long = date2long(end_Str, "yyyy-MM-dd HH:mm");
			long dayL = 1000 * 60 * 60 * 24;
			//			LogUtil.i("start_Str=" + start_Str);
			//			LogUtil.i("当前=" + getDateFormat(now + "", "yyyy-MM-dd HH:mm"));
			//			LogUtil.i("end_Str=" + end_Str);

			if ((start_Long < now && now < end_Long)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Date d = new Date();
	// d.setTime(fromtime * 1000);
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss",
	// Locale.CHINA);
	// sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	public static long oneMonth = monthDays() * 24 * 60 * 60;

	/**
	 * 返回当前年份
	 * 
	 * @return
	 */
	public static int getYear() {
		Date date = new Date();
		String year = new SimpleDateFormat("yyyy").format(date);
		return Integer.parseInt(year);
	}

	/**
	 * 返回当前年份
	 * 
	 * @return
	 */
	public static String getYearStr() {
		Date date = new Date();
		String year = new SimpleDateFormat("yyyy").format(date);
		return year;
	}

	/**
	 * 返回当前月份
	 * 
	 * @return
	 */
	public static int getMonth() {
		Date date = new Date();
		String month = new SimpleDateFormat("MM").format(date);
		return Integer.parseInt(month);
	}

	/**
	 * 返回当前日期
	 * 
	 * @return
	 */
	public static int getDay() {
		Date date = new Date();
		String day = new SimpleDateFormat("dd").format(date);
		return Integer.parseInt(day);
	}

	/**
	 * 返回当前小时
	 * 
	 * @return
	 */
	public static int getH() {
		Date date = new Date();
		String day = new SimpleDateFormat("HH").format(date);
		return Integer.parseInt(day);
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 当月多少天
	 * 
	 * @return
	 */
	public static int monthDays() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 上月多少天
	 * 
	 * @return
	 */
	public static int UPmonthDays() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为当前月第一天
		c.add(Calendar.DAY_OF_MONTH, -1);// -1天得到上月最后一天
		return c.getTime().getDate();
	}

	/**
	 * 本月第一天
	 * 
	 * @return
	 */
	public static long MonthOfDay() {
		// 本月的第一天
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis() + 1000;
	}

	/**
	 * 本月的最后一天
	 * 
	 * @return
	 */
	public static long MonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTimeInMillis();
	}

	/**
	 * 现在
	 * 
	 * @return
	 */
	public static long now() {
		// 现在
		Calendar calendar = new GregorianCalendar();
		return calendar.getTimeInMillis();
	}

	/**
	 * 判断是否是今天
	 * 
	 * @param time
	 *            long值时间
	 * @return
	 */
	public static Boolean isToday(String time) {
		if (time != null) {
			String now = now() + "";
			String dateFormat = getDateFormat(now, "yyyy-mm-dd");
			String dateFormat2 = getDateFormat(time, "yyyy-mm-dd");
			if (dateFormat.equals(dateFormat2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 将2013年9月11日 转换为long类型 秒值
	 * 
	 * @param date
	 * @param dateType
	 *            日期格式 yyyyMMddHHmmss
	 * @return
	 */

	public static long date2long(String date, String dateType) {
		// String sDt = "08-3-6 21时";
		// SimpleDateFormat sdf= new SimpleDateFormat("yy-M-d HH时");
		String sDt = date;
		SimpleDateFormat sdf = new SimpleDateFormat(dateType);
		Date dt2;
		try {
			dt2 = sdf.parse(sDt);
			// 继续转换得到秒数的long型
			long lTime = dt2.getTime();
			return lTime;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回当前时间，String类型的毫秒值 a2 2013-6-7 String
	 * 
	 * @return
	 */
	public static String getdatatimeString() {
		Date d = new Date();
		long longtime = d.getTime();
		String s = String.valueOf(longtime);
		return s;
	}

	/**
	 * 得到当前时间，返回值是格式化后的字符串 a2 2013-6-7 String
	 * 
	 * @return
	 */
	public static String getDateFormat() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(longtime);

	}

	/**
	 * 得到当前时间，返回值是格式化后的字符串 a2 2013-6-7 String
	 * 
	 * @return
	 */
	public static String getDateFormatMd() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		return sdf.format(longtime);

	}

	/**
	 * 得到当前时间，返回值是格式化后的字符串
	 * 
	 * @return
	 */
	public static String getDateFormatMdHm() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmm");
		return sdf.format(longtime);
	}

	/**
	 * 得到当前时间，年月日 六位
	 * 
	 * @return
	 */
	public static String getDateFormatyyMMdd() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(longtime);

	}

	/**
	 * 得到当前时间(hhmmss)，返回值是格式化后的字符串 a2 2013-6-7 String
	 * 
	 * @return
	 */
	public static String getDateFormatHMS() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("hhmmss");
		return sdf.format(longtime);

	}
	/**
	 * 得到当前时间(hhmm)，返回值是格式化后的字符串 a2 2013-6-7 String
	 * 
	 * @return
	 */
	public static String getDateFormatHM() {
		Date d = new Date();
		long longtime = d.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(longtime);

	}
	/**
	 * 判断两个时间的大小
	 * 
	 * @return	1表示前者大，-1表示后者大
	 */
	public static int getConpareTime(String time1,String time2) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");//创建日期转换对象HH:mm为时分
			Date dt1 = df.parse(time1);//将字符串转换为date类型  
			Date dt2 = df.parse(time2);  
			if(dt1.getTime()>dt2.getTime())//比较时间大小,dt1小于dt2  
			{  
				return 1; 
			}else{  
				return -1; 
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0; 
		}
	}

	/**
	 * 制定月的第一天第一秒
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long firstDay(int year, int month) {
		// 本月的第一天第一秒
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		// System.out.println(":::" + calendar.getTimeInMillis() / 1000);
		return calendar.getTimeInMillis();
	}

	/**
	 * 制定月最后一秒
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static long LastDay(int year, int month) {
		// 本月的最后一秒
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, -1);
		// System.out.println(":::" + calendar.getTimeInMillis() / 1000);
		return calendar.getTimeInMillis();
	}

	/**
	 * 将秒值转换为 日期
	 * 
	 * @param time
	 *            秒值
	 * @param type
	 *            日期格式
	 * @return
	 */
	public static String getDateFormat(String time, String type) {
		if (time != null) {

			long longtime = Long.parseLong(time);
			SimpleDateFormat sdf = new SimpleDateFormat(type);
			return sdf.format(longtime);
		}
		return "";
	}

	/**
	 * 返回当前时间，long类型的毫秒值 a2 2013-6-7 Long
	 * 
	 * @return
	 */
	public static Long getdatatimelong() {
		Date d = new Date();
		long longtime = d.getTime();
		return longtime;
	}

	/**
	 * 距离某日多少天
	 * 
	 * @param bzrq
	 * @return
	 */
	public static long getBzTime(String bzrq) {
		if (bzrq == null) {
			return 0;
		}
		long syDay = Long.parseLong(bzrq) - getdatatimelong();
		long syts = syDay / 60 / 60 / 1000 / 24;// 获取今天到指定日期剩余天数
		return syts;
	}

	/**
	 * 判断当前日期是星期几
	 * 
	 * @param time
	 *            设置的需要判断的时间
	 * 
	 * @return week 返回周几
	 */
	public static String getWeek(long time) {
		String Week = "周";

		Calendar c = Calendar.getInstance();
		c.setTime(new Date(time));

		switch (c.get(Calendar.DAY_OF_WEEK)) {
		case 1:
			Week += "日";
			break;
		case 2:
			Week += "一";
			break;
		case 3:
			Week += "二";
			break;
		case 4:
			Week += "三";
			break;
		case 5:
			Week += "四";
			break;
		case 6:
			Week += "五";
			break;
		case 7:
			Week += "六";
			break;

		default:
			break;
		}

		return Week;
	}

	/**
	 * Log日志文件保存使用
	 * 
	 * @return
	 */
	public static String getFileName() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	/**
	 * Log日志文件保存使用
	 * 
	 * @return
	 */
	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;
	}
}
