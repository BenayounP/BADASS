package eu.benayoun.badass.ui.events;

public interface UIEventListenerContract
{
    void onEvent(int eventId, long eventTimeInMs);
}
