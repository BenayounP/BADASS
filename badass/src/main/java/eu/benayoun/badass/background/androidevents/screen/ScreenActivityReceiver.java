package eu.benayoun.badass.background.androidevents.screen;

/**
 * Created by PierreB on 11/09/2016.
 */
public class ScreenActivityReceiver
{
	ScreenActivityListenerContract screenActivityListener;
	ScreenOnReceiver screenOnReceiver;
	ScreenOffReceiver screenOffReceiver;

	public ScreenActivityReceiver(ScreenActivityListenerContract screenActivityListener)
	{
		this.screenActivityListener = screenActivityListener;
		screenOnReceiver = new ScreenOnReceiver(this);
		screenOffReceiver = new ScreenOffReceiver(this);
	}

	public void onScreenOn()
	{
		screenActivityListener.onScreenOn();
	}

	public void onScreenOff()
	{
		screenActivityListener.onScreenOff();
	}
}
