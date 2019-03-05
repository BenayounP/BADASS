package eu.benayoun.badass.background.badassthread.manager;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateUtils;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJob;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJobsCtrl;
import eu.benayoun.badass.background.badassthread.schedulemanager.BadassScheduler;
import eu.benayoun.badass.background.badassthread.schedulemanager.ScheduleJobService;
import eu.benayoun.badass.utility.androidsystem.BadassUtilsAndroidSystem;
import eu.benayoun.badass.utility.os.time.BadassUtilsDuration;
import eu.benayoun.badass.utility.os.time.BadassUtilsTime;
import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 14/01/2018.
 */

public class BadassThreadMngr
{
	protected static final long DEFAULT_MIN_CALL_INTERVAL = DateUtils.HOUR_IN_MILLIS;

	protected BadassJobsCtrl jobsCtrl;
	protected BadassScheduler scheduler;
    Handler handler;
	protected Runnable workRunnable;
	protected boolean isRunning;
	protected BadassThreadListener badassThreadListener;

	protected long defaultCallInterval = DEFAULT_MIN_CALL_INTERVAL;

	public BadassThreadMngr(BadassJobsCtrl jobsCtrl, BadassThreadListener badassThreadListener)
	{
		this.jobsCtrl = jobsCtrl;
		this .badassThreadListener = badassThreadListener;
		HandlerThread handlerThread = new HandlerThread("BadassThread");
        handlerThread.start();

        handler = new Handler(handlerThread.getLooper());

		workRunnable = new Runnable() {
			@Override
			public void run()
			{
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
			    workInBackground();
			}
		};
		scheduler = new BadassScheduler(this);
	}

	public void startThread()
    {
        if (BadassUtilsAndroidSystem.thisThreadIsUIThread())
        {
            goInBackgroundAndStart();
        }
        else // Already in background
        {
            workInBackground();
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

    protected void workInBackground()
    {
        if (isRunning == false)
        {
            // PREPARE
            long currentTimeInMs = BadassUtilsTime.getCurrentTimeInMs();
            scheduler.setNextSession(currentTimeInMs+ defaultCallInterval);

            // WORK
            isRunning = true;
            badassThreadListener.onThreadStart();

            jobsCtrl.startJobs();

            // AFTER
            currentTimeInMs = BadassUtilsTime.getCurrentTimeInMs();
            long nextSessionInMs = jobsCtrl.getNextStartInMs();
            if (nextSessionInMs == BadassJob.NEVER_CALL_TIME_IN_MS)
            {
                BadassLog.verboseLogInFile("## No task Left. No call scheduled");
            }
            else
            {
                BadassLog.verboseLogInFile("## No task Left. State:\n" + jobsCtrl.getCompleteStatusAnalysis()+"\nNext thread in: " + BadassUtilsDuration.getDurationStringWithSecs(nextSessionInMs - currentTimeInMs));
            }

            scheduler.setNextSession(nextSessionInMs);
            isRunning = false;
            badassThreadListener.onThreadEnd();
           warnEventualScheduleJobService();
        }
        else
        {
            BadassLog.verboseLogInFile("##! badass thread don't need to startWorkInBackground, already running");
        }
    }

    protected void goInBackgroundAndStart()
	{
        handler.post(workRunnable);
	}

	protected void warnEventualScheduleJobService()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            LocalBroadcastManager.getInstance(Badass.getApplicationContext()).sendBroadcast(new Intent(ScheduleJobService.SCHEDULE_JOB_SERVICE_INTENT));
        }
    }


}
