package eu.benayoun.badass.background.badassthread.badassjob;


import java.util.ArrayList;

import eu.benayoun.badass.utility.model.ArrayListUtils;
import eu.benayoun.badass.utility.os.time.BadassTimeUtils;


/**
 * Created by PierreB on 14/09/2016.
 */
@SuppressWarnings("ALL")
public class BadassJobsCtrl
{
	protected ArrayList<BadassJob> badassJobList;
	protected BadassJobListContract badassJobListContract;

	public BadassJobsCtrl(BadassJobListContract badassJobListContract)
	{
		this.badassJobListContract = badassJobListContract;
	}


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
			badassJobListContract.onJobListStart();
			while(IsJobsPending())
			{
				for (int i = 0; i < badassJobList.size(); i++)
				{
					badassJobList.get(i).StartIfRequired(BadassTimeUtils.getCurrentTimeInMs());
				}
			}
			badassJobListContract.onJobListEnd();
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

	public String getStatusAnalysis()
	{
		StringBuilder stringBuilder = new StringBuilder();
		BadassJob currentBadassJobCtrlr;
		int           bgndTasksListSize = ArrayListUtils.getSize(badassJobList);
		for (int i = 0; i < bgndTasksListSize; i++)
		{
            stringBuilder.append(badassJobList.get(i).getCompleteStatusString());
		}
		return stringBuilder.toString();
	}

	/**
	 * INTERNAL COOKING
	 */

	protected boolean IsJobsPending()
	{
		boolean thereIsJobsPending = false;
		long    currentTimeInMs = BadassTimeUtils.getCurrentTimeInMs();
		int bgndTasksListSize = ArrayListUtils.getSize(badassJobList);
		for (int i = 0; i < bgndTasksListSize; i++)
		{
			thereIsJobsPending = badassJobList.get(i).isStartRequired(currentTimeInMs);
			if (thereIsJobsPending) break;
		}
		return thereIsJobsPending;
	}
}
