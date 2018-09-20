package eu.benayoun.badass.background.androidevents.screen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.WakefulBroadcastReceiver;

import eu.benayoun.badass.Badass;

/**
 * Created by PierreB on 11/09/2016.
 */
public class ScreenOnReceiver extends WakefulBroadcastReceiver
{
	ScreenActivityReceiver screenActivityReceiver;

	public ScreenOnReceiver(ScreenActivityReceiver screenActivityReceiver)
	{
		this.screenActivityReceiver = screenActivityReceiver;
		IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_ON");
		Badass.getApplicationContext().registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				onScreenON();
			}
		}, filter);
	}

	@Override
	public void onReceive(Context context, Intent arg1)
	{
		onScreenON();
	}

	/**
	 * INTERNAL COOKING
	 */

	protected void onScreenON()
	{
		screenActivityReceiver.onScreenOn();
	}
}
