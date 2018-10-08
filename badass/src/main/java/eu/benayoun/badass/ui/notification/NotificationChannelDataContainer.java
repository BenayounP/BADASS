package eu.benayoun.badass.ui.notification;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import eu.benayoun.badass.Badass;

/**
 * Created by PierreB on 27/06/2017.
 */

public class NotificationChannelDataContainer
{

    // CHANNEL
    public String channelId;
    public String channelName;

    public String chanelDescription;

    public int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
    public boolean channelEnableLights = false;
    public int channelLightColorArgb=-1;

    public boolean channelEnableVibration  = false;
    public long[]  channelVibrationPattern = new long[]{0, 0, 0, 0,0, 0, 0, 0, 0};
    
    
    public NotificationChannelDataContainer(String channelId, String channelName, String chanelDescription)
	{
		this.channelId = channelId;
		this.channelName = channelName;
		this.chanelDescription = chanelDescription;
		if (Build.VERSION.SDK_INT >= 26)
		{
			setNotificationChannel();
		}
	}


	/**
	 * INTERNAL COOKING
	 */

	@TargetApi(Build.VERSION_CODES.O)
    protected void setNotificationChannel()
	{
		Context context = Badass.getApplicationContext();
		NotificationManager notificationManager =
				(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);
		// lights
		notificationChannel.setDescription(chanelDescription);
		notificationChannel.enableLights(channelEnableLights);
		notificationChannel.setLightColor(channelLightColorArgb);

		notificationChannel.enableVibration(channelEnableVibration);
		notificationChannel.setVibrationPattern(channelVibrationPattern);
		notificationChannel.setSound(null,null);
		notificationManager.createNotificationChannel(notificationChannel);
	}

}
