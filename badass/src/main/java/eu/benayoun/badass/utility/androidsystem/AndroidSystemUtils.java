package eu.benayoun.badass.utility.androidsystem;

import android.os.Looper;
import android.util.DisplayMetrics;

import eu.benayoun.badass.Badass;

/**
 * Created by Pierre on 28/12/2014.
 */
public class AndroidSystemUtils
{
    public static float getDeviceWidth()
    {
        DisplayMetrics dm = Badass.getApplicationContext()
                .getResources().getDisplayMetrics();
        return dm.widthPixels /dm.density;
    }


    public static boolean thisThreadIsUIThread()
    {
       return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
