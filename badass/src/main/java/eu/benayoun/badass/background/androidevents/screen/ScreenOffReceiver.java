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
public class ScreenOffReceiver extends WakefulBroadcastReceiver
{
	private ScreenActivityReceiver screenActivityReceiver;

	public ScreenOffReceiver(ScreenActivityReceiver screenActivityReceiver)
	{
		this.screenActivityReceiver = screenActivityReceiver;
		IntentFilter filter = new IntentFilter("android.intent.action.SCREEN_OFF");
		Badass.getApplicationContext().registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				onScreenOff();
			}
		}, filter);
	}

	@Override
	public void onReceive(Context context, Intent arg1)
	{
		onScreenOff();
	}

	/**
	 * INTERNAL COOKING
	 */

    private void onScreenOff()
	{
		screenActivityReceiver.onScreenOff();
	}
}
