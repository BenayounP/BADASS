package eu.benayoun.badass.ui.events;

public interface BadassUIEventListenerContract
{
    void onEvent(int eventId, long eventTimeInMs);
}
