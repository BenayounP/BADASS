package eu.benayoun.badass.ui.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.ui.events.UIEventListenerContract;


/**
 * Created by Pierre on 19/10/2015.
 */
public abstract class NotificationCtrl implements UIEventListenerContract
{
	protected final boolean IS_NOT_ONGOING = false;

	protected final String INTENT_EXTRA_NOTIFICATION_ID = "BADASSNOTIFICATIONMNGR.INTENT_EXTRA_NOTIFICATION_ID";

	protected NotificationCompat.Builder customNotificationBuilder  = null;

	protected NotificationChannelDataContainer notificationChannelDataContainer;

	public void setNotificationChannelDataContainer(String chanelId, String channelName, String chanelDescription)
	{
		notificationChannelDataContainer = new NotificationChannelDataContainer(chanelId, channelName, chanelDescription);
	}

	/**
	 * METHODS
	 */

	// ABSTRACT
	@Override
	abstract public void onEvent(int eventId, long eventTimeInMs);

	// GLOBAL
	protected void cancelNotification(int notification_ID)
	{
		final NotificationManager notificationManager = (NotificationManager) Badass.getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(notification_ID);
	}

	// CLASSIC

	protected void displayClassicAreaNotification(int notification_ID, int stringId, int iconId, int titleId, int colorId, Class mainActivityClass, int launchType)
	{
		displayClassicAreaNotification(notification_ID,Badass.getString(stringId), iconId,titleId,colorId,mainActivityClass,launchType);
	}

	protected void displayClassicAreaNotification(int notification_ID, String mainText, int iconId, int titleId, int colorId, Class mainActivityClass, int launchType)
	{
		Context   context   = Badass.getApplicationContext();
		Resources resources = context.getResources();


		NotificationCompat.Builder	classicNotificationBuilder = new NotificationCompat.Builder(context,notificationChannelDataContainer.channelId)
				.setSmallIcon(iconId)
				.setContentTitle(resources.getText(titleId))
				.setContentText(mainText)
				.setColor(resources.getColor(colorId))
				.setContentIntent(getLaunchActivityIntent(mainActivityClass,launchType))
				.setOngoing(false)
				.setAutoCancel(true);

		NotificationManagerCompat.from(context).notify(
				notification_ID,
				classicNotificationBuilder.build());
	}

	// CUSTOM

	protected void setCustomNotification(int globalNotificationID, int sub_notification_ID, int priority, boolean isOngoing, int iconId, RemoteViews remoteViews, Class mainActivityClass, Class broadcastClass)
	{
		if (remoteViews!=null && iconId!=-1)
		{
			customNotificationBuilder = new NotificationCompat.Builder(Badass.getApplicationContext(), notificationChannelDataContainer.channelId);
			customNotificationBuilder.setDefaults(0);
			customNotificationBuilder.setContentIntent(getLaunchActivityIntent(mainActivityClass, sub_notification_ID));
			customNotificationBuilder.setDeleteIntent(getBroadcastDeleteIntent(broadcastClass, sub_notification_ID));
			customNotificationBuilder.setPriority(priority).setOngoing(isOngoing);
			customNotificationBuilder
					.setSmallIcon(iconId)
					.setContent(remoteViews);


			NotificationManagerCompat.from(Badass.getApplicationContext()).notify(
					globalNotificationID,
					customNotificationBuilder.build());
		}
		else
		{
			if (remoteViews==null) Badass.logInFile("%%! setCustomNotification: remoteViews == null");
			if (iconId==-1) Badass.logInFile("%%! setCustomNotification: iconId==0");
		}
	}


	protected PendingIntent getLaunchActivityIntent(Class mainActivityClass, int notificationID)
	{
		Context   context   = Badass.getApplicationContext();
		final Intent launchActivityIntent = new Intent(context, mainActivityClass);
		launchActivityIntent.setAction(Intent.ACTION_MAIN);
		launchActivityIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		launchActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				Intent.FLAG_ACTIVITY_SINGLE_TOP);
		launchActivityIntent.putExtra(INTENT_EXTRA_NOTIFICATION_ID, notificationID);
		return PendingIntent.
				getActivity(context, 0,
						launchActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	protected PendingIntent getBroadcastDeleteIntent(Class broadcastReceiver, int notificationID)
	{
		Context   context   = Badass.getApplicationContext();
		Intent intent = new Intent(context, broadcastReceiver);
		intent.putExtra(INTENT_EXTRA_NOTIFICATION_ID, notificationID);
		return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
}
