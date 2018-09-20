package eu.benayoun.badass.background.badassthread.schedulemanager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 03/10/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScheduleJobService extends JobService
{
	static final public int JOB_ID = 1234567;

	@Override
	public boolean onStartJob(final JobParameters jobParameters)
	{
		new Thread(new Runnable() {
			@Override
			public void run()
			{
				BadassLog.logInFile("##! ScheduleJobService START");
				Badass.startBadassThread();
				jobFinished(jobParameters,false);
				BadassLog.logInFile("##! ScheduleJobService END");
			}
		}).start();
		return false;
	}

	@Override
	public boolean onStopJob(JobParameters jobParameters)
	{
		BadassLog.logInFile("##! ScheduleJobService STOPPED");
		return false;
	}

	public  static Class<?> getJobServiceClass()
	{
		if (Badass.androidVersionSuperiorOrEqualTo(Build.VERSION_CODES.LOLLIPOP)) return ScheduleJobService.class;
		return null;
	}

	public static int getJobServiceId()
	{
		return ScheduleJobService.JOB_ID;
	}
}
