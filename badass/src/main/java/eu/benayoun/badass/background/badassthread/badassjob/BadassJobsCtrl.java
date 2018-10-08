package eu.benayoun.badass.background.badassthread.badassjob;


import java.util.ArrayList;

import eu.benayoun.badass.utility.model.ArrayListUtils;
import eu.benayoun.badass.utility.os.time.BadassUtilsTime;


/**
 * Created by PierreB on 14/09/2016.
 */
@SuppressWarnings("ALL")
public class BadassJobsCtrl
{
	protected ArrayList<BadassJob> badassJobList;

	public BadassJobsCtrl addJob(BadassJob badassJob)
	{
		if (badassJobList ==null)
		{
			badassJobList = new ArrayList<>();
		}
		badassJobList.add(badassJob);
		return this;
	}
	

	public void startJobs()
	{
		if (ArrayListUtils.isNOTNullOrEmpty(badassJobList))
		{
			// WORK IF NECCESSARY
			while(IsJobsPending())
			{
				for (int i = 0; i < badassJobList.size(); i++)
				{
					badassJobList.get(i).StartIfRequired(BadassUtilsTime.getCurrentTimeInMs());
				}
			}
		}
	}


	public long getNextStartInMs()
	{
		long nextCall = Long.MAX_VALUE;
		int bgndTasksListSize = ArrayListUtils.getSize(badassJobList);
		for (int i = 0; i < bgndTasksListSize; i++)
		{
			nextCall = Math.min(nextCall, badassJobList.get(i).getNextStartTimeInMs());
		}
		return nextCall;
	}

	public String getCompleteStatusAnalysis()
	{
		StringBuilder stringBuilder = new StringBuilder();
		BadassJob currentBadassJobCtrlr;
		int           bgndTasksListSize = ArrayListUtils.getSize(badassJobList);
		for (int i = 0; i < bgndTasksListSize; i++)
		{
            BadassJob badassJob = badassJobList.get(i);
            stringBuilder.append(badassJob.getCompleteStatusString());
            if (i!=bgndTasksListSize-1) stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * INTERNAL COOKING
	 */

	protected boolean IsJobsPending()
	{
		boolean thereIsJobsPending = false;
		long    currentTimeInMs = BadassUtilsTime.getCurrentTimeInMs();
		int bgndTasksListSize = ArrayListUtils.getSize(badassJobList);
		for (int i = 0; i < bgndTasksListSize; i++)
		{
			thereIsJobsPending = badassJobList.get(i).isStartRequired(currentTimeInMs);
			if (thereIsJobsPending) break;
		}
		return thereIsJobsPending;
	}
}
