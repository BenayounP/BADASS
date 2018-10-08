package eu.benayoun.badass.utility.os.permissions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import eu.benayoun.badass.Badass;


/**
 * Created by Pierre on 02/10/2015.
 */
public class BadassPermissionsMngr
{
	protected ArrayList<BadassPermissionCtrl> permissionsList;

	public BadassPermissionCtrl getPermissionCtrl(String permissionName, int explanationStringId, BadassPermissionListenerContract badassPermissionListener)
	{
		if (permissionsList==null)
		{
			permissionsList = new ArrayList<>();
		}
		int                  id                   = permissionsList.size();
		BadassPermissionCtrl badassPermissionCtrl = new BadassPermissionCtrl(id,permissionName,explanationStringId, badassPermissionListener);
		permissionsList.add(badassPermissionCtrl);
		return badassPermissionCtrl;
	}

	public void requestPermission(Activity activity, BadassPermissionCtrl badassPermissionCtrl)
	{
		int id = badassPermissionCtrl.getId();
		ActivityCompat.requestPermissions(activity,
				new String[]{permissionsList.get(id).name},
				id);
	}

	@TargetApi(23)
	public void onRequestPermissionsResult(Activity activity, int requestCode,
	                                       String permissions[], int[] grantResults)
	{
		if (requestCode < permissionsList.size() && grantResults.length > 0)
		{
			BadassPermissionCtrl badassPermissionCtrl = permissionsList.get(requestCode);
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				badassPermissionCtrl.onPermissionGranted();
			}
			else
			{
				badassPermissionCtrl.onPermissionDenied(activity.shouldShowRequestPermissionRationale( badassPermissionCtrl.getName()));
			}
		}
	}

	static public void goToSettingsPage()
	{
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.fromParts("package", Badass.getApplicationContext().getPackageName(), null));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Badass.getApplicationContext().startActivity(intent);
	}


}
