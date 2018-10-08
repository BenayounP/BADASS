package eu.benayoun.badass.background.androidevents;

import eu.benayoun.badass.background.androidevents.internetconnectivity.BadassInternetConnectivityListenerContract;
import eu.benayoun.badass.background.androidevents.internetconnectivity.InternetConnectivityReceiver;
import eu.benayoun.badass.background.androidevents.screen.BadassScreenActivityListenerContract;
import eu.benayoun.badass.background.androidevents.screen.ScreenActivityReceiver;

/**
 * Created by PierreB on 13/03/2018.
 */

public class AndroidEventsCtrl
{
	private InternetConnectivityReceiver internetConnectivityReceiver;
	private ScreenActivityReceiver       screenActivityReceiver;

	public void listenToInternetConnectivity(BadassInternetConnectivityListenerContract internetConnectivityListener)
	{
		internetConnectivityReceiver = new InternetConnectivityReceiver(internetConnectivityListener);
	}

	public void listenToScreenActivity(BadassScreenActivityListenerContract screenActivityListener)
	{
		screenActivityReceiver = new ScreenActivityReceiver(screenActivityListener);
	}

	public boolean isConnectedToInternet()
	{
		return (internetConnectivityReceiver != null && internetConnectivityReceiver.isConnectedToInternet());
	}
}
