package eu.benayoun.badass.utility.ui;

import android.Manifest;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Calendar;
import java.util.TimeZone;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.R;
import eu.benayoun.badass.utility.os.permissions.BadassPermissionCtrl;

public class BadassLog
{
	// VERY IMPORTANT : if false there will be no logMethodName
	protected static boolean logAllowed = false;

	protected static String               log_tag                    = "BENAYOUN";
	protected static boolean              allowLogInFile             = false;
	protected static BadassPermissionCtrl logInFilePermissionManager =null;


	protected static boolean cantLogLine = false;
	protected static boolean cantCreateFileMentioned   = false;
	protected static boolean cantCreateFolderMentioned = false;

	// CONFIG
	// logfileName
	protected static final String LOGS_GENERAL_DIRECTORY = "android_logs" + File.separator;
	protected static  String logsFileName = "logMethodName.txt";

	public static void defineTag(String tag)
	{
		log_tag = tag;
	}

	public static void enableLogging()
	{
		logAllowed = true;
	}

	public static void allowLogInFile(String logsFileName)
	{
		allowLogInFile = true;
		logInFilePermissionManager=Badass.getPermissionManager(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.permission_write,null);
		logsFileName = logsFileName;
	}

	public static boolean isAllowLogInFile()
	{
		return allowLogInFile;
	}

	public static BadassPermissionCtrl getLogInFilePermissionManager()
	{
		return logInFilePermissionManager;
	}

	public static String getLogPath()
	{
		return LOGS_GENERAL_DIRECTORY;
	}

	public static String getLogsFileName()
	{
		return logsFileName;
	}


	/****************************
	              LOG
	 ****************************/


	public static void finalLog(String toLog)
	{
		internalLog(toLog, allowLogInFile);
	}

	public static void log(String toLog, boolean isLogged)
	{
		if (isLogged)
		{
			log(toLog);
		}
	}

	public static void error(String toLog)
	{
		if (logAllowed)
		{
			if (toLog == null)
			{
				toLog = "No specific message emmited";
			}
			Log.d(log_tag, "ERROR : " + toLog);
		}
	}

	public static void error(String toLog, boolean isLogged)
	{
		if (isLogged)
		{
			error(toLog);
		}
	}

	public static void logIfNull(Object object)
	{
		if (object == null)
		{
			log(object.getClass().getSimpleName() + " is null");
		}
	}

	public static void logInFile(String string)
	{
		internalLog(string,allowLogInFile);
	}


	public static void log(String string)
	{
		internalLog(string,false);
	}

	// File

	public static int getLogFileLines()
	{
		LineNumberReader lnr          = null;
		int              logFileLines = -1;
		try
		{
			lnr = new LineNumberReader(new FileReader(getLogFile()));
		} catch (FileNotFoundException e)
		{
			Log.d(log_tag,"Can't count lines:  logMethodName file not found." );
		}
		if (lnr!=null)
		{
			boolean pb = false;
			try
			{
				lnr.skip(Long.MAX_VALUE);
			} catch (IOException e)
			{
				Log.d(log_tag,"pb with logMethodName file when skipping lines" );
				pb = true;
			}
			if (!pb)
			{
				logFileLines = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
				// Finally, the LineNumberReader object should be closed to prevent resource leak
				try
				{
					lnr.close();
				} catch (IOException e)
				{
					Log.d(log_tag,"pb with logMethodName file when closing in counting lines" );
				}
			}
		}
		return logFileLines;
	}

	public static void clearLogFile()
	{
		File logFile =getLogFile();
		if (logFile!=null)
		{
			boolean isDeleted =	logFile.delete();
			if (isDeleted == false)
			{
				Log.d(log_tag,"Can't delete logMethodName file." );
			}
		}
	}

	// METHOD NAME

	public static void logMethodName()
	{
		log(new Exception().getStackTrace()[1].getMethodName());
	}

	public static void logMethodName(String string)
	{
		log(getMethodName() + " : " + string);
	}

	public static void logMethodName(boolean verbose)
	{
		if (verbose)
		{
			logMethodName();
		}
	}

	public static void logMethodNameWithClassName()
	{
		log(getSimpleClassName() + "." + getMethodName()+"()");
	}

	// METHOD NAME WITH CALLER

	public static void logMethodNameWithCaller()
	{
		log(getMethodName() + " called by " + getCaller());
	}

	public static void logMethodNameWithCaller(String additionalText)
	{
		log(getMethodName() + " called by " + getCaller() + " : " + additionalText);
	}

	public static void logMethodNameWithCallerInFile()
	{
		log(getMethodName() + " called by " + getCaller());
	}


	static public String getMethodCaller()
	{
		java.lang.StackTraceElement stackTraceElement = new Exception().getStackTrace()[2];
		return getSimpleClassName(stackTraceElement.getClassName()) + "." + stackTraceElement.getMethodName();
	}

	static public String getMethodCaller(int level )
	{
		java.lang.StackTraceElement stackTraceElement = new Exception().getStackTrace()[level];
		return getSimpleClassName(stackTraceElement.getClassName()) + "." + stackTraceElement.getMethodName();
	}

	// CLASS NAME

	public static String getSimpleClassName()
	{
		java.lang.StackTraceElement stackTraceElement = new Exception().getStackTrace()[2];
		return getSimpleClassName(stackTraceElement);
	}


	public static String getSimpleClassName(Object object)
	{
		return object.getClass().getSimpleName();
	}

	// STACK TRACE

	public static void logCabNumStackTrace()
	{
		log(getCabNumStackTrace().toString());
	}

	public static void logInFileCabNumStackTrace()
	{
		logInFile(getCabNumStackTrace().toString());
	}




	/**
	 * INTERNAL COOKING
	 */

	static protected String getCaller()
	{
		java.lang.StackTraceElement stackTraceElement = new Exception().getStackTrace()[3];
		return getSimpleClassName(stackTraceElement.getClassName()) + "." + stackTraceElement.getMethodName();
	}

	static protected String getMethodName()
	{
		return new Exception().getStackTrace()[2].getMethodName();
	}



	static protected String  getSimpleClassName(String fullClassName)
	{
		return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
	}

	static protected StringBuilder getCabNumStackTrace()
	{
		StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
		StringBuilder stringBuilder = new StringBuilder();
		String completeClassName;
		for (int i=2;i<stackTraceElements.length;i++)
		{
			completeClassName = stackTraceElements[i].getClassName();
			if (completeClassName.contains("eu.lecabinetnumerique."))
			{
				if (i >2) stringBuilder.append("\n<-");
				stringBuilder.append(getSimpleClassName(completeClassName)).append(".").append(stackTraceElements[i].getMethodName()).append(":").append(stackTraceElements[i].getLineNumber());
			}
		}
		return stringBuilder;
	}


	/**********************
	 * INTERNAL COOKING
	 **********************/

	static protected void internalLog(String toLog, boolean logInFile)
	{
		if (logAllowed && toLog!=null)
		{
			String lines[] = toLog.split("\\r?\\n");
			for (int i = 0; i < lines.length; i++)
			{
				logLine(lines[i], logInFile);
			}
		}
	}

	static protected void logLine(String toLog, boolean logInFile)
	{
		Log.d(log_tag, toLog);
		if (logInFile)
		{
			fileLog(toLog);
		}
	}

	static public boolean deleteLogFile()
	{
		File logFile = getLogFile();
		boolean success = true;
		if (logFile!=null)
		{
			success = logFile.delete();
		}
		return success;
	}

	protected static void fileLog(String text)
	{
		if (logInFilePermissionManager.isPermissionGranted())
		{
			TimeZone tz = TimeZone.getDefault();
			Calendar c  = Calendar.getInstance(tz);
			String time = c.get(Calendar.HOUR_OF_DAY) + ":"
					+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "."
					+ c.get(Calendar.MILLISECOND);

			File folder = getLogFolder();

			// Make sure the path directory exists.
			if (!folder.exists())
			{
				if (cantCreateFolderMentioned == false)
				{
					Log.d(log_tag, "Create Log Folder");
				}
				boolean folderExists = folder.mkdirs();
				if (folderExists == false && cantCreateFolderMentioned == false)
				{
					Log.d(log_tag, "Can't create folder.");
					cantCreateFolderMentioned = true;
				}
			}

			final File logFile = getLogFile();

			if (!logFile.exists())
			{
				try
				{

					if (cantCreateFileMentioned == false)
					{
						Log.d(log_tag, "Create Log File.");
					}
					logFile.createNewFile();
				} catch (IOException e)
				{
					if (cantCreateFileMentioned == false)
					{
						Log.d(log_tag, "Can't create file: " + e.toString());
						cantCreateFileMentioned = true;
					}
				}
			}
			try
			{
				// BufferedWriter for performance, true to set append to file
				// flag
				BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
						true));
				buf.append(time).append(" : ").append(text).append("\n");
				buf.newLine();
				buf.close();
			} catch (IOException e)
			{
				if (cantLogLine == false)
				{
					Log.d(log_tag, "Can't create line: " + e.toString());
					cantLogLine = true;
				}
			}
		}
	}

	static protected File getLogFolder()
	{
		return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS + File.separator+getLogPath());
	}

	static protected File getLogFile()
	{
		return new File(getLogFolder(), getLogsFileName());
	}
}
