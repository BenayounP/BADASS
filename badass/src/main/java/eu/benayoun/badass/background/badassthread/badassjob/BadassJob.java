package eu.benayoun.badass.background.badassthread.badassjob;


import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.os.time.BadassTimeUtils;
import eu.benayoun.badass.utility.os.time.DurationUtils;
import eu.benayoun.badass.utility.ui.BadassLog;

import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.Status.RESOLVE_PROBLEM;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.Status.SCHEDULED;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.Status.SLEEPING;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.Status.START_ASAP;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.Status.START_AT_NEXT_CALL;

/**
 * Created by PierreB on 21/05/2017.
 */

public abstract class BadassJob
{
	public enum Status
	{
		SLEEPING,
        RESOLVE_PROBLEM,
        START_ASAP,
        START_AT_NEXT_CALL,
		SCHEDULED
	}

	static public final long NEVER_CALL_TIME_IN_MS = Long.MAX_VALUE;

	// FIELDS

	protected Status status=getStartingStatus();

	protected int globalProblemStringId=-1;
	protected int specificReasonProblemStringId=-1;


	protected long minProblemIntervalTimeInMs                    =2* DateUtils.SECOND_IN_MILLIS;
	protected long maxProblemIntervalTimeInMs                    =DateUtils.HOUR_IN_MILLIS;
	protected long currentIntervalBeforeNextProblemResolutionTry = -1;
	protected long nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;

	// METHODS

	// ABSTRACT
	protected abstract BadassJob.Status getStartingStatus();
	protected abstract void work();

	// PUBLIC

	public String getName()
	{
		return this.getClass().getSimpleName();
	}

	// START

	public boolean isStartRequired(long currentTimeInMs)
	{
		boolean shouldWork=false;
		if (status == START_ASAP) shouldWork = true;
		else if (status == RESOLVE_PROBLEM || status == SCHEDULED)
		{
			shouldWork = currentTimeInMs >= nextStartTimeInMs;
		}
		return  shouldWork;
	}

	public void StartIfRequired(long currentTimeInMs)
	{
		if (isStartRequired(currentTimeInMs) || status == START_AT_NEXT_CALL)
		{
			BadassLog.logInFile("## " + getName() + "  works.");
			work();
		}
	}

	// CHANGE STATUS

	public void askToStartAsap()
	{
		status = START_ASAP;
		currentIntervalBeforeNextProblemResolutionTry = -1;
		nextStartTimeInMs = BadassTimeUtils.getCurrentTimeInMs();
	}

    public void prepareToStartAtNextCall()
    {
        status = START_AT_NEXT_CALL;
        currentIntervalBeforeNextProblemResolutionTry = -1;
        nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;
    }

    public void askToResolveProblem()
    {
        if (status != RESOLVE_PROBLEM)
        {
            status = RESOLVE_PROBLEM;
            currentIntervalBeforeNextProblemResolutionTry = minProblemIntervalTimeInMs;
        }
        else
        {
            currentIntervalBeforeNextProblemResolutionTry *= 4;
            currentIntervalBeforeNextProblemResolutionTry = Math.max(currentIntervalBeforeNextProblemResolutionTry,minProblemIntervalTimeInMs);
            currentIntervalBeforeNextProblemResolutionTry = Math.min(currentIntervalBeforeNextProblemResolutionTry,maxProblemIntervalTimeInMs);
        }
        nextStartTimeInMs = BadassTimeUtils.getCurrentTimeInMs()+ currentIntervalBeforeNextProblemResolutionTry;
        BadassLog.logInFile("##! " + getName() +" On Problem next call in " + DurationUtils.getDurationStringWithSecs(currentIntervalBeforeNextProblemResolutionTry));
    }

    public void goToSleep()
    {
        specificReasonProblemStringId = -1;
        status = SLEEPING;
        currentIntervalBeforeNextProblemResolutionTry = -1;
        nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;
    }

    public void schedule(long nextWorkTimeInMs)
    {
        specificReasonProblemStringId = -1;
        BadassLog.logInFile("## " + getName() + " schedule next working session at " + BadassTimeUtils.getCompleteDateString(nextWorkTimeInMs));
        status = SCHEDULED;
        currentIntervalBeforeNextProblemResolutionTry = -1;
        this.nextStartTimeInMs = nextWorkTimeInMs;
    }


	public void setSpecificProblemStringId(int specificReasonProblemStringId)
	{
		this.specificReasonProblemStringId = specificReasonProblemStringId;
	}

	public void setGlobalProblemStringId(int globalProblemStringId)
	{
		this.globalProblemStringId = globalProblemStringId;
	}



	// GETTERS

	public String getProblemString()
	{
		if (specificReasonProblemStringId == -1) return null;
		else
		{
			StringBuilder stringBuilder = new StringBuilder();
			if (globalProblemStringId!=-1)
			{
				stringBuilder.append(Badass.getCharSequence(globalProblemStringId)).append(Badass.getCharSequence(R.string.colon_with_spaces));
			}
			stringBuilder.append(Badass.getCharSequence(specificReasonProblemStringId));
			if (globalProblemStringId!=-1)
			{
				stringBuilder.append("\n").append(" Next try: ").append(BadassTimeUtils.getTimeString(nextStartTimeInMs));
			}
			return stringBuilder.toString();
		}
	}

	public String getCompleteStatusString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		switch (status)
		{
			case SLEEPING: stringBuilder.append(status.name());break;
			case RESOLVE_PROBLEM:
			{
				stringBuilder.append(status.name()).append(". ");
				if (specificReasonProblemStringId !=-1)
				{
					stringBuilder.append(Badass.getCharSequence(specificReasonProblemStringId)).append(". ");
				}
				stringBuilder.append("Next call in: ").append(DurationUtils.getDurationStringWithSecs(currentIntervalBeforeNextProblemResolutionTry));
			}break;
			case START_ASAP: stringBuilder.append(status.name());break;
			case SCHEDULED: stringBuilder.append(status.name()).append(" at : ").append(BadassTimeUtils.getNiceTimeString(nextStartTimeInMs));break;
		}
		return  stringBuilder.toString();
	}

	public Status getStatus()
	{
		return status;
	}

	public  long getNextStartTimeInMs()
	{
		return nextStartTimeInMs;
	}

}
