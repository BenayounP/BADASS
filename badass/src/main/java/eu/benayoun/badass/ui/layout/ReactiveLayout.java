package eu.benayoun.badass.ui.layout;

import android.view.View;

import java.util.ArrayList;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.ui.events.BadassUIEventListenerContract;
import eu.benayoun.badass.utility.model.ArrayListUtils;


/**
 * Created by PierreB on 31/07/2017.
 */

public class ReactiveLayout implements BadassUIEventListenerContract
{
	protected View                                 mainView;

	protected boolean isVisible                     =true;
	
	protected boolean hasBeenRefreshedOnce = false;
	protected ArrayList<Boolean> monitoredUIEventsList;
	protected ArrayList<ReactiveLayout> subLayoutsList;


	public ReactiveLayout(View mainViewArg)
	{
		mainView = mainViewArg;
		monitoredUIEventsList = Badass.getFreshUIEventsList();
		subLayoutsList = new ArrayList<>();
	}

	@Override
	public void onEvent(int eventId, long eventTimeInMs)
	{
		// update main content
		if (monitoredUIEventsList.get(eventId) && (hasBeenRefreshedOnce == false || shouldUpdateOn(eventId, eventTimeInMs)))
		{
			hasBeenRefreshedOnce = true;
			updateMainContent(eventId, eventTimeInMs);
		}

		// update sub layouts
		if (ArrayListUtils.isNOTNullOrEmpty(subLayoutsList))
		{
			for (int i=0;i<subLayoutsList.size();i++)
			{
				subLayoutsList.get(i).onEvent(eventId, eventTimeInMs);
			}
		}
	}

	public void releaseUI()
	{
		if (ArrayListUtils.isNOTNullOrEmpty(subLayoutsList))
		{
			for (int i = 0; i < subLayoutsList.size(); i++)
			{
				subLayoutsList.get(i).releaseUI();

			}
			subLayoutsList.clear();
			subLayoutsList=null;
		}
	}

	public void addSubLayout(ReactiveLayout subLayout)
	{
		subLayoutsList.add(subLayout);
	}


	public void setInvisible()
	{
		mainView.setVisibility(View.INVISIBLE);
		isVisible = false;
	}

	public void setVisible()
	{
		mainView.setVisibility(View.VISIBLE);
		isVisible = true;
	}

	public void setGoneVisibility()
	{
		mainView.setVisibility(View.GONE);
		isVisible = false;
	}

	public boolean isVisible()
    {
        return isVisible;
    }



	/**
	 * INTERNAL COOKING
	 */

	protected void listenTo(int eventId)
	{
		monitoredUIEventsList.set(eventId, true);
	}



	@SuppressWarnings("EmptyMethod")
    protected void updateMainContent(int eventId, long eventTimeInMs)
	{
        // override if you need to update the main content of the layput in addition of the sublayouts
	}

	@SuppressWarnings("SameReturnValue")
    protected boolean shouldUpdateOn(int eventId, long eventTimeInMs)
	{
	    // tweak for update only depending on the value of the eventId an/or eventTimeInMs
		return true;
	}


}
