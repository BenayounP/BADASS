package eu.benayoun.badass.background.androidevents.screen;

/**
 * Created by PierreB on 11/09/2016.
 */
public class ScreenActivityReceiver
{
	private BadassScreenActivityListenerContract screenActivityListener;
	private ScreenOnReceiver screenOnReceiver;
	private ScreenOffReceiver screenOffReceiver;

	public ScreenActivityReceiver(BadassScreenActivityListenerContract screenActivityListener)
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
