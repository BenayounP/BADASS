package eu.benayoun.badass.background.badassthread.schedulemanager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 03/10/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScheduleJobService extends JobService
{
	protected static final int JOB_ID = 1234567;
	public static final String SCHEDULE_JOB_SERVICE_INTENT= "SCHEDULE_JOB_SERVICE_INTENT";
	JobParameters mJobParameters;

    protected BroadcastReceiver badassThreadMngrMessageReceiver;

	@Override
	public boolean onStartJob(final JobParameters jobParameters)
	{
        mJobParameters=jobParameters;
        badassThreadMngrMessageReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
                jobFinished(mJobParameters, false);
            }
        };
        LocalBroadcastManager
                .getInstance(Badass.getApplicationContext()).registerReceiver(badassThreadMngrMessageReceiver, new IntentFilter(SCHEDULE_JOB_SERVICE_INTENT));
        Badass.launchBadassThread();
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters)
	{
		BadassLog.verboseLogInFile("##! ScheduleJobService onStopJob");
		return false;
	}

	public  static Class<?> getJobServiceClass()
	{
		if (Badass.androidVersionSuperiorOrEqualTo(Build.VERSION_CODES.LOLLIPOP)) return ScheduleJobService.class;
		return null;
	}

    @SuppressWarnings("SameReturnValue")
	public static int getJobServiceId()
	{
		return ScheduleJobService.JOB_ID;
	}
}
