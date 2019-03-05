package eu.benayoun.badass.background.androidevents.internetconnectivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.WakefulBroadcastReceiver;

import eu.benayoun.badass.Badass;


/**
 * Created by PierreB on 11/09/2016.
 */
public class InternetConnectivityReceiver extends WakefulBroadcastReceiver
{
	protected BadassInternetConnectivityListenerContract InternetConnectivityListener;
	protected boolean isCurrentlyConnected;

	public InternetConnectivityReceiver(BadassInternetConnectivityListenerContract InternetConnectivityListener)
	{
		this.InternetConnectivityListener = InternetConnectivityListener;
		IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
		Badass.getApplicationContext().registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent)
			{
				onConnectivityEvent();
			}
		}, filter);
		isCurrentlyConnected = isConnectedToInternet();
	}

	@Override
	public void onReceive(Context context, Intent arg1)
	{
		onConnectivityEvent();
	}

	public boolean isConnectedToInternet()
	{
		ConnectivityManager cm      = (ConnectivityManager) Badass.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		@SuppressLint("MissingPermission") NetworkInfo         netInfo = cm.getActiveNetworkInfo();
		//should check null because in airplane mode it will be null
		return  (netInfo != null && (netInfo.isConnected() || netInfo.isAvailable()));
	}

	/**
	 * INTERNAL COOKING
	 */

    protected void onConnectivityEvent()
	{
		boolean isJustConnectedToInternet = isConnectedToInternet();
		if (isJustConnectedToInternet != isCurrentlyConnected)
		{
			isCurrentlyConnected = isJustConnectedToInternet;
			if (isCurrentlyConnected) InternetConnectivityListener.onConnectedToInternet();
			else InternetConnectivityListener.onDisconnectedToInternet();
		}
	}
}
