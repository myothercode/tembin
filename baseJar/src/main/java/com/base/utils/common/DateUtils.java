/** Created by flym at 13-3-1 */
package com.base.utils.common;


import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import org.apache.commons.lang.math.IntRange;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.impl.cookie.DateParseException;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 时间工具类，用于封装各个时间对象信息
 *
 * @author flym
 */
public class DateUtils {
	private static final Pattern datePattern = Pattern
			.compile("(?:19|20)[0-9]{2}-(?:0?[1-9]|1[012])-(?:0?[1-9]|[12][0-9]|3[01])");
	private static final Pattern timePattern = Pattern.compile("(?:[01]?[0-9]|2[0-3]):[0-5]?[0-9]:[0-5]?[0-9]");
	private static final Pattern dateTimePattern = Pattern.compile(datePattern.pattern() + " " + timePattern.pattern());
	private static final BiMap<Integer, Integer> dayOfWeekMap;//处理星期几的双向转换

	static {
		ImmutableBiMap.Builder<Integer, Integer> biMap = ImmutableBiMap.builder();
		biMap.put(Calendar.MONDAY, 1);
		biMap.put(Calendar.TUESDAY, 2);
		biMap.put(Calendar.WEDNESDAY, 3);
		biMap.put(Calendar.THURSDAY, 4);
		biMap.put(Calendar.FRIDAY, 5);
		biMap.put(Calendar.SATURDAY, 6);
		biMap.put(Calendar.SUNDAY, 7);
		dayOfWeekMap = biMap.build();
	}

	private static final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		}
	};

	private static final ThreadLocal<DateFormat> timeFormatThreadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};

	private static final ThreadLocal<DateFormat> dateTimeFormatThreadLocal = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	private static final ThreadLocal<DateFormat> dateTimeFormatForHM = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
	};
	private static final ThreadLocal<Calendar> calendarThreadLocal = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			return calendar;
		}
	};

	/** 用于表示日期的常量对象 */
	public static class DateX implements Serializable, Comparable<DateX> {
		private final int year;
		private final int month;
		private final int weekOfMonth;
		private final int dayOfMonth;

		public DateX(int year, int month, int dayOfMonth) {
			this.year = year;
			this.month = month;
			this.dayOfMonth = dayOfMonth;
			weekOfMonth = DateUtils.getWeekOfMonth(DateUtils.buildDate(year, month, dayOfMonth));
		}

		public int getYear() {
			return year;
		}

		public int getMonth() {
			return month;
		}

		public int getWeekOfMonth() {
			return weekOfMonth;
		}

		public int getDayOfMonth() {
			return dayOfMonth;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o == null || getClass() != o.getClass()) {
				return false;
			}

			DateX dateX = (DateX) o;

			if(dayOfMonth != dateX.dayOfMonth) {
				return false;
			}
			if(month != dateX.month) {
				return false;
			}
			if(year != dateX.year) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int result = year;
			result = 31 * result + month;
			result = 31 * result + dayOfMonth;
			return result;
		}

		@Override
		public int compareTo(DateX o) {
			if(year != o.year) {
				return year > o.year ? 1 : -1;
			}
			if(month != o.month) {
				return month > o.month ? 1 : -1;
			}
			if(dayOfMonth != o.dayOfMonth) {
				return dayOfMonth > o.dayOfMonth ? 1 : -1;
			}
			return 0;
		}
	}

	/** 用于表示日期的常量对象 */
	public static class TimeX implements Serializable {
		private final int hour;
		private final int minute;
		private final int second;

		public TimeX(int hour, int minute, int second) {
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		public int getHour() {
			return hour;
		}

		public int getMinute() {
			return minute;
		}

		public int getSecond() {
			return second;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o == null || getClass() != o.getClass()) {
				return false;
			}

			TimeX timeX = (TimeX) o;

			if(hour != timeX.hour) {
				return false;
			}
			if(minute != timeX.minute) {
				return false;
			}
			if(second != timeX.second) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int result = hour;
			result = 31 * result + minute;
			result = 31 * result + second;
			return result;
		}
	}

	/** 用于表示日期时间的常量对象 */
	public static class DateTimeX implements Serializable {
		private final int year;
		private final int month;
		private final int dayOfMonth;
		private final int hour;
		private final int minute;
		private final int second;

		public DateTimeX(int year, int month, int dayOfMonth, int hour, int minute, int second) {
			this.year = year;
			this.month = month;
			this.dayOfMonth = dayOfMonth;
			this.hour = hour;
			this.minute = minute;
			this.second = second;
		}

		public int getYear() {
			return year;
		}

		public int getMonth() {
			return month;
		}

		public int getDayOfMonth() {
			return dayOfMonth;
		}

		public int getHour() {
			return hour;
		}

		public int getMinute() {
			return minute;
		}

		public int getSecond() {
			return second;
		}

		@Override
		public boolean equals(Object o) {
			if(this == o) {
				return true;
			}
			if(o == null || getClass() != o.getClass()) {
				return false;
			}

			DateTimeX dateTimeX = (DateTimeX) o;

			if(dayOfMonth != dateTimeX.dayOfMonth) {
				return false;
			}
			if(hour != dateTimeX.hour) {
				return false;
			}
			if(minute != dateTimeX.minute) {
				return false;
			}
			if(month != dateTimeX.month) {
				return false;
			}
			if(second != dateTimeX.second) {
				return false;
			}
			if(year != dateTimeX.year) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int result = year;
			result = 31 * result + month;
			result = 31 * result + dayOfMonth;
			result = 31 * result + hour;
			result = 31 * result + minute;
			result = 31 * result + second;
			return result;
		}
	}

	/** 转换日期至年月日时分 */
	public static String formatDateHM(Date date) {
		return dateTimeFormatForHM.get().format(date);
	}

	/** 时间转年月日 */
	public static String formatDate(Date date) {
		return dateFormatThreadLocal.get().format(date);
	}

	/** 时间转时分秒 */
	public static String formatTime(Date date) {
		return timeFormatThreadLocal.get().format(date);
	}

	/** 时间转年月日 时分秒 */
	public static String formatDateTime(Date date) {
		return dateTimeFormatThreadLocal.get().format(date);
	}

	/** 时间转指定格式的字符串 */
	public static String format(Date date, String pattern) {
		return DateFormatUtils.format(date, pattern, Locale.CHINA);
	}

	/** 年月日转时间,转换失败时抛出转换异常 */
	public static Date parseDate(String dateStr) throws DateParseException {
		Date d = parseDate(dateStr, null);
		if(d == null) {
			throw new DateParseException(dateStr + " 不能进行转换为Date");
		}
		return d;
	}

	/** 年月日转时间,转换失败时返回默认时间 */
	public static Date parseDate(String dateStr, Date defaultDate) {
		if(!datePattern.matcher(dateStr).matches()) {
			return defaultDate;
		}
		try {
			return dateFormatThreadLocal.get().parse(dateStr);
		} catch(ParseException e) {
			return defaultDate;
		}
	}

	/** 时分秒转时间,转换失败时抛出转换异常 */
	public static Date parseTime(String dateStr) throws DateParseException {
		Date d = parseTime(dateStr, null);
		if(d == null) {
			throw new DateParseException(dateStr + " 不能转换为Time");
		}
		return d;
	}

	/** 时分秒转时间,转换失败时返回默认时间 */
	public static Date parseTime(String dateStr, Date defaultDate) {
		if(!timePattern.matcher(dateStr).matches()) {
			return defaultDate;
		}
		try {
			return timeFormatThreadLocal.get().parse(dateStr);
		} catch(ParseException e) {
			return defaultDate;
		}
	}

	/** 年月日 时分秒转时间,转换失败时抛出转换异常 */
	public static Date parseDateTime(String dateStr) throws DateParseException {
		Date d = parseDateTime(dateStr, null);
		if(d == null) {
			throw new DateParseException(dateStr + " 不能转换为DateTime");
		}
		return d;
	}

	/** 年月日 时分秒转时间,转换失败时返回默认值 */
	public static Date parseDateTime(String dateStr, Date defaultDate) {
		if(!dateTimePattern.matcher(dateStr).matches()) {
			return defaultDate;
		}
		try {
			return dateTimeFormatThreadLocal.get().parse(dateStr);
		} catch(ParseException e) {
			return defaultDate;
		}
	}

	public static Calendar getCalendar(Date date) {
		Calendar calendar = calendarThreadLocal.get();
		calendar.setTime(date);
		return calendar;
	}

	/** 是否是闰年 */
	private static boolean isLeapYear(int year) {
		return year % 400 == 0 || (year % 100 != 0 && year % 4 == 0);
	}

	/**
	 * 时间转为当天 23:59:59
	 *
	 * @deprecated 方法命名应与turnToDateStart相对应, 修正为turnToDateEnd, 请使用turnToDateEnd方法
	 */
	@Deprecated
	public static Date turnToDateLine(Date date) {
		return turnToDateEnd(date);
	}

	/** 时间转为当天 23:59:59 */
	public static Date turnToDateEnd(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/** 时间转为当天 00:00:00 */
	public static Date turnToDateStart(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	/** 时间转为当周第一天 00:00:00 */
	public static Date turnToWeekStart(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		DateX dateX = getDateX(calendar.getTime());
		return buildDateTime(dateX.year, dateX.month, dateX.dayOfMonth, 0, 0, 0);
	}

	/** 时间转为当周最后一天 23:59:59 */
	public static Date turnToWeekEnd(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		DateX dateX = getDateX(calendar.getTime());
		return buildDateTime(dateX.year, dateX.month, dateX.dayOfMonth, 23, 59, 59);
	}

	/** 时间转为当月最后一天 23:59:59 */
	public static Date turnToMonthEnd(Date date) {
		DateX dateX = getDateX(date);
		int month = dateX.month;
		int[] monthDays = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int day = month == 1 ? (isLeapYear(dateX.year) ? 29 : 28) : monthDays[month];
		return buildDateTime(dateX.year, month, day, 23, 59, 59);
	}

	/** 时间转为当月第一天 00:00:00 */
	public static Date turnToMonthStart(Date date) {
		DateX dateX = getDateX(date);
		return buildDateTime(dateX.year, dateX.month, 1, 0, 0, 0);
	}

	/** 转换为明天 */
	public static Date turnToTomorrow(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/** 转换为昨天 */
	public static Date turnToYesterday(Date date) {
		Calendar calendar = getCalendar(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/** 时间对换 开始 结束 */
	public static void swap(Date startDate, Date endDate) {
		if(startDate == null || endDate == null) {
			return;
		}
		if(startDate.after(endDate)) {
			long startDateLong = startDate.getTime();
			startDate.setTime(endDate.getTime());
			endDate.setTime(startDateLong);
		}
	}



	/** 获取指定时间的年份 */
	public static int getFullYear(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	/** 获取指定时间的月份 */
	public static int getMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH);
	}

	/** 获取指定的周 */
	public static int getWeekOfMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/** 获取指定时间所在的月有多少周,从1开始 */
	public static int[] getWeeksInMonth(Date date) {
		Date dateEnd = turnToMonthEnd(date);
		return new IntRange(1, getWeekOfMonth(dateEnd)).toArray();
	}

	/** 获取指定时间的天(按月计) */
	public static int getDayOfMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/** 获取指定的天(按周计),按照中国算法，返回相应的实际数字，星期1为1，星期日为7 */
	public static int getDayOfWeek(Date date) {
		Calendar calendar = getCalendar(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Integer result = dayOfWeekMap.get(dayOfWeek);
		return result == null ? 0 : result;
	}

	/** 获取小时 */
	public static int getHour(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/** 获取分钟 */
	public static int getMinute(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MINUTE);
	}

	/** 获取秒 */
	public static int getSecond(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.SECOND);
	}

	/** 获取日期 */
	public static DateX getDateX(Date date) {
		Calendar calendar = getCalendar(date);
		return new DateX(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
	}

	/** 获取时间 */
	public static TimeX getTimeX(Date date) {
		Calendar calendar = getCalendar(date);
		return new TimeX(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));
	}

	/** 获取日期时间 */
	public static DateTimeX getDateTimeX(Date date) {
		Calendar calendar = getCalendar(date);
		return new DateTimeX(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
				calendar.get(Calendar.SECOND));
	}

	/** 判断两个时间是否是同一天 */
	public static boolean isSameDay(Date aDate, Date bDate) {
		DateX aDateX = getDateX(aDate);
		DateX bDateX = getDateX(bDate);

		return aDateX.equals(bDateX);
	}

	/** 判断指定的时间是否为今天 */
	public static boolean isToday(Date date) {
		return isSameDay(date, new Date());
	}

	/** 根据年,月,日创建时间 */
	public static Date buildDate(int year, int month, int dayOfMonth) {
		Calendar calendar = getCalendar(new Date());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		return calendar.getTime();
	}

	/** 根据年，月，周，星期几创建时间 */
	public static Date buildDate(int year, int month, int weekOfMonth, int dayOfWeek) {
		Calendar calendar = getCalendar(new Date());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeekMap.inverse().get(dayOfWeek));

		return calendar.getTime();
	}

	/** 根据小时,分,秒创建时间 */
	public static Date buildTime(int hour, int minute, int second) {
		Calendar calendar = getCalendar(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/** 根据年,月,日,小时,分,秒创建时间 */
	public static Date buildDateTime(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		Calendar calendar = getCalendar(new Date());
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}

    /**获取指定日期几天后的日期 23:59:59*/
    public static Date addDays(Date date , int add){
        Date d=date==null?new Date():date;
        Calendar calendar = getCalendar(d);
        calendar.add(Calendar.DATE,add);
        return turnToDateEnd(calendar.getTime());
    }
    /**获取指定日期几天前的日期 00:00:00*/
    public static Date subDays(Date date , int add){
        Date d=date==null?new Date():date;
        Calendar calendar = getCalendar(d);
        calendar.add(Calendar.DATE,0-add);
        return turnToDateStart(calendar.getTime());
    }
}
