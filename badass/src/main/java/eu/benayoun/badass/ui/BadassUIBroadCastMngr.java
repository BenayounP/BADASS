package eu.benayoun.badass.ui;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.ui.events.BadassUIEventListenerContract;
import eu.benayoun.badass.utility.os.time.BadassUtilsTime;


@SuppressWarnings("ALL")
public class BadassUIBroadCastMngr
{
	static protected final String EVENT_IDENTIFIER = "BADASS_EVENT_IDENTIFIER";
	public static final String    ACTION_EVENT     = "BADASS_ACTION_EVENT";

	protected BadassUIEventListenerContract notificationAndWidgetsEventsLister;


	public void setNotificationAndWidgetsEventsLister(BadassUIEventListenerContract notificationAndWidgetsEventsLister)
	{
		this.notificationAndWidgetsEventsLister = notificationAndWidgetsEventsLister;
	}

	public void broadcastUIEvent(int eventId)
	{
		Intent intent = new Intent(ACTION_EVENT);
		intent.putExtra(EVENT_IDENTIFIER,eventId);
		LocalBroadcastManager.getInstance(Badass.getApplicationContext()).sendBroadcast(intent);

		if (notificationAndWidgetsEventsLister !=null)
		{
			long nowInMs = BadassUtilsTime.getCurrentTimeInMs();
			notificationAndWidgetsEventsLister.onEvent(eventId, nowInMs);

		}
	}


	public static int getEventId(Intent intent)
	{
		int eventId=-1;
		if (intent != null)
		{
			eventId=intent.getIntExtra(EVENT_IDENTIFIER,-1);
		}
		return eventId;
	}


}
