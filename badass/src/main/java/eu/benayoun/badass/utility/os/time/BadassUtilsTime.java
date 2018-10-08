package eu.benayoun.badass.utility.os.time;


import android.annotation.SuppressLint;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;


public class BadassUtilsTime
{

	// NOTE :
	// "Ms" in methods and vars name means Milliseconds

	static final int DISPLAY_TYPE_ONLY_HOUR = 1;
	static final int DISPLAY_TYPE_HOUR_MINUTE = DISPLAY_TYPE_ONLY_HOUR + 1	;
	static final int DISPLAY_TYPE_HOUR_MINUTE_SECONDS = DISPLAY_TYPE_HOUR_MINUTE+ 1;
	static final int DISPLAY_TYPE_HOUR_MINUTE_SECONDS_MILLISECONDS = DISPLAY_TYPE_HOUR_MINUTE_SECONDS+ 1;


	static protected String		time;

	static final String			ONLY_HOUR_24_FORMAT	= "HH";
	static final String         HOUR_MINUTE_24_FORMAT = "HH:mm";
	static final String         HOUR_MINUTE_SECONDS_24_FORMAT = "HH:mm:ss";
	static final String         HOUR_MINUTE_SECONDS_MILLISECONDS_24_FORMAT = "HH:mm:ss:SSS";
	static final String         HOUR_MINUTE_24_MIDNIGHT_FORMAT = ":mm";
	static final String         HOUR_MINUTE_SECONDS_24_MIDNIGHT_FORMAT = ":mm:ss";
	static final String         HOUR_MINUTE_SECONDS_MILLISECONDS_24_MIDNIGHT_FORMAT = ":mm:ss:SSS";

	static final String			ONLY_HOUR_12_FORMAT	= "hh aa";
	static final String         HOUR_MINUTE_12_FORMAT = "hh:mm aa";
	static final String         HOUR_MINUTE_SECONDS_12_FORMAT = "hh:mm:ss aa";
	static final String         HOUR_MINUTE_SECONDS_MILLISECONDS_12_FORMAT = "kk:mm:ss:SSS aa";



    /*********
     * THE MVP
     *********/
    static public long getCurrentTimeInMs()
    {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }


	/*********************
	 * YEARS, MONTHS, DAYS
	 *********************/

	// YEARS

	static public int getYear()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	static public int getYear(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.YEAR);
	}

	// MONTHS

	static public int getArrayMonth(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.MONTH);
	}


	static public int numberOfDaysInMonth(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	// DAYS

	static public int getDayOfWeek(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	static public int getLocalFirstDayOfWeek()
	{
		return (Calendar.getInstance()).getFirstDayOfWeek();
	}

	static public String getDayOfWeekString(long timeInMs)
	{
		return Badass.getResources().getStringArray(R.array.days_of_week)[getDayOfWeek(timeInMs)-1];
	}
    static public String getDiminutiveDayOfWeekString(long timeInMs)
    {
        return Badass.getResources().getStringArray(R.array.days_of_week_diminutives)[getDayOfWeek(timeInMs)-1];
    }

	static public int getDayOfYear(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}


	static public String getLocalDayName(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
	}


	static public int getDayOfMonth()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	static public int getDayOfMonth(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	static public int getFirstDayOfMonthInYear()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DAY_OF_MONTH));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}


	static public boolean isWeekend()
	{
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	}

	static public boolean isSameDay(long time1, long time2)
	{
		return getStartOfTheDayInMs(time1) == getStartOfTheDayInMs(time2);
	}

	static public boolean isLaterDay(long time1, long time2)
	{
		return getStartOfTheDayInMs(time1) > getStartOfTheDayInMs(time2);
	}

	/*******************
	 * HOURS AND MINUTES
 	 *******************/

	// WITH CALENDAR

	static public void setStartOfTheDay(Calendar cal)
	{
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	}

	static public void setEndOfTheDay(Calendar cal)
	{
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
	}

	static public void setStartOfHour(Calendar cal)
	{
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	}

	static public void setEndOfHour(Calendar cal)
	{
		cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
	}

    /**********************
     * TIME IN MILLISECONDS
     **********************/

	static public boolean isStartOfHour(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		setStartOfHour(tmpCalendar);
		return ( tmpCalendar.getTimeInMillis()== timeInMs);
	}

	static public boolean isEndOfHour(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		setEndOfHour(tmpCalendar);
		return (tmpCalendar.getTimeInMillis() == timeInMs);
	}


	static public boolean isStartOfTheDay(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		setStartOfTheDay(tmpCalendar);
		return ( tmpCalendar.getTimeInMillis()== timeInMs);
	}

	static public boolean isEndOfTheDay(long timeInMs)
	{
		return (getEndOfDayInMs(timeInMs) == timeInMs);
	}


	static public long getEndOfDayInMs(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		setEndOfTheDay(tmpCalendar);
		return tmpCalendar.getTimeInMillis();
	}

	static public int getHourOfDay24(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}


	// FORMAT is 12 or 24
	static public int getFormatedHour(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		if (DateFormat.is24HourFormat(Badass.getApplicationContext()))
		{
			return tmpCalendar.get(Calendar.HOUR_OF_DAY);
		}
		else
		// TIME_FORMAT_12
		{
			return tmpCalendar.get(Calendar.HOUR);
		}
	}

	static public int getMinutesInCurrentHour(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		return tmpCalendar.get(Calendar.MINUTE);
	}

    /*********************
     * OTHER TIME METHODS
     *********************/

	// date
	static public Date getDate(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		return calendar.getTime();
	}

	// age
	static public int getAge(long birthDateInMs)
	{
		Calendar nowCalendar = Calendar.getInstance();
		Calendar birthCalendar = Calendar.getInstance();

		birthCalendar.setTimeInMillis(birthDateInMs);

		int yourAge = nowCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

		birthCalendar.add(Calendar.YEAR, yourAge);
		if (nowCalendar.before(birthCalendar)) {
			yourAge--;
		}

		return yourAge;
	}

	// period of the day like AM or PM
	static public int getTimePeriod(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		return tmpCalendar.get(Calendar.AM_PM);
	}

	static public long changePeriod(long timeInMs)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(timeInMs);
		if (tmpCalendar.get(Calendar.AM_PM) == Calendar.AM)
		{
			tmpCalendar.set(Calendar.AM_PM, Calendar.PM);
		}
		else
		{
			tmpCalendar.set(Calendar.AM_PM, Calendar.AM);
		}

		return tmpCalendar.getTimeInMillis();
	}

    /**********
     * UTC TIME
     **********/

	static public long getUTCOffsetInMs()
	{
		return TimeZone.getDefault().getOffset(getCurrentTimeInMs());
	}

    /***************
     * MILLISECONDS
     ***************/

	static public long getMsFromDayOfWeek(int dayOfWeek)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		return calendar.getTimeInMillis();
	}

	static public long getMsFromCalendar(int dayOfMonth, int month, int year)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTimeInMillis();
	}


	static public long getStartOfDayFromCalendar(int dayOfMonth, int month, int year)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		return getStartOfTheDayInMs(calendar.getTimeInMillis());
	}


	static public long getMsFromHourAndMinute(int formatedHour,
                                              int minute, int timeFormat)
	{
		Calendar calendar = Calendar.getInstance();

		if (DateFormat.is24HourFormat(Badass.getApplicationContext()))
		{
			calendar.set(Calendar.HOUR_OF_DAY, formatedHour);
		}
		else
		{
			calendar.set(Calendar.HOUR, formatedHour);
		}
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTimeInMillis();
	}

	static public long getThisMidnightTime()
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	static public long getStartOfTheDayInMs(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	static public long getEndOfTheDayInMs(long timeInMs)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMs+ DateUtils.DAY_IN_MILLIS);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis()-1;
	}


	// elapsed
	static public long getElapsedTime(long timeInMs)
	{
		return timeInMs - getCurrentTimeInMs()+ SystemClock.elapsedRealtime();
	}

    /*******************
     * NICE TIME STRING
     *******************/

	static public String getCompleteDateString(long timeInMs)
	{
		return BadassUtilsTime.getDateString(timeInMs)+ " : " + getTimeString(timeInMs);
	}

	static public String getVerboseDate(long timeInMs)
	{
		return BadassUtilsTime.getDateString(timeInMs)+ " : " + getTimeStringWithMs(timeInMs);
	}

	static public String getDateString(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);
		java.text.DateFormat df=DateFormat.getDateFormat(Badass.getApplicationContext());
		return df.format(tmpCalendar.getTime());
	}

	static public String getDayAndMonthString(long milliseconds)
	{
		return DateUtils.formatDateTime(Badass.getApplicationContext(), milliseconds, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE);
	}


	static public String getNiceTimeString(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);

		StringBuilder toReturn = new StringBuilder();
		long now = getCurrentTimeInMs();
		int deltaDay = (int)((getStartOfTheDayInMs(milliseconds) - getStartOfTheDayInMs(now)) / DateUtils.DAY_IN_MILLIS);
		boolean notToday = true;
		if (deltaDay==0)
		{
			notToday = false;
		}
		else if (deltaDay == 1)
		{
			toReturn.append(Badass.getCharSequence(R.string.tomorrow));
		}
		else
		{
			toReturn.append(getDateString(milliseconds));
		}
		if (notToday)
		{
			toReturn.append(Badass.getCharSequence(R.string.colon_with_spaces));
		}
		toReturn.append(getTimeString(milliseconds));

		return toReturn.toString();
	}


	static public String getTimeString(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);
		return getTimeString(tmpCalendar);
	}

	static public String getTimeStringOnlyWithHours(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);
		return getTimeStringOnlyWithHours(tmpCalendar);
	}

	static public String getTimeStringWithSecs(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);
		return getTimeStringWithSecs(tmpCalendar);
	}

	static public String getTimeStringWithMs(long milliseconds)
	{
		Calendar tmpCalendar = Calendar.getInstance();
		tmpCalendar.setTimeInMillis(milliseconds);
		return getTimeStringWithMs(tmpCalendar);
	}

	static public String getTimeStringOnlyWithHours(Calendar InCalendar)
	{
		return getTimeStringFromCalendar(InCalendar, DISPLAY_TYPE_ONLY_HOUR);
	}

	static public String getTimeString(Calendar InCalendar)
	{
		return getTimeStringFromCalendar(InCalendar, DISPLAY_TYPE_HOUR_MINUTE);
	}

	static public String getTimeStringWithSecs(Calendar InCalendar)
	{
		return getTimeStringFromCalendar(InCalendar, DISPLAY_TYPE_HOUR_MINUTE_SECONDS);
	}

	static public String getTimeStringWithMs(Calendar InCalendar)
	{
		return getTimeStringFromCalendar(InCalendar, DISPLAY_TYPE_HOUR_MINUTE_SECONDS_MILLISECONDS);
	}


	/**
	 * INTERNAL COOKING
	 */

	@SuppressLint("SimpleDateFormat")
    protected static String getTimeStringFromCalendar(Calendar InCalendar, int displayType)
	{
		SimpleDateFormat sdf;
		StringBuilder sBuilder = new StringBuilder();
		String myFormat;

		if (DateFormat.is24HourFormat(Badass.getApplicationContext()))
		{
			switch(displayType)
			{
				case DISPLAY_TYPE_ONLY_HOUR : myFormat = ONLY_HOUR_24_FORMAT;break;
				default:
				case DISPLAY_TYPE_HOUR_MINUTE : myFormat = HOUR_MINUTE_24_FORMAT;break;
				case DISPLAY_TYPE_HOUR_MINUTE_SECONDS : myFormat = HOUR_MINUTE_SECONDS_24_FORMAT;break;
				case DISPLAY_TYPE_HOUR_MINUTE_SECONDS_MILLISECONDS : myFormat = HOUR_MINUTE_SECONDS_MILLISECONDS_24_FORMAT;break;
			}

			sdf = new SimpleDateFormat(myFormat);
			sBuilder.append(sdf.format(InCalendar.getTime()));

		}
		else
		// TIME_FORMAT_12
		{
			switch(displayType)
			{
				case DISPLAY_TYPE_ONLY_HOUR : myFormat = ONLY_HOUR_12_FORMAT;break;
				default:
				case DISPLAY_TYPE_HOUR_MINUTE : myFormat = HOUR_MINUTE_12_FORMAT;break;
				case DISPLAY_TYPE_HOUR_MINUTE_SECONDS : myFormat = HOUR_MINUTE_SECONDS_12_FORMAT;break;
				case DISPLAY_TYPE_HOUR_MINUTE_SECONDS_MILLISECONDS : myFormat = HOUR_MINUTE_SECONDS_MILLISECONDS_12_FORMAT;break;
			}
			sdf = new SimpleDateFormat(myFormat);
			sBuilder.append(sdf.format(InCalendar.getTime()));
		}
		return sBuilder.toString();
	}
}
