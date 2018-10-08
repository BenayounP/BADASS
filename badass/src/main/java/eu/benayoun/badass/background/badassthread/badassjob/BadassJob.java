package eu.benayoun.badass.background.badassthread.badassjob;


import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.os.time.BadassUtilsTime;
import eu.benayoun.badass.utility.os.time.BadassUtilsDuration;
import eu.benayoun.badass.utility.ui.BadassLog;

import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.State.RESOLVE_PROBLEM;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.State.SCHEDULED;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.State.SLEEPING;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.State.START_ASAP;
import static eu.benayoun.badass.background.badassthread.badassjob.BadassJob.State.START_AT_NEXT_CALL;

/**
 * Created by PierreB on 21/05/2017.
 */

public abstract class BadassJob
{
	public enum State
	{
		SLEEPING,
        RESOLVE_PROBLEM,
        START_ASAP,
        START_AT_NEXT_CALL,
		SCHEDULED
	}

	static public final long NEVER_CALL_TIME_IN_MS = Long.MAX_VALUE;

	// FIELDS

	protected State state = getStartingState();

	protected int globalProblemStringId=-1;
	protected int specificReasonProblemStringId=-1;


	protected long minProblemIntervalTimeInMs                    =2* DateUtils.SECOND_IN_MILLIS;
	protected long maxProblemIntervalTimeInMs                    =DateUtils.HOUR_IN_MILLIS;
	protected long currentIntervalBeforeNextProblemResolutionTry = -1;
	protected long nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;

	// METHODS

	// ABSTRACT
	protected abstract State getStartingState();
	protected abstract void work();

	// PUBLIC

	protected String getName()
	{
		return this.getClass().getSimpleName();
	}

	// START

	public boolean isStartRequired(long currentTimeInMs)
	{
		boolean shouldWork=false;
		if (state == START_ASAP) shouldWork = true;
		else if (state == RESOLVE_PROBLEM || state == SCHEDULED)
		{
			shouldWork = currentTimeInMs >= nextStartTimeInMs;
		}
		return  shouldWork;
	}

	public void StartIfRequired(long currentTimeInMs)
	{
		if (isStartRequired(currentTimeInMs) || state == START_AT_NEXT_CALL)
		{
			BadassLog.verboseLogInFile("## " + getName() + "  on duty.");
			work();
		}
	}

	// CHANGE STATUS

	public void askToStartAsap()
	{
		state = START_ASAP;
		currentIntervalBeforeNextProblemResolutionTry = -1;
		nextStartTimeInMs = BadassUtilsTime.getCurrentTimeInMs();
	}

    public void prepareToStartAtNextCall()
    {
        state = START_AT_NEXT_CALL;
        currentIntervalBeforeNextProblemResolutionTry = -1;
        nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;
    }

    public void askToResolveProblem()
    {
        if (state != RESOLVE_PROBLEM)
        {
            state = RESOLVE_PROBLEM;
            currentIntervalBeforeNextProblemResolutionTry = minProblemIntervalTimeInMs;
        }
        else
        {
            currentIntervalBeforeNextProblemResolutionTry *= 4;
            currentIntervalBeforeNextProblemResolutionTry = Math.max(currentIntervalBeforeNextProblemResolutionTry,minProblemIntervalTimeInMs);
            currentIntervalBeforeNextProblemResolutionTry = Math.min(currentIntervalBeforeNextProblemResolutionTry,maxProblemIntervalTimeInMs);
        }
        nextStartTimeInMs = BadassUtilsTime.getCurrentTimeInMs()+ currentIntervalBeforeNextProblemResolutionTry;
        BadassLog.verboseLogInFile("##! " + getName() +" On Problem next call in " + BadassUtilsDuration.getDurationStringWithSecs(currentIntervalBeforeNextProblemResolutionTry));
    }

    public void goToSleep()
    {
        specificReasonProblemStringId = -1;
        state = SLEEPING;
        currentIntervalBeforeNextProblemResolutionTry = -1;
        nextStartTimeInMs = NEVER_CALL_TIME_IN_MS;
    }

    public void schedule(long nextWorkTimeInMs)
    {
        specificReasonProblemStringId = -1;
        BadassLog.verboseLogInFile("## " + getName() + " schedule next working session at " + BadassUtilsTime.getCompleteDateString(nextWorkTimeInMs));
        state = SCHEDULED;
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
				stringBuilder.append("\n").append(" Next try: ").append(BadassUtilsTime.getTimeString(nextStartTimeInMs));
			}
			return stringBuilder.toString();
		}
	}

	public String getCompleteStatusString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getName()).append(": ");
		switch (state)
		{
			case RESOLVE_PROBLEM:
			{
				stringBuilder.append(state.name());
				if (specificReasonProblemStringId !=-1)
				{
					stringBuilder.append(Badass.getCharSequence(specificReasonProblemStringId));
				}
				stringBuilder.append(". Next call in: ").append(BadassUtilsDuration.getDurationStringWithSecs(currentIntervalBeforeNextProblemResolutionTry));
			}break;
            case SCHEDULED: stringBuilder.append(state.name()).append(" at : ").append(BadassUtilsTime.getNiceTimeString(nextStartTimeInMs));break;
            default: stringBuilder.append(state.name());break;
		}
        stringBuilder.append(". ");
		return  stringBuilder.toString();
	}

	public State getState()
	{
		return state;
	}

	public  long getNextStartTimeInMs()
	{
		return nextStartTimeInMs;
	}

}
