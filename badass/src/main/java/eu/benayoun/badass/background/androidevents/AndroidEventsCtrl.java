package eu.benayoun.badass.background.androidevents;

import eu.benayoun.badass.background.androidevents.internetconnectivity.InternetConnectivityListenerContract;
import eu.benayoun.badass.background.androidevents.internetconnectivity.InternetConnectivityReceiver;
import eu.benayoun.badass.background.androidevents.screen.ScreenActivityListenerContract;
import eu.benayoun.badass.background.androidevents.screen.ScreenActivityReceiver;

/**
 * Created by PierreB on 13/03/2018.
 */

public class AndroidEventsCtrl
{
	protected InternetConnectivityReceiver internetConnectivityReceiver;
	protected ScreenActivityReceiver       screenActivityReceiver;

	public void listenToInternetConnectivity(InternetConnectivityListenerContract internetConnectivityListener)
	{
		internetConnectivityReceiver = new InternetConnectivityReceiver(internetConnectivityListener);
	}

	public void listenToScreenActivity(ScreenActivityListenerContract screenActivityListener)
	{
		screenActivityReceiver = new ScreenActivityReceiver(screenActivityListener);
	}

	public boolean isConnectedToInternet()
	{
		return (internetConnectivityReceiver != null && internetConnectivityReceiver.isConnectedToInternet());
	}
}
