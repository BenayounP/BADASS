package eu.benayoun.badass.utility.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.os.time.BadassUtilsTime;



/**
 * Created by Pierre on 20/11/2014.
 */

// ALL IN MILLISECONDS !

public class BadassMsDuration
{
    // ALL IN MILLISECONDS !
    protected long start;
    protected long end;

    protected boolean isDataToSave;

	static final String START_KEY = "_BdsDur_s";
	static final String END_KEY = "_BdsDur_e";

	// CONSTRUCTORS

	public BadassMsDuration()
	{
		start = -1;
		end = -1;
		isDataToSave = false;
	}

	public BadassMsDuration(long start, long end)
	{
		this.start = start;
		this.end = end;
        isDataToSave = true;
	}

	public BadassMsDuration(BadassMsDuration original)
	{
		this.start = original.start;
		this.end = original.end;
        isDataToSave = true;
	}

    // SETTER


    public void setStart(long start)
    {
        this.start = start;
        isDataToSave = true;
    }

    public void setEnd(long end)
    {
        this.end = end;
        isDataToSave = true;
    }

    public void update(BadassMsDuration newBadassMsDuration)
    {
        if (start ==-1) start = newBadassMsDuration.start;
        else start = Math.min(start, newBadassMsDuration.start);
        if (end ==-1) end = newBadassMsDuration.end;
        else end =Math.max(end, newBadassMsDuration.end);
        isDataToSave = true;
    }

    // GETTERS

    public long getStart()
    {
        return start;
    }

    public long getEnd()
    {
        return end;
    }

    public long getDuration()
    {
        return end - start;
    }

    public boolean isEqualTo(BadassMsDuration otherBadassMsDuration)
    {
        boolean isEqualToOther = start == otherBadassMsDuration.start;
        if (isEqualToOther)
        {
            isEqualToOther = end == otherBadassMsDuration.end;
        }
        return isEqualToOther;
    }

    public boolean isAfter(long timeInMs)
    {
        return timeInMs <= start;
    }

    public boolean isBefore(long timeInMs)
    {
        return timeInMs >= end;
    }

    public boolean contains(long timeInMs)
    {
        return timeInMs >= start && timeInMs <= end;
    }

    public int getDurationInHours()
    {
        return (int)(getDuration()/ DateUtils.HOUR_IN_MILLIS);
    }


	// SAVED DATA

    public void save(String key, SharedPreferences.Editor editor)
    {
        if (isDataToSave)
        {
            editor.putLong(key + START_KEY, start);
            editor.putLong(key + END_KEY, end);
            isDataToSave = false;
        }
    }
    public void load(String key, SharedPreferences sharedPreferences)
    {
        start = sharedPreferences.getLong(key+START_KEY,-1);
        end = sharedPreferences.getLong(key+END_KEY,-1);
        isDataToSave = false;
    }

	public void removeSavedData(String key, SharedPreferences.Editor editor)
	{
		editor.remove(key+START_KEY);
		editor.remove(key+END_KEY);
        isDataToSave = true;
	}



	// STRING

    public String toDetailedString()
    {
        Context context = Badass.getApplicationContext();
	    String startTimeString = "-1";
	    if (start !=-1)
	    {
		    startTimeString = BadassUtilsTime.getVerboseDate(start);
	    }
        String sStart = context.getString(R.string.start) + " : " + startTimeString;
	    String endTimeString = "-1";
	    if (end !=-1)
	    {
		    endTimeString = BadassUtilsTime.getVerboseDate(end);
	    }
	    String sEnd = context.getString(R.string.end) + " : " + endTimeString;
        String sDuration = context.getString(R.string.duration) + " : " + (end - start);
        return sStart + "\t" + sEnd + "\t" + sDuration;
    }

    public String toSimpleString()
    {
        Context context = Badass.getApplicationContext();
	    String startTimeString = "-1";
	    if (start !=-1)
	    {
		    startTimeString = BadassUtilsTime.getDateString(start)+"|" + BadassUtilsTime.getTimeString(start);
	    }
        String sStart = context.getString(R.string.start) + ": " + startTimeString;
	    String endTimeString = "-1";
	    if (end !=-1)
	    {
		    endTimeString = BadassUtilsTime.getTimeString(end);
	    }
        String sEnd = context.getString(R.string.end) + ": " + endTimeString;
        return sStart + " " + sEnd ;
    }
}
