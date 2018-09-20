package eu.benayoun.badass.utility.os.time;


import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.math.MathUtils;
import eu.benayoun.badass.utility.model.DurationInMs;

/**
 * Created by Pierre on 29/12/2015.
 */
public class DurationUtils
{
	static final int INDEX_DAY  = 0;
	static final int INDEX_HOUR = 1;
	static final int INDEX_MIN  =2;
	static final int INDEX_SEC  =3;

	static public int getHourFromDuration(long milliseconds)
	{
		return (int) (milliseconds / DateUtils.HOUR_IN_MILLIS);
	}

	static public int getMinutesRemainingFromDuration(long milliseconds)
	{
		return (int) ((milliseconds - (getHourFromDuration(milliseconds) * DateUtils.HOUR_IN_MILLIS)) / DateUtils.MINUTE_IN_MILLIS);
	}

	static public long getDurationInMS(int hour, int minutes)
	{
		return hour * DateUtils.HOUR_IN_MILLIS + minutes * DateUtils.MINUTE_IN_MILLIS;
	}

	static public String getNiceDurationString(long totalMS)
	{
		if (totalMS < 2*DateUtils.MINUTE_IN_MILLIS)
		{
			return getDurationStringWithSecs(totalMS);
		}
		else
		{
			return getDurationString(totalMS, R.string.dashes);
		}
	}

	static public DurationInMs getToday()
	{
		long nowInMs = BadassTimeUtils.getCurrentTimeInMs();
		return new DurationInMs(BadassTimeUtils.getStartOfTheDayInMs(nowInMs), BadassTimeUtils.getEndOfDayInMs(nowInMs));
	}

	static public String getRoundedDurationString(long totalMs)
	{
		final int DETAILED = 1;
		final int ROUNDED = 2;
		final int JUST_HALF = 3;
		final int DO_NOT_DISPLAY = 4;

		boolean displayHours = true;
		int minuteDisplayStatus = DETAILED;
		int secondDisplayStatus = DETAILED;

		long day_hour_min_sec[] = new long[4];

		// DAYS
		day_hour_min_sec[INDEX_DAY] = totalMs / DateUtils.DAY_IN_MILLIS;

		if (day_hour_min_sec[INDEX_DAY] != 0)
		{
			if (day_hour_min_sec[INDEX_DAY] > 2)
			{
				displayHours = false;
			}
			minuteDisplayStatus = DO_NOT_DISPLAY;
			secondDisplayStatus = DO_NOT_DISPLAY;
		}

		// HOURS
		if (displayHours)
		{
			long remainder = totalMs - day_hour_min_sec[0] * DateUtils.DAY_IN_MILLIS;
			day_hour_min_sec[INDEX_HOUR] = remainder / DateUtils.HOUR_IN_MILLIS;
			if (day_hour_min_sec[INDEX_HOUR] != 0)
			{
				if (minuteDisplayStatus!= DO_NOT_DISPLAY)
				{
					if (day_hour_min_sec[1] > 3)
					{
						minuteDisplayStatus = JUST_HALF;
					}
					else
					{
						minuteDisplayStatus = ROUNDED;
					}
				}

				secondDisplayStatus = DO_NOT_DISPLAY;
			}

			// MINUTES
			remainder = remainder - day_hour_min_sec[1] * DateUtils.HOUR_IN_MILLIS;
			day_hour_min_sec[INDEX_MIN] = remainder / DateUtils.MINUTE_IN_MILLIS;

			if (minuteDisplayStatus == DO_NOT_DISPLAY)
			{
				day_hour_min_sec[INDEX_MIN]=0;
			}
			switch (minuteDisplayStatus)
			{
				case JUST_HALF:
					day_hour_min_sec = getJustHalf(day_hour_min_sec, INDEX_MIN);
					break;
				case ROUNDED:
					day_hour_min_sec = getRounded(day_hour_min_sec, INDEX_MIN);
					break;
				default: // Detailed minutes
					if (day_hour_min_sec[INDEX_MIN] > 14)
					{
						secondDisplayStatus = DO_NOT_DISPLAY;
					}
					if (day_hour_min_sec[INDEX_MIN] > 5)
					{
						secondDisplayStatus = JUST_HALF;
					}
					break;
			}
			// SECONDS
			day_hour_min_sec[INDEX_SEC] = (remainder - day_hour_min_sec[INDEX_MIN] * DateUtils.MINUTE_IN_MILLIS) / 1000;
			if (secondDisplayStatus == DO_NOT_DISPLAY)
			{
				day_hour_min_sec[INDEX_SEC]=0;
			}
			if (secondDisplayStatus == JUST_HALF)
			{
				day_hour_min_sec = getJustHalf(day_hour_min_sec, INDEX_SEC);
			}
			else if (secondDisplayStatus == ROUNDED)
			{
				day_hour_min_sec = getRounded(day_hour_min_sec, INDEX_SEC);
			}
		}
		return getRoundedString(day_hour_min_sec);
	}

	static long[] getJustHalf(long[] day_hour_min_sec, int index)
	{
		if (day_hour_min_sec[index] < 20)
		{
			day_hour_min_sec[index]=0;
		}
		else if (day_hour_min_sec[index] < 42)
		{
			day_hour_min_sec[index]=30;
		}
		else
		{
			day_hour_min_sec[index]=0;
			day_hour_min_sec[index-1]++;
		}
		return day_hour_min_sec;
	}

	static long[] getRounded(long[] day_hour_min_sec, int index)
	{
		if (day_hour_min_sec[index] < 9)
		{
			day_hour_min_sec[index]=0;
		}
		else if (day_hour_min_sec[index] < 22)
		{
			day_hour_min_sec[index]=15;
		}
		else if (day_hour_min_sec[index] < 38)
		{
			day_hour_min_sec[index]=30;
		}
		else if (day_hour_min_sec[index] < 52)
		{
			day_hour_min_sec[index]=45;
		}
		else
		{
			day_hour_min_sec[index]=0;
			day_hour_min_sec[index-1]++;
		}

		return day_hour_min_sec;
	}

	static String getRoundedString(long[] day_hour_min_sec)
	{
		StringBuilder sBuilder = new StringBuilder();

		// INDEX_DAY
		if (day_hour_min_sec[INDEX_DAY]!=0)
		{
			int day_s_id=R.string.day_initial;
			if (day_hour_min_sec[INDEX_HOUR]==0 && day_hour_min_sec[INDEX_MIN]==0 && day_hour_min_sec[INDEX_SEC]==0)
			{
				if (day_hour_min_sec[INDEX_DAY]==1)
				{
					day_s_id = R.string.day;
				}
				else
				{
					day_s_id = R.string.days;
				}
			}
			sBuilder.append(Long.toString(day_hour_min_sec[INDEX_DAY])).append(" ").append(Badass.getCharSequence(day_s_id));
		}
		// INDEX_HOUR
		if (day_hour_min_sec[INDEX_HOUR]!=0)
		{
			if (day_hour_min_sec[INDEX_DAY]!=0)
			{
				sBuilder.append(" ").append(Badass.getCharSequence(R.string.and)).append(" ");
			}
			int hour_s_id=R.string.hour_little;
			if (day_hour_min_sec[INDEX_DAY]==0 && day_hour_min_sec[INDEX_MIN]==0 && day_hour_min_sec[INDEX_SEC]==0)
			{
				if (day_hour_min_sec[INDEX_HOUR]==1)
				{
					hour_s_id = R.string.hour;
				}
				else
				{
					hour_s_id = R.string.hours;
				}
			}
			sBuilder.append(Long.toString(day_hour_min_sec[INDEX_HOUR])).append(" ").append(Badass.getCharSequence(hour_s_id));
		}
		// MINUTE
		if (day_hour_min_sec[INDEX_MIN]!=0)
		{
			if (day_hour_min_sec[INDEX_DAY] != 0 || day_hour_min_sec[INDEX_HOUR] != 0)
			{
				sBuilder.append(" ");
			}

			int minute_s_id = R.string.minute_little;

			if ((day_hour_min_sec[INDEX_DAY] != 0 || day_hour_min_sec[INDEX_HOUR] != 0) && day_hour_min_sec[INDEX_SEC] == 0)
			{
				minute_s_id = R.string.blank;
			}
			else if (day_hour_min_sec[INDEX_DAY] == 0 && day_hour_min_sec[INDEX_HOUR] == 0 && day_hour_min_sec[INDEX_SEC] == 0)
			{
				if (day_hour_min_sec[INDEX_MIN] == 1)
				{
					minute_s_id = R.string.minute;
				}
				else
				{
					minute_s_id = R.string.minutes;
				}
			}
			sBuilder.append(Long.toString(day_hour_min_sec[INDEX_MIN])).append(" ").append(Badass.getCharSequence(minute_s_id));
		}

		// SECOND
		if (day_hour_min_sec[INDEX_SEC]!=0)
		{
			if (day_hour_min_sec[INDEX_DAY]!=0 || day_hour_min_sec[INDEX_HOUR]!=0 || day_hour_min_sec[INDEX_MIN]!=0)
			{
				sBuilder.append(" ");
			}
			int second_s_id=R.string.blank;
			if (day_hour_min_sec[INDEX_DAY]==0 && day_hour_min_sec[INDEX_HOUR]==0 && day_hour_min_sec[INDEX_MIN]==0)
			{
				if (day_hour_min_sec[INDEX_SEC]==1)
				{
					second_s_id = R.string.second;
				}
				else
				{
					second_s_id = R.string.seconds;
				}
			}
			sBuilder.append(Long.toString(day_hour_min_sec[INDEX_SEC])).append(" ").append(Badass.getCharSequence(second_s_id));
		}
		return sBuilder.toString();
	}

	static final int DISPLAY_NO_SECONDS = 1;
	static final int DISPLAY_SECONDS = 2;
	static final int DISPLAY_MILLISECONDS = 3;

	static public String getDurationString(long totalMilliSeconds, int zeroId)
	{
		return getDurationString(totalMilliSeconds, DISPLAY_NO_SECONDS, zeroId, false);
	}

	static public String getDurationString(long totalMilliSeconds)
	{
		return getDurationString(totalMilliSeconds, DISPLAY_NO_SECONDS, R.string.dashes, false);
	}

	static public String getMultiLineDurationString(long totalMs, int zeroId)
	{
		return getDurationString(totalMs, DISPLAY_NO_SECONDS, zeroId,true);
	}

	static public String getDurationStringWithSecs(long totalMs)
	{
		return getDurationString(totalMs, DISPLAY_SECONDS, R.string.dashes,false);
	}

	static public String getDurationStringWithSecs(long totalMs, int zeroId)
	{
		return getDurationString(totalMs, DISPLAY_SECONDS, zeroId, false);
	}

	static public String getDurationStringWithMs(long totalMs)
	{
		return getDurationString(totalMs, DISPLAY_MILLISECONDS, R.string.dashes,false);
	}


	/**
	 * INTERNAL COOKING
	 */

	static long getDurationFromFirstDayOfMonth(long timeInMs)
	{
		int deltaDays = BadassTimeUtils.getDayOfMonth(timeInMs)-1;
		return getDurationFromDeltaDay(timeInMs, deltaDays);
	}

	static long getDurationFromFirstDayOfWeek(long timeInMs, int firstDayOfWeek)
	{
		int thisDayOfWeek = BadassTimeUtils.getDayOfWeek(timeInMs);
		int deltaDays = MathUtils.classicMod(thisDayOfWeek - firstDayOfWeek, 7);
		return getDurationFromDeltaDay(timeInMs, deltaDays);
	}

	static long getDurationFromDeltaDay(long timeInMs, int deltaDays)
	{
		return timeInMs - BadassTimeUtils.getStartOfTheDayInMs(timeInMs) + deltaDays*DateUtils.DAY_IN_MILLIS;
	}



	static String getDurationString(long totalMs,
	                                int displayMode, int zeroStringId,boolean multiLines)
	{
		StringBuilder sBuilder = new StringBuilder();

		if (totalMs == 0)
		{
			sBuilder.append(Badass.getCharSequence(zeroStringId));
		}
		else
		{
			String separator;
			if (multiLines)
			{
				separator = "\n";
			}
			else
			{
				separator = " ";
			}

			long days      = totalMs / DateUtils.DAY_IN_MILLIS;
			long remainderInMs = totalMs - days * DateUtils.DAY_IN_MILLIS;
			long hours     = remainderInMs / DateUtils.HOUR_IN_MILLIS;
			remainderInMs = remainderInMs - hours * DateUtils.HOUR_IN_MILLIS;
			long minutes    = remainderInMs / DateUtils.MINUTE_IN_MILLIS;
			remainderInMs = remainderInMs - minutes * DateUtils.MINUTE_IN_MILLIS;
			long seconds = remainderInMs / 1000;
			remainderInMs = remainderInMs - seconds*1000;

			if (days != 0)
			{
				sBuilder.append(Long.toString(days)).append(" ").append(Badass.getCharSequence(R.string.day_initial));
			}
			if (hours != 0)
			{
				if (days != 0)
				{
					sBuilder.append(separator);
				}
				sBuilder.append(Long.toString(hours)).append(" ").append(Badass.getCharSequence(R.string.hour_little));
			}
			if (minutes != 0)
			{
				if (days != 0 || hours != 0)
				{
					sBuilder.append(separator);
				}
				sBuilder.append(Long.toString(minutes)).append(" ").append(Badass.getCharSequence(R.string.minute_little));
			}
			if (displayMode == DISPLAY_SECONDS || displayMode == DISPLAY_MILLISECONDS)
			{
				if (displayMode == DISPLAY_SECONDS)
				{
					if (totalMs < 1000)
					{
						seconds=1;
					}
				}
				if (seconds != 0)
				{
					if (days != 0 || hours != 0 || minutes != 0)
					{
						sBuilder.append(separator);
					}
					sBuilder.append(Long.toString(seconds)).append(" ").append(Badass.getCharSequence(R.string.seconds_little));
				}

			}
			if (displayMode == DISPLAY_MILLISECONDS)
			{
					if (days != 0 || hours != 0 || minutes != 0 || seconds != 0)
					{
						sBuilder.append(separator);
					}
					sBuilder.append(Long.toString(remainderInMs)).append(" ").append(Badass.getCharSequence(R.string.milliseconds_little));

			}
			if (totalMs < DateUtils.MINUTE_IN_MILLIS && displayMode == DISPLAY_NO_SECONDS)
			{
				sBuilder.append(Badass.getCharSequence(R.string.inf_to_1_min));
			}
		}
		return sBuilder.toString();
	}
}
