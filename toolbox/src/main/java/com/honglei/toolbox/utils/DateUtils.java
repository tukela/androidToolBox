package com.honglei.toolbox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期类操作工具类
 * @author Andy.Niu
 * @create date 2014-07-16
 * 
 */
public class DateUtils {

	private static final String TAG = "DateUtils";

	private static final SimpleDateFormat[] dateFormats;
	private static final int dateFormat_default = 2;


	private DateUtils() { }


	static
	{
		final String[] possibleDateFormats =
				{
						"EEE, dd MMM yyyy HH:mm:ss z", // RFC_822
						"EEE, dd MMM yyyy HH:mm zzzz",
						"yyyy-MM-dd'T'HH:mm:ssZ",
						"yyyy-MM-dd'T'HH:mm:ss.SSSzzzz", // Blogger Atom feed has millisecs also
						"yyyy-MM-dd'T'HH:mm:sszzzz",
						"yyyy-MM-dd'T'HH:mm:ss z",
						"yyyy-MM-dd'T'HH:mm:ssz", // ISO_8601
						"yyyy-MM-dd'T'HH:mm:ss",
						"yyyy-MM-dd'T'HHmmss.SSSz",
						"yyyy-MM-dd"
				};


		dateFormats = new SimpleDateFormat[possibleDateFormats.length];
		TimeZone gmtTZ = TimeZone.getTimeZone("GMT");


		for (int i = 0; i < possibleDateFormats.length; i++)
		{
    /* TODO: Support other locales? */
			dateFormats[i] = new SimpleDateFormat(possibleDateFormats[i],
					Locale.ENGLISH);


			dateFormats[i].setTimeZone(gmtTZ);
		}
	}


	/**
	 * Parse a date string.  The format of RSS/Atom dates come in many
	 * different forms, so this method is extremely flexible and attempts to
	 * understand many different formats.
	 *
	 * Copied verbatim from Informa 0.7.0-alpha2, ParserUtils.java.
	 *
	 * @param strdate
	 *   Date string to attempt to parse.
	 *
	 * @return
	 *   If successful, returns a {@link Date} object representing the parsed
	 *   date; otherwise, null.
	 */
	public static Date parseDate(String strdate)
	{
		Date result = null;
		strdate = strdate.trim();
		if (strdate.length() > 10) {


// TODO deal with +4:00 (no zero before hour)
			if ((strdate.substring(strdate.length() - 5).indexOf("+") == 0 || strdate
					.substring(strdate.length() - 5).indexOf("-") == 0)
					&& strdate.substring(strdate.length() - 5).indexOf(":") == 2) {


				String sign = strdate.substring(strdate.length() - 5,
						strdate.length() - 4);


				strdate = strdate.substring(0, strdate.length() - 5) + sign + "0"
						+ strdate.substring(strdate.length() - 4);
// logger.debug("CASE1 : new date " + strdate + " ? "
//    + strdate.substring(0, strdate.length() - 5));


			}


			String dateEnd = strdate.substring(strdate.length() - 6);


// try to deal with -05:00 or +02:00 at end of date
// replace with -0500 or +0200
			if ((dateEnd.indexOf("-") == 0 || dateEnd.indexOf("+") == 0)
					&& dateEnd.indexOf(":") == 3) {
// TODO deal with GMT-00:03
				if ("GMT".equals(strdate.substring(strdate.length() - 9, strdate
						.length() - 6))) {
					Log.d(TAG, "General time zone with offset, no change");
				} else {
// continue treatment
					String oldDate = strdate;
					String newEnd = dateEnd.substring(0, 3) + dateEnd.substring(4);
					strdate = oldDate.substring(0, oldDate.length() - 6) + newEnd;
// logger.debug("!!modifying string ->"+strdate);
				}
			}
		}
		int i = 0;
		while (i < dateFormats.length) {
			try {
				result = dateFormats[i].parse(strdate);
// logger.debug("******Parsing Success "+strdate+"->"+result+" with
// "+dateFormats[i].toPattern());
				break;
			} catch (java.text.ParseException eA) {
				i++;
			}
		}


		return result;
	}


	/**
	 * Format a date in a manner that would be most suitable for serialized
	 * storage.
	 *
	 * @param date
	 *   {@link Date} object to format.
	 *
	 * @return
	 *   Robust, formatted date string.
	 */
	public static String formatDate(Date date)
	{
		return dateFormats[dateFormat_default].format(date);
	}

	/**
	 * 获取yyMMdd格式的日期
	 * @param date
	 * @return
	 */
	public static String getDateStrByFlow(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取yyyy年MM月dd日格式的日期
	 * 例：2015年01月01日
	 * @param date
	 * @return
	 */
	public static String getDateStrChina(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取yyyy-MM-dd格式的日期
	 * @param date
	 * @return
	 */
	public static String getDateStrBz(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 获取yyyyMMdd格式的日期
	 * @param date
	 * @return
	 */
	public static String getDateStrByStan(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 将对应字符串转化为日期对象
	 * @param dateStr
	 * @return
	 */
	public static String getDateByStr(String dateStr){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		String timeStr = null;
		try {
			date = sdf.parse(dateStr);
			timeStr = new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStr;
	}
	
	/**
	 * 获取两个时间之间相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException 
	 */
	public static long getDaysBetween(String startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0L;
		}
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ;
		long time1=1l;
		try {
			time1 = sdf.parse(startDate).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
        long time2 = endDate.getTime();
        long diff = time2 - time1;
        long day = diff / (24 * 60 * 60 * 1000);

        //return ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        return day;
    }

	public static String getDateStr(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 输出格式为2014-08
	 * @param dateStr
	 * @return
	 */
	public static String getMonthTime(String dateStr){
		String timeStr = "";
		if(dateStr != null){
			if(dateStr.contains("-")){
				timeStr = dateStr;
			}else{
				timeStr = dateStr.substring(0, 4) + "-" + dateStr.substring(5, 7);
			}
		}
		return timeStr;
	}
	
	/**
	 * 得到当前时间天
	 * @param dateStr
	 * @return
	 */
	public static String getCurrentDay(){
		
		Date dateStr = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(dateStr);
	}
	
	/**
	 * 获取传入指定月数后的日期
	 * @param monthIndex 指定月份数
	 * @param flag 是否指定日期
	 * @param dayIndex 指定月份中的天数
	 * @return
	 */
	public static String getNextMonth(int monthIndex,boolean flag,int dayIndex){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowDate);// 获得当前时间

		if(flag){
			// 日期不变，把时间设定为00：00：00
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 00);
//			cal.set(Calendar.SECOND, 00);
			cal.set(Calendar.DAY_OF_MONTH, dayIndex);
		}
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + monthIndex);
		
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取指定天数的日期
	 * @param date 时间
	 * @param dayIndex 指定月份中的天数
	 * @return
	 */
	public static String getIndexDate(Date date,int dayIndex){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

			// 日期不变，把时间设定为00：00：00
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.MINUTE, 00);
//			cal.set(Calendar.SECOND, 00);
//			cal.set(Calendar.DAY_OF_MONTH, dayIndex);
//		cal.set(Calendar.DAY_OF_MONTH, dayIndex);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + dayIndex);
		
		return sdf.format(cal.getTime());
	}
	
	public static void main(String[] args) {
//		System.out.println(getMonthTime("2014年08月"));
//		System.out.println(getDateByStr("2014-04-06 20:24:17"));
		//2015-03-25 14:32:16.0
//		System.out.println(getDateByStr("2015-03-25 14:32:16.0"));
//		System.out.println(getCurrentDay());
//		System.out.println(getNextMonth(0,false,1));
//		System.out.println(getDateStrChina(new Date()));
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String dateStr = "2015-06-03 14:32:16";
//			Date date = sdf.parse(dateStr);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(date);
////			cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 2);
//			cal.set(Calendar.MINUTE, 0);
//			cal.set(Calendar.HOUR_OF_DAY, 0);
//			cal.set(Calendar.SECOND, 0);
//			Date dateFmt = cal.getTime();
//			System.out.println(dateFmt);
//			System.out.println(sdf.format(dateFmt));
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		System.out.println(getIndexDate(new Date(), -1));
		
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		int[] is = new int[list.size()];
//		is[0] = 10;
	}
	
}
