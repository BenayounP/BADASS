package eu.benayoun.badass.utility.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.os.time.BadassTimeUtils;



/**
 * Created by Pierre on 20/11/2014.
 */
public class DurationInMs
{
    public long startTime;
    public long endTime;

	static final String START_KEY = "_DurationInMs_startTime";
	static final String END_KEY = "_DurationInMs_endTime";

	public DurationInMs()
	{
		startTime = -1;
		endTime = -1;
	}

	public DurationInMs(long startTime, long endTime)
	{
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public DurationInMs(DurationInMs original)
	{
		this.startTime = original.startTime;
		this.endTime = original.endTime;
	}


	// SAVED DATA
    public void load(String globalKey, SharedPreferences sharedPreferences)
    {
        startTime = sharedPreferences.getLong(globalKey+START_KEY,-1);
        endTime = sharedPreferences.getLong(globalKey+END_KEY,-1);
    }

	public void save(String globalKey, SharedPreferences.Editor editor)
	{
		editor.putLong(globalKey+START_KEY,startTime);
		editor.putLong(globalKey+END_KEY,endTime);
	}

	public void removeSavedData(String BufferKey, SharedPreferences.Editor editor)
	{
		editor.remove(BufferKey+START_KEY);
		editor.remove(BufferKey+END_KEY);
	}

	public void union(DurationInMs durationInMs)
	{
		this.startTime = Math.min(startTime,durationInMs.startTime);
		this.endTime=Math.max(endTime,durationInMs.endTime);
	}

    public long getDuration()
    {
        return endTime - startTime;
    }

	public boolean isEqualTo(DurationInMs otherDurationInMs)
	{
		boolean isEqualToOther = startTime == otherDurationInMs.startTime;
		if (isEqualToOther)
		{
			isEqualToOther = endTime == otherDurationInMs.endTime;
		}
		return isEqualToOther;
	}

	public boolean isAfter(long timeInMs)
		{
		return timeInMs <= startTime;
	}

	public boolean isBefore(long timeInMs)
	{
		return timeInMs >= endTime;
	}

	public boolean contains(long timeInMs)
	{
		return timeInMs >= startTime && timeInMs <= endTime;
	}

	public int getDurationInHours()
	{
		return (int)(getDuration()/ DateUtils.HOUR_IN_MILLIS);
	}

	// STRING

    public String toDetailedString()
    {
        Context context = Badass.getApplicationContext();
	    String startTimeString = "-1";
	    if (startTime!=-1)
	    {
		    startTimeString = BadassTimeUtils.getVerboseDate(startTime);
	    }
        String sStart = context.getString(R.string.start) + " : " + startTimeString;
	    String endTimeString = "-1";
	    if (endTime!=-1)
	    {
		    endTimeString = BadassTimeUtils.getVerboseDate(endTime);
	    }
	    String sEnd = context.getString(R.string.end) + " : " + endTimeString;
        String sDuration = context.getString(R.string.duration) + " : " + (endTime - startTime);
        return sStart + "\t" + sEnd + "\t" + sDuration;
    }

    public String toSimpleString()
    {
        Context context = Badass.getApplicationContext();
	    String startTimeString = "-1";
	    if (startTime!=-1)
	    {
		    startTimeString = BadassTimeUtils.getDateString(startTime)+"|" + BadassTimeUtils.getTimeString(startTime);
	    }
        String sStart = context.getString(R.string.start) + ": " + startTimeString;
	    String endTimeString = "-1";
	    if (endTime!=-1)
	    {
		    endTimeString = BadassTimeUtils.getTimeString(endTime);
	    }
        String sEnd = context.getString(R.string.end) + ": " + endTimeString;
        return sStart + " " + sEnd ;
    }
}
