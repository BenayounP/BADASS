package eu.benayoun.badass.background.badassthread.schedulemanager;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.SystemClock;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.background.BadassThreadMngr;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJob;
import eu.benayoun.badass.utility.os.time.BadassTimeUtils;
import eu.benayoun.badass.utility.os.time.DurationUtils;
import eu.benayoun.badass.utility.ui.BadassLog;


/**
 * Created by PierreB on 14/09/2016.
 */
@SuppressWarnings("ALL")
public class BadassScheduler extends BroadcastReceiver
{

	static final protected String BROADCAST_FILTER   = "eu.benayoun.kilometer.mvc.controllers.services.BadassScheduler";

	protected AlarmManager alarmManager;
	protected Class<?> cls;
	protected int JOB_ID;
	BadassThreadMngr badassThreadMngr;

	public BadassScheduler(BadassThreadMngr badassThreadMngr)
	{
		mandatoryInit(badassThreadMngr);
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
		BadassLog.logInFile("##! BadassScheduler OnReceive");
		badassThreadMngr.startThread();
	}


	public void setNextSession(long nextCallInMs)
	{
		long minNextCall = BadassTimeUtils.getCurrentTimeInMs()+ badassThreadMngr.getDefaultCallInterval();
		if (nextCallInMs > minNextCall)
		{
			nextCallInMs = minNextCall;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			manageJobScheduler(nextCallInMs);
		}
		else
		{
			setAlarm(nextCallInMs);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void manageJobScheduler(long  nextCallInMs)
	{
		JobScheduler jobScheduler = (JobScheduler) Badass.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
		if (nextCallInMs != BadassJob.NEVER_CALL_TIME_IN_MS)
		{
			long delayInMs = nextCallInMs - BadassTimeUtils.getCurrentTimeInMs();
			BadassLog.logInFile("## Setjob in " + DurationUtils.getDurationStringWithMs(delayInMs)+ " ("+ BadassTimeUtils.getCompleteDateString(nextCallInMs)+")");
			jobScheduler.schedule(new JobInfo.Builder(ScheduleJobService.getJobServiceId(),
					new ComponentName(Badass.getApplicationContext(),ScheduleJobService.getJobServiceClass()))
					.setMinimumLatency(delayInMs)
					.setPersisted(true)
					.build());
		}
		else
		{
			BadassLog.logInFile("##! JobService Canceled");
			jobScheduler.cancel(JOB_ID);
		}
	}

	/**
	 * INTERNAL COOKING
	 */

	protected void mandatoryInit(BadassThreadMngr badassThreadMngrArg)
	{
		this.badassThreadMngr = badassThreadMngrArg;
		IntentFilter filter = new IntentFilter(BROADCAST_FILTER);
		Badass.getApplicationContext().registerReceiver(this,filter);
	}


	protected void setAlarm(long nextCallInMs)
	{
		long elapsedRealTime= SystemClock.elapsedRealtime();
		if (alarmManager == null)
		{
			alarmManager = (android.app.AlarmManager) Badass.getApplicationContext()
					.getSystemService(Context.ALARM_SERVICE);
		}
		PendingIntent pendingIntent = PendingIntent.getBroadcast(Badass.getApplicationContext(), 0, new Intent(BROADCAST_FILTER), PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager.cancel(pendingIntent);
		if (nextCallInMs != BadassJob.NEVER_CALL_TIME_IN_MS)
		{
			long elapsedDelayInMs = nextCallInMs - SystemClock.elapsedRealtime();
			alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,  +elapsedDelayInMs, pendingIntent);
			BadassLog.logInFile("##! Set alarm in " + DurationUtils.getDurationStringWithSecs(nextCallInMs - BadassTimeUtils.getCurrentTimeInMs()));
		}
		else
		{
			BadassLog.logInFile("##! Alarm canceled");
		}
	}
}
