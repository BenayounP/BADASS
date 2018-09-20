package eu.benayoun.badass.ui.events;

import java.util.ArrayList;

/**
 * Created by PierreB on 15/03/2018.
 */

public interface UIEventsContract
{
	ArrayList<Boolean> getFreshUiEventsList();
	String getEventName(int eventId);
}
