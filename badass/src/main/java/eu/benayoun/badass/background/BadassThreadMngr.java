package eu.benayoun.badass.background;

import android.text.format.DateUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.benayoun.badass.background.badassthread.badassjob.BadassJob;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJobsCtrl;
import eu.benayoun.badass.background.badassthread.schedulemanager.BadassScheduler;
import eu.benayoun.badass.utility.androidsystem.AndroidSystemUtils;
import eu.benayoun.badass.utility.os.time.BadassTimeUtils;
import eu.benayoun.badass.utility.os.time.DurationUtils;
import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 14/01/2018.
 */

public class BadassThreadMngr
{
	static final protected long DEFAULT_MIN_CALL_INTERVAL = DateUtils.HOUR_IN_MILLIS;

	protected BadassJobsCtrl jobsCtrl;
	protected BadassScheduler scheduler;
	ExecutorService executorService;
	Runnable workRunnable;
	boolean isRunning;

	long defaultCallInterval = DEFAULT_MIN_CALL_INTERVAL;

	public BadassThreadMngr(BadassJobsCtrl jobsCtrl)
	{
		this.jobsCtrl = jobsCtrl;
		executorService = Executors.newSingleThreadExecutor();
		workRunnable = new Runnable() {
			@Override
			public void run()
			{
				start();
			}
		};
		scheduler = new BadassScheduler(this);
	}


	public void startThread()
	{
		if (AndroidSystemUtils.thisThreadIsUIThread())
		{
			goInBackgroundAndStart();
		}
		else // Already in background
		{
			start();
		}
	}

	public boolean isRunning()
	{
		return isRunning;
	}

	public long getDefaultCallInterval()
	{
		return defaultCallInterval;
	}

	public void setDefaultCallInterval(long defaultCallInterval)
	{
		this.defaultCallInterval = defaultCallInterval;
	}

	public void scheduleNextCall()
    {
        scheduler.setNextSession(jobsCtrl.getNextStartInMs());
    }



	/**
	 * INTERNAL COOKING
	 */

	protected void goInBackgroundAndStart()
	{
		executorService.execute(workRunnable);
	}

	protected void start()
	{
		if (isRunning == false)
		{
			// PREPARE
			long currentTimeInMs = BadassTimeUtils.getCurrentTimeInMs();
			scheduler.setNextSession(currentTimeInMs+ defaultCallInterval);

			// WORK
			isRunning = true;
			jobsCtrl.startJobs();

			// AFTER
			currentTimeInMs = BadassTimeUtils.getCurrentTimeInMs();
			long nextSessionInMs = jobsCtrl.getNextStartInMs();
			if (nextSessionInMs == BadassJob.NEVER_CALL_TIME_IN_MS)
			{
                BadassLog.logInFile("## No task Left. No call scheduled");
			}
			else
			{
                BadassLog.logInFile("## No task Left. Next task calls: " + jobsCtrl.getStatusAnalysis()+". Sooner in: " + DurationUtils.getDurationStringWithSecs(nextSessionInMs - currentTimeInMs));
			}

			scheduler.setNextSession(nextSessionInMs);
			isRunning = false;
		}
		else
		{
			BadassLog.logInFile("##! CAN'T start, already working");
		}
	}
}
