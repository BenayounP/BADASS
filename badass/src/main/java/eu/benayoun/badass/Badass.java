package eu.benayoun.badass;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.util.ArrayList;

import eu.benayoun.badass.background.BadassThreadMngr;
import eu.benayoun.badass.background.androidevents.AndroidEventsCtrl;
import eu.benayoun.badass.background.androidevents.internetconnectivity.InternetConnectivityListenerContract;
import eu.benayoun.badass.background.androidevents.screen.ScreenActivityListenerContract;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJobsCtrl;
import eu.benayoun.badass.ui.BadassUIBroadCastMngr;
import eu.benayoun.badass.ui.events.UIEventListenerContract;
import eu.benayoun.badass.ui.events.UIEventsContract;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionCtrl;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionsMngr;
import eu.benayoun.badass.utility.os.permissions.PermissionListenerContract;
import eu.benayoun.badass.utility.ui.BadassLog;

/**
 * Created by PierreB on 14/01/2018.
 */

public class Badass
{
	public enum ActivityState {
		ACTIVITY_CREATED,
		ACTIVITY_STARTED,
		ACTIVITY_RESUMED,
		ACTIVITY_PAUSED,
		ACTIVITY_STOPPED,
		ACTIVITY_RESTARTING,
		ACTIVITY_DESTROYED,
	}


	static protected Context applicationContext = null;

	static protected ActivityState activityState;

	static protected BadassUIBroadCastMngr badassUIBroadCastMngr;
	static protected BadassThreadMngr badassThreadMngr;
	static protected UIEventsContract AppUIEvents;
	static protected BadassPermissionsMngr badassPermissionsMngr;
	static protected AndroidEventsCtrl androidEventsCtrl;


	static public void init(Application applicationArg, UIEventsContract AppUIEvents)
	{
		applicationContext = applicationArg;
		badassUIBroadCastMngr = new BadassUIBroadCastMngr();
		Badass.AppUIEvents = AppUIEvents;
	}

	/*************
	 BADASS THREAD
	 *************/

	public static void setJobCtrl(BadassJobsCtrl badassJobsCtrl)
	{
		Badass.badassThreadMngr = new BadassThreadMngr(badassJobsCtrl);
	}



	public static void startBadassThread()
	{
		badassThreadMngr.startThread();
	}

	public static boolean isThreadRunning()
    {
        return badassThreadMngr.isRunning();
    }

	public static void scheduleNextWorkingSession(){
        badassThreadMngr.scheduleNextCall();}

	/**********************
	 ANDROID EVENTS MANAGER
	 **********************/

	public static void listenToInternetConnectivity(InternetConnectivityListenerContract internetConnectivityListener)
	{
		if (androidEventsCtrl == null)
		{
			androidEventsCtrl = new AndroidEventsCtrl();
		}
		androidEventsCtrl.listenToInternetConnectivity(internetConnectivityListener);
	}

	public static void listenToScreenActivity(ScreenActivityListenerContract screenActivityListener)
	{
		if (androidEventsCtrl == null)
		{
			androidEventsCtrl = new AndroidEventsCtrl();
		}
		androidEventsCtrl.listenToScreenActivity(screenActivityListener);
	}

	public static boolean isConnectedToInternet()
	{
		if (androidEventsCtrl == null)
		{
			androidEventsCtrl = new AndroidEventsCtrl();
		}
		return androidEventsCtrl.isConnectedToInternet();
	}


	/************************
	          UI EVENTS
	 *************************/

	public static void broadcastUIEvent(int eventId)
	{
		badassUIBroadCastMngr.broadcastUIEvent(eventId);
	}

	public static ArrayList<Boolean> getFreshUIEventsList()
	{
		return AppUIEvents.getFreshUiEventsList();
	}

	public static void setNotificationAndWidgetsEventsLister(UIEventListenerContract notificationAndWidgetsEventsLister)
	{
		badassUIBroadCastMngr.setNotificationAndWidgetsEventsLister(notificationAndWidgetsEventsLister);
	}

	public static String getEventName(int eventId)
	{
		return AppUIEvents.getEventName(eventId);
	}

	/*********************************
	        PERMISSIONS MANAGER
	  *********************************/
	public static BadassPermissionCtrl getPermissionManager(String permissionName, int explanationStringId, PermissionListenerContract badassPermissionListener)
	{
		return getBadassPermissionsMngr().getPermissionCtrl(permissionName,explanationStringId,badassPermissionListener);
	}

	public static void requestPermission(Activity activity, BadassPermissionCtrl badassPermissionCtrl)
	{
		getBadassPermissionsMngr().requestPermission(activity, badassPermissionCtrl);
	}

	public static void onRequestPermissionsResult(Activity activity, int requestCode,
	                                       String permissions[], int[] grantResults)
	{
		getBadassPermissionsMngr().onRequestPermissionsResult(activity,requestCode,permissions,grantResults);
	}

	/************************************************************************************
	                  SOME UTILITIES
	 ***********************************************************************************/

	/***************
	 CONTEXT
	 **************/

    public static String getString(int textId)
    {
        return applicationContext.getString(textId);
    }

	public static CharSequence getCharSequence(int textId)
	{
		return applicationContext.getText(textId);
	}

	public static int getColor(int colorId)
	{
		return applicationContext.getResources().getColor(colorId);
	}

	public static Context getApplicationContext()
	{
		return applicationContext.getApplicationContext();
	}

	public static Resources getResources()
	{
		return applicationContext.getResources();
	}

	public static String getString(int textId, java.lang.Object... args)
	{
		return String.format(getString(textId), args);
	}

	/***************
	 ANDROID VERSION
	 ***************/

	public static boolean androidVersionSuperiorOrEqualTo(int androidVersion)
	{
		return Build.VERSION.SDK_INT >= androidVersion;
	}

	/************
	 LOG
	 ***********/

	public static void enableLogging()
	{
		BadassLog.enableLogging();
	}

	public static void allowLogInFile(String logsFileName)
	{
		BadassLog.allowLogInFile(logsFileName);
	}
	public static boolean isAllowLogInFile()
	{
		return BadassLog.isAllowLogInFile();
	}

	public static BadassPermissionCtrl getLogInFilePermissionCtrl()
	{
		return BadassLog.getLogInFilePermissionManager();
	}

	public static void defineTag(String tag)
	{
		BadassLog.defineTag(tag);
	}

	public static void finalLog(String toLog)
	{
		BadassLog.finalLog(toLog);
	}

	public static void log(String toLog)
	{
		BadassLog.log(toLog);
	}

	public static void logInFile(String string)
	{
		BadassLog.logInFile(string);
	}

	public static String getCaller() {return BadassLog.getMethodCaller(3);}

	public static String  getSimpleClassName() {return BadassLog.getSimpleClassName(3);}

	public static String getSimpleClassName(Object o) {return BadassLog.getSimpleClassName(o);}

	/*****************
	 ACTIVITY STATE
	 **************/


	public static ActivityState getActivityState()
	{
		return activityState;
	}

	public static void setActivityState(ActivityState activityState)
	{
		Badass.activityState = activityState;
	}

	/********************
	 * INTERNAL COOKING
	 ********************/

	protected static BadassPermissionsMngr getBadassPermissionsMngr()
	{
		if (badassPermissionsMngr == null)
		{
			badassPermissionsMngr = new BadassPermissionsMngr();
		}
		return badassPermissionsMngr;
	}
}
