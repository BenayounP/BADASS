package eu.benayoun.badass;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.util.ArrayList;

import eu.benayoun.badass.background.badassthread.manager.BadassThreadListener;
import eu.benayoun.badass.background.badassthread.manager.BadassThreadMngr;
import eu.benayoun.badass.background.androidevents.AndroidEventsCtrl;
import eu.benayoun.badass.background.androidevents.internetconnectivity.BadassInternetConnectivityListenerContract;
import eu.benayoun.badass.background.androidevents.screen.BadassScreenActivityListenerContract;
import eu.benayoun.badass.background.badassthread.badassjob.BadassJobsCtrl;
import eu.benayoun.badass.ui.BadassUIBroadCastMngr;
import eu.benayoun.badass.ui.events.BadassUIEventListenerContract;
import eu.benayoun.badass.ui.events.BadassUIEventsContract;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionCtrl;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionsMngr;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionListenerContract;
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


	private static Context applicationContext = null;

	private static ActivityState activityState;

	private static BadassUIBroadCastMngr badassUIBroadCastMngr;
	private static BadassThreadMngr badassThreadMngr;
	private static BadassUIEventsContract AppUIEvents;
	private static BadassPermissionsMngr badassPermissionsMngr;
	private static AndroidEventsCtrl androidEventsCtrl;


	static public void init(Application applicationArg, BadassUIEventsContract AppUIEvents)
	{
		applicationContext = applicationArg;
		badassUIBroadCastMngr = new BadassUIBroadCastMngr();
		Badass.AppUIEvents = AppUIEvents;
	}

	/*************
	 BADASS THREAD
	 *************/

	public static void setThreadMngr(BadassJobsCtrl badassJobsCtrl, BadassThreadListener badassThreadListener)
	{
		Badass.badassThreadMngr = new BadassThreadMngr(badassJobsCtrl,badassThreadListener);
	}


	public static void launchBadassThread()
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

	public static void listenToInternetConnectivity(BadassInternetConnectivityListenerContract internetConnectivityListener)
	{
		if (androidEventsCtrl == null)
		{
			androidEventsCtrl = new AndroidEventsCtrl();
		}
		androidEventsCtrl.listenToInternetConnectivity(internetConnectivityListener);
	}

	public static void listenToScreenActivity(BadassScreenActivityListenerContract screenActivityListener)
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

	public static void setNotificationAndWidgetsEventsLister(BadassUIEventListenerContract notificationAndWidgetsEventsLister)
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
	public static BadassPermissionCtrl getPermissionManager(String permissionName, int explanationStringId, BadassPermissionListenerContract badassPermissionListener)
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

	public static void beVerbose()
    {
        BadassLog.beVerbose();
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

	private static BadassPermissionsMngr getBadassPermissionsMngr()
	{
		if (badassPermissionsMngr == null)
		{
			badassPermissionsMngr = new BadassPermissionsMngr();
		}
		return badassPermissionsMngr;
	}
}
