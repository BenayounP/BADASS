package eu.benayoun.badass.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.benayoun.badass.Badass;

import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_CREATED;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_DESTROYED;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_PAUSED;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_RESTARTING;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_RESUMED;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_STARTED;
import static eu.benayoun.badass.Badass.ActivityState.ACTIVITY_STOPPED;


/**
 * Created by Pierre on 19/10/2015.
 */
public class BadassActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Badass.setActivityState(ACTIVITY_CREATED);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Badass.setActivityState(ACTIVITY_STARTED);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Badass.setActivityState(ACTIVITY_RESUMED);
	}

	@Override
	protected void onPause()
	{
		Badass.setActivityState(ACTIVITY_PAUSED);
		super.onPause();

	}

	@Override
	protected void onStop()
	{
		Badass.setActivityState(ACTIVITY_STOPPED);
		super.onStop();

	}

	@Override
	protected void onRestart()
	{
        super.onRestart();
		Badass.setActivityState(ACTIVITY_RESTARTING);
	}

	@Override
	protected void onDestroy()
	{
		Badass.setActivityState(ACTIVITY_DESTROYED);
		super.onDestroy();

	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
	                                       String permissions[], int[] grantResults)
	{
		Badass.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
	}
}
