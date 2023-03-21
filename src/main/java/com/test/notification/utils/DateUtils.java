package com.test.notification.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtils {

	private static Log logger = LogFactory.getLog(DateUtils.class);

	/** The Constant DATE_PARSING_FORMATTING_ERROR_DETECTED. */
	private static final String DATE_PARSING_FORMATTING_ERROR_DETECTED = "Date parsing/formatting error detected";

	/* DayTime format Related */
	/** The Constant DD_SLASH_MM_SLASH_YYYY. */
	public static final String DD_SLASH_MM_SLASH_YYYY = "dd/MM/yyyy";
	public static final String EXCEL_DD_SLASH_MM_SLASH_YYYY = "dd/mm/yyyy";

	/** The Constant DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM. */
	public static final String DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM = "dd/MM/yyyy HH:mm";
	public static final String SQL_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM = "dd/mm/yyyy hh:mi";
	public static final String EXCEL_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM = "dd/mm/yyyy hh:mm";

	/** The Constant DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS. */
	public static final String JAVA_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String SQL_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS = "dd/mm/yyyy hh:mi:ss";
	public static final String EXCEL_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS = "dd/mm/yyyy hh:mm:ss";

	/** The Constant YYYY_MM_DD_HH_MM_SS. */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyyMMddHHmmss";

	/** The Constant HH_COLON_MM. */
	public static final String HH_COLON_MM = "HH:mm";

	/** The Constant YYYYMMDD. */
	public static final String YYYYMMDD = "yyyyMMdd";

	/** The Constant YYYY_SLASH_MM_SLASH_DD. */
	public static final String YYYY_SLASH_MM_SLASH_DD = "yyyy/MM/dd";

	/** The Constant YYYY_SLASH_MM_SLASH_DD_SPACE_HH_COLON_MM. */
	public static final String YYYY_SLASH_MM_SLASH_DD_SPACE_HH_COLON_MM = "yyyy/MM/dd HH:mm";

	/** The Constant YYYY_SLASH_MM_SLASH_DD_SPACE_HH_COLON_MM. */
	public static final String YYYY_SLASH_MM_SLASH_DD_SPACE_HH_COLON_MM_COLON_SS = "yyyy/MM/dd hh:mm:ss";

	public static final String EXCEL_DD_HYPHEN_MMM_HYPHEN_YY_SPACE_HH_COLON_MM = "dd-MMM-yy HH:mm";

	public static final String YYYY_HYPHEN_MM_HYPHEN_DD = "yyyy-MM-dd";

	/** The Constant DATE_FORMAT_YEAR. */
	public static final String DATE_FORMAT_YEAR = "yyyy";
	/** The Constant MONTH_STRING_FORMAT. */
	public static final String MONTH_STRING_FORMAT = "MM";
	/** The Constant DAY_STRING_FORMAT. */
	public static final String DAY_STRING_FORMAT = "dd";
	/** The Constant TIME_ONLY_FORMAT. */
	public static final String TIME_ONLY_FORMAT = "HH:mm";
	/** The Constant DATE_HOUR_MIN_SEC_FORMAT. */
	public static final String DATE_HOUR_MIN_SEC_FORMAT = "dd/MM/yyyy HH:mm:ss";
	/** The Constant DATE_HOUR_MIN_FORMAT. */
	public static final String DATE_HOUR_MIN_FORMAT = "dd/MM/yyyy HH:mm";
	/** The Constant TIME_HOUR_ONLY_FORMAT. */
	public static final String TIME_HOUR_ONLY_FORMAT = "HH";
	/** The Constant TIME_MIN_ONLY_FORMAT. */
	public static final String TIME_MIN_ONLY_FORMAT = "mm";

	/** The Constant ldap time. */
	public static final String LDAP_TIME = "yyyyMMddHHmmss'Z'";

	public static java.util.Date removeTimeInfo(java.util.Date d) {
		if (d == null)
			return null;
		SimpleDateFormat tempF = new SimpleDateFormat("ddMMyyyy");
		try {
			d = tempF.parse(tempF.format(d)); // remove time info
		} catch (Exception ex) {
			logger.error(ex);
			return null;
		} finally {
			tempF = null;
		}
		return d;
	}

	/**
	 * Gets the display date string by format.
	 *
	 * @param t      the t
	 * @param format the format
	 * @return the display date string by format
	 */
	public static String getDisplayDateStringByFormat(Timestamp t, String format) {
		if (t == null || StringUtils.isBlank(format)) {
			return "";
		}
		Date date;
		SimpleDateFormat sdf;
		try {
			date = new Date(t.getTime());
			sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			logger.debug(DATE_PARSING_FORMATTING_ERROR_DETECTED, e);
			return "";
		} finally {
			date = null;
			sdf = null;
		}
	}

	/**
	 * Gets the display date string.
	 *
	 * @param t the t
	 * @return the display date string
	 */
	public static String getDisplayDateString(Timestamp t) {
		if (t == null) {
			return "";
		}
		Date date;
		SimpleDateFormat sdf;
		try {
			date = new Date(t.getTime());
			sdf = new SimpleDateFormat(JAVA_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS);
			return sdf.format(date);
		} catch (Exception e) {
			logger.debug(DATE_PARSING_FORMATTING_ERROR_DETECTED, e);
			return "";
		} finally {
			date = null;
			sdf = null;
		}
	}

	/**
	 * Gets the date without time.
	 *
	 * @param inpuDate the input date
	 * @param format   the format
	 * @return the date without time
	 */
	public static Date getDateWithoutTime(Date inpuDate, String format) {
		SimpleDateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat(format); // "dd/MM/yyyy"
			return formatter.parse(formatter.format(inpuDate));
		} catch (Exception e) {
			logger.debug(DATE_PARSING_FORMATTING_ERROR_DETECTED, e);
			return null;
		} finally {
			formatter = null;
		}
	}

	/**
	 * Gets the time stamp.
	 *
	 * @param d the d
	 * @return the time stamp
	 */
	public static Timestamp getTimeStamp(Date d) {
		return d == null ? null : new java.sql.Timestamp(d.getTime());
	}

	/**
	 * Gets the date only string.
	 *
	 * @param t the t
	 * @return the date only string
	 */
	public static String getDateOnlyString(Timestamp t) {
		if (t == null) {
			return "";
		}
		return new SimpleDateFormat(DD_SLASH_MM_SLASH_YYYY).format(t);
	}

	/**
	 * Gets the time only string.
	 *
	 * @param t the t
	 * @return the time only string
	 */
	public static String getTimeOnlyString(Timestamp t) {
		if (t == null) {
			return "";
		}
		Date date = new Date(t.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_ONLY_FORMAT);
		return sdf.format(date);
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public static Date getCurrentDate() {

		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();

	}

	/**
	 * Gets the current date time.
	 *
	 * @return the current date time
	 */
	public static Timestamp getCurrentDateTime() {
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new Timestamp(date.getTime());
	}

	/**
	 * Gets the today without time.
	 *
	 * @return the current date without time
	 */
	public static Date getCurrentDateWithoutTime() {

		Calendar calendar = Calendar.getInstance();
		Date currentDate = new Date();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	/**
	 * Gets the date time of today dayend i.e 23:59:59
	 *
	 * @return the current date with time in day end
	 */
	public static Date getCurrentDateWithTimeInDayEnd() {
		Calendar calendar = Calendar.getInstance();
		Date currentDate = getCurrentDate();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * Add/ Subtracts day(s) from java.sql.Timestamp object
	 *
	 * @param fromDate  the from date
	 * @param noOfYears the no of years
	 * @return the timestamp
	 */
	public static Timestamp incrementTimeStamptByYear(Timestamp fromDate, int noOfYears) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.YEAR, noOfYears);
		result.setTime(cal.getTimeInMillis());

		return result;
	}

	/**
	 * Add/ Subtracts day(s) from java.sql.Timestamp object
	 *
	 * @param fromDate the from date
	 * @param noOfDays the no of days
	 * @return the timestamp
	 */
	public static Timestamp incrementTimeStamptByDay(Timestamp fromDate, int noOfDays) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.DAY_OF_WEEK, noOfDays);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	/**
	 * Add/ Subtracts day(s) from java.util.date object
	 *
	 * @param date     the date
	 * @param noOfDays the no of days
	 * @return the date
	 */
	public static Date incrementDateByDay(Date date, int noOfDays) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, noOfDays);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	/**
	 * Add/ Subtracts day(s) from java.util.date object
	 *
	 * @param date     the date
	 * @param noOfDays the no of days
	 * @return the date
	 */
	public static Date incrementDateByDayOfYear(Date date, int noOfDays) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, noOfDays);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	/**
	 * Add/ Subtracts day(s) from java.util.date object. Return only the Date
	 * portion.
	 *
	 * @param date     the date
	 * @param noOfDays the no of days
	 * @return the date
	 */
	public static Date incrementDateWithoutTimeByDay(Date date, int noOfDays) {
		Timestamp result = new Timestamp(System.currentTimeMillis());

		Date tmp = incrementDateByDay(date, noOfDays);
		Calendar cal = Calendar.getInstance();
		cal.setTime(tmp);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		result.setTime(cal.getTimeInMillis());

		return result;
	}

	/**
	 * Add/ Subtracts hour(s) from java.util.date object
	 *
	 * @param date the date
	 * @param hour the hour
	 * @return the date
	 */
	public static Date incrementDateByHour(Date date, int hour) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hour);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	/**
	 * Get current time string according specific format.
	 *
	 * @param format the format
	 * @return the current date time str
	 */
	public static String getCurrentDateTimeStr(String format) {
		Date currentTime;
		SimpleDateFormat sdf;
		try {
			currentTime = new Date(System.currentTimeMillis());
			sdf = new SimpleDateFormat(format);
			return sdf.format(currentTime);
		} catch (Exception ex) {
			logger.debug("getCurrentDateTimeStr() have error:", ex);
			return "";
		} finally {
			currentTime = null;
			sdf = null;
		}
	}

	/**
	 * get Datatime string with format.
	 *
	 * @param datetime the datetime
	 * @param format   the format
	 * @return the date time str with format
	 */
	public static String getString(Date datetime, String format) {
		SimpleDateFormat sdf = null;
		try {
			sdf = new SimpleDateFormat(format);
			return sdf.format(datetime);
		} catch (Exception e) {
			logger.debug(DATE_PARSING_FORMATTING_ERROR_DETECTED, e);
			return "";
		} finally {
			sdf = null;
		}
	}

	/**
	 * Convert string to timestamp.
	 *
	 * @param strDate the str date
	 * @param format  the format
	 * @return the timestamp
	 */
	public static Timestamp stringToTimestamp(String strDate, String format) {
		SimpleDateFormat formatter;
		try {
			formatter = new SimpleDateFormat(format);
			// you can change format of date
			Date date = formatter.parse(strDate);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			logger.debug(DATE_PARSING_FORMATTING_ERROR_DETECTED, e);
			return null;
		} finally {
			formatter = null;
		}
	}

	/**
	 * Gets the next date.
	 *
	 * @param dt the dt
	 * @return the next date
	 */
	public static Date getNextDate(Date dt) {
		Calendar c = toCal(dt);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * Gets the previous date.
	 *
	 * @param dt the dt
	 * @return the previous date
	 */
	public static Date getPreviousDate(Date dt) {
		Calendar c = toCal(dt);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	/**
	 * Gets the last week date.
	 *
	 * @param dt the dt
	 * @return the last week date
	 */
	public static Date getLastWeekDate(Date dt) {
		Calendar c = toCal(dt);
		c.add(Calendar.DATE, -7);
		return c.getTime();
	}

	/**
	 * Gets the first date of a month.
	 *
	 * @return the first date of a month
	 */
	public static Date getFirstDateOfMonth(Date currentDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * Gets the first date of previous month.
	 *
	 * @return the first date of previous month
	 */
	public static Date getFirstDateOfPreviousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * Gets the last date of previous month.
	 *
	 * @return the last date of previous month
	 */
	public static Date getLastDateOfPreviousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	/**
	 * add (or minus) milli second to the input Java Util Date and return the new
	 * Java Util Date.
	 *
	 * @param inputDate             the input date
	 * @param millisecondIncreament the millisecond increament
	 * @return the java.util. date
	 */
	public static java.util.Date addMillisecondToJavaUtilDate(java.util.Date inputDate, int millisecondIncreament) {
		java.util.Date resultDate = null;
		Calendar cal;
		try {
			if (inputDate != null) {
				cal = Calendar.getInstance(); // locale-specific
				cal.setTime(inputDate);
				cal.add(Calendar.MILLISECOND, millisecondIncreament);
				resultDate = cal.getTime();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[addMillisecondToJavaUtilDate] inputDate=" + inputDate + ", millisecondIncreament="
						+ millisecondIncreament + ", resultDate=" + resultDate);
			}
			return resultDate;
		} finally {
			cal = null;
		}
	}

	/**
	 * Not null and not after.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean notNullAndNotAfter(Date date1, Date date2) {
		return ObjectUtils.allNotNull(date1, date2) && !date1.after(date2);
	}

	/**
	 * Not null and not before.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean notNullAndNotBefore(Date date1, Date date2) {
		return ObjectUtils.allNotNull(date1, date2) && !date1.before(date2);
	}

	/**
	 * Not null and not equal.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean notNullAndNotEqual(Date date1, Date date2) {
		return ObjectUtils.allNotNull(date1, date2) && !date1.equals(date2);
	}

	/**
	 * Equal or both null
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if equal or both are null
	 */
	public static boolean isEqualOrBothNull(Date date1, Date date2) {
		if (ObjectUtils.anyNotNull(date1, date2) && !ObjectUtils.allNotNull(date1, date2)) {
			return false;
		}
		if (!ObjectUtils.anyNotNull(date1, date2)) {
			return true;
		}
		return date1.compareTo(date2) == 0;
	}

	/**
	 * To local date.
	 *
	 * @param date the date
	 * @return the local date
	 */
	public static LocalDate toLocalDate(Date date) {
		if (date == null) {
			return null;
		}
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	}

	/**
	 * Clear milli seconds.
	 *
	 * @param ts the ts
	 * @return the timestamp
	 */
	public static Timestamp clearMilliSeconds(Timestamp ts) {
		return new Timestamp(ts.getTime() / 1000 * 1000); // sonarqube said to remove the Math.floor
	}

	/**
	 * To local date.
	 *
	 * @param dateOnlyStr the date only str
	 * @return the local date
	 */
	public static LocalDate toLocalDate(String dateOnlyStr) {
		if (StringUtils.isBlank(dateOnlyStr)) {
			return null;
		}
		try {
			return LocalDate.parse(dateOnlyStr, DateTimeFormatter.ofPattern(DD_SLASH_MM_SLASH_YYYY));
		} catch (DateTimeParseException e) {
			logger.debug("Unable to parse", e);
		}
		return null;
	}

	/**
	 * Not null and not after today.
	 *
	 * @param date the date
	 * @return true, if successful
	 */
	public static boolean notNullAndNotAfterToday(Date date) {
		return notNullAndNotAfter(date, getCurrentDateWithoutTime());
	}

	/**
	 * Not null and not before today.
	 *
	 * @param date the date
	 * @return true, if successful
	 */
	public static boolean notNullAndNotBeforeToday(Date date) {
		return notNullAndNotBefore(date, getCurrentDateWithoutTime());
	}

	/**
	 * Gets the simple date format.
	 *
	 * @return SimpleDateFormat lDAP simpledate format
	 */
	public static SimpleDateFormat getLDAPSimpledateFormat() {
		SimpleDateFormat sdf = new SimpleDateFormat(LDAP_TIME);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf;
	}

	/**
	 * Format.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String format(Object value) {
		return value == null ? "" : getSimpleDateFormat(DD_SLASH_MM_SLASH_YYYY, Locale.US).format(value);
	}

	/**
	 * Format.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String format(Date value) {
		return value == null ? "" : getSimpleDateFormat(DD_SLASH_MM_SLASH_YYYY, Locale.US).format(value);
	}

	/**
	 * Format date hour min.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String formatDateHourMin(Date value) {
		return value == null ? ""
				: getSimpleDateFormat(DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM, Locale.US).format(value);
	}

	/**
	 * Format.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String format(String value) {
		try {
			return value == null ? ""
					: format(new Date(getSimpleDateFormat(YYYYMMDD, Locale.US).parse(value).getTime()));
		} catch (Exception e) {
			logger.error("Exception caught in formatYMD() e: ", e);
			return null;
		}
	}

	/**
	 * Format YMD.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String formatYMD(Timestamp value) {
		return value == null ? "" : formatYMD(getDate(value));
	}

	/**
	 * Format YMD.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String formatYMD(Date value) {
		return value == null ? "" : getSimpleDateFormat(YYYYMMDD, Locale.US).format(value);
	}

	/**
	 * Format with pattern.
	 *
	 * @param value  the value
	 * @param format the format
	 * @return the string
	 */
	public static String formatWithPattern(Date value, String format) {
		return value == null ? "" : getSimpleDateFormat(format, Locale.US).format(value);
	}

	/**
	 * Time format.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String timeFormat(Date value) {
		return value == null ? ""
				: getSimpleDateFormat(JAVA_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS, Locale.US).format(value);
	}

	/**
	 * Time only format.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String timeOnlyFormat(Date value) {
		return value == null ? "" : getSimpleDateFormat(HH_COLON_MM, Locale.US).format(value);
	}

	/**
	 * Gets the date.
	 *
	 * @param ts the ts
	 * @return the date
	 */
	public static Date getDate(Timestamp ts) {
		return ts;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @param d the d
	 * @return the time stamp
	 */
	public static Timestamp getTimestamp(Date d) {
		return d == null ? null : new Timestamp((d).getTime());
	}

	/**
	 * Gets the time only from string.
	 *
	 * @param hhmm the hhmm
	 * @return the time only from string
	 */
	public static Date getTimeOnlyFromString(String hhmm) {
		try {
			return getSimpleDateFormat(HH_COLON_MM, Locale.US).parse(hhmm);
		} catch (Exception e) {
			logger.error("Exception caught in getTimeOnlyFromString() e: ", e);
			return null;
		}
	}

	/**
	 * Method to get DateTime in default "dd/MM/yyyy HH:mm:ss" format.
	 *
	 * @param value the value
	 * @return the date time
	 */
	public static String getDateTime(Date value) {
		return value == null ? ""
				: getSimpleDateFormat(JAVA_DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM_COLON_SS, Locale.US).format(value);
	}

	/**
	 * Method to get DateTime by using the formatter "dd/MM/yyyy HH:mm".
	 *
	 * @param dateStr the date str
	 * @param hours   the hours
	 * @param minutes the minutes
	 * @return null if on error.
	 */
	public static Date getDate(String dateStr, String hours, String minutes) {
		SimpleDateFormat timeFormat = new SimpleDateFormat(DD_SLASH_MM_SLASH_YYYY_SPACE_HH_COLON_MM, Locale.US);
		String str = dateStr + " " + hours + ":" + minutes;
		Date value = null;
		try {
			value = timeFormat.parse(str);
		} catch (ParseException e) {
			logger.error("Exception caught in getDate() e: ", e);
		}
		return value;
	}

	/**
	 * Checks if is valid date string.
	 *
	 * @param dateString the date string
	 * @return true, if is valid date string
	 */
	public static boolean isValidDateString(String dateString) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DD_SLASH_MM_SLASH_YYYY);
			Date d = dateFormat.parse(dateString);
			String validDate = dateFormat.format(d);
			return validDate.equals(dateString);
		} catch (Exception e) {
			logger.error("Exception caught in isValidDateString() e: ", e);
			return false;
		}
	}

	/**
	 * Gets the date.
	 *
	 * @param dateString the date string
	 * @param format     the format
	 * @return the date
	 */
	public static Date getDate(String dateString, String format) {

		if (StringUtils.isBlank(dateString))
			return null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateFormat.setLenient(false);
			return dateFormat.parse(dateString);
		} catch (Exception e) {
			logger.error("Exception caught in getDate() e: ", e);
			return null;
		}
	}

	/**
	 * Gets the date.
	 *
	 * @param dateString the date string
	 * @return the date
	 */
	public static Date getDate(String dateString) {
		return getDate(dateString, DD_SLASH_MM_SLASH_YYYY);
	}

	/**
	 * Gets the timestamp.
	 *
	 * @param dateString the date string
	 * @param strsdf     the strsdf
	 * @return the timestamp
	 */
	public static Timestamp getTimestamp(String dateString, String strsdf) {

		if (StringUtils.isBlank(dateString))
			return null;

		try {
			Date date = getDate(dateString, strsdf);
			if (date != null) {
				return new Timestamp(date.getTime());
			}
		} catch (Exception e) {
			logger.error("Exception caught in getTimestamp() e: ", e);
		}
		return null;
	}

	/**
	 * To cal.
	 *
	 * @param date the date
	 * @return the calendar
	 */
	public static Calendar toCal(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	/**
	 * return current date time string according to the provided format e.g.
	 * "yyyyMMddHHmmss"
	 *
	 * @param format the format
	 * @return the current date string
	 */
	public static String getCurrentDateString(String format) {
		String dateStr = null;
		try {
			Calendar cal = Calendar.getInstance();
			java.util.Date currentDate = cal.getTime();
			dateStr = new SimpleDateFormat(format).format(currentDate);
		} catch (Exception ex) {
			logger.error("Exception caught in getCurrentDateString() e: ", ex);
		}
		return dateStr;
	}

	/**
	 * Gets the simple date format.
	 *
	 * @param inStr  the in str
	 * @param locale the locale
	 * @return the simple date format
	 */
	private static SimpleDateFormat getSimpleDateFormat(String inStr, Locale locale) {
		return new SimpleDateFormat(inStr, locale);
	}

	/**
	 * Convert sql date to string.
	 *
	 * @param date   the date
	 * @param format the date format
	 * @return the string
	 */
	public static String sqlDateToString(java.sql.Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * Date add time(hour:min:sec).
	 *
	 * @param value the value
	 * @return the string
	 */
	public static Timestamp setTimeStampByHourMinSec(Timestamp fromDate, int hourOfDay, int minute, int second) {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, 0);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	public static boolean isSameDay(Date d1, Date d2) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		return fmt.format(d1).equals(fmt.format(d2));
	}

	/**
	 * Get last date of Year
	 * 
	 * @return 31/12/this year 23:59:59.999
	 */
	public static Date getLastDateOfYear() {
		Timestamp result = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		Date currentDate = new Date();
		cal.setTime(currentDate);
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		result.setTime(cal.getTimeInMillis());
		return result;
	}

	/**
	 * Get last two digits of current year
	 *
	 * @return the string
	 */
	public static String getLastTwoDigitsOfCurrYear() {
		return new SimpleDateFormat("yy").format(Calendar.getInstance().getTime());
	}

	/**
	 * Get first Monday of current month
	 *
	 * @return the date
	 */
	public static Date getFirstMondayInMth(int year, int month) {
		return getFirstMondayInWeek(year, month, 1);
	}

	/**
	 * Get first Monday of current week
	 *
	 * @return the date
	 */
	public static Date getFirstMondayInWeek(int year, int month, int week) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, week);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}

}