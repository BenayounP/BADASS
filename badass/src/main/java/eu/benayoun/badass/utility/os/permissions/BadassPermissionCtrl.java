package eu.benayoun.badass.utility.os.permissions;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import eu.benayoun.badass.Badass;

/**
 * Created by PierreB on 17/03/2018.
 */

public class BadassPermissionCtrl
{
	protected int id;
	protected String name;
	protected BadassPermissionListenerContract listener;
	protected int standardExplanationStringId;
	protected int explanationForUsersThatCheckedNeverAskAgainStringId=-1;
	protected boolean userHasCheckedNeverAskAgain = false;


	public BadassPermissionCtrl(int id, String name, int standardExplanationStringId, BadassPermissionListenerContract badassPermissionListener)
	{
		this.id = id;
		this.name = name;

		listener = badassPermissionListener;
		this.standardExplanationStringId = standardExplanationStringId;
	}

    public void setExplanationForUsersThatCheckedNeverAskAgainStringId(int explanationForUsersThatCheckedNeverAskAgainStringId)
    {
        this.explanationForUsersThatCheckedNeverAskAgainStringId = explanationForUsersThatCheckedNeverAskAgainStringId;
    }

    public int getId()
	{
		return id;
	}

	public String getPermissionName()
	{
		return name;
	}

    public int getExplanationStringId()
    {
        if (userHasCheckedNeverAskAgain && explanationForUsersThatCheckedNeverAskAgainStringId!=-1) return explanationForUsersThatCheckedNeverAskAgainStringId;
        return standardExplanationStringId;
    }


    public boolean isUserHasCheckedNeverAskAgain() {
        return userHasCheckedNeverAskAgain;
    }

    public boolean isPermissionGranted()
	{
		boolean isAllowed;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
		{
			isAllowed = true;
		}
		else
		{
			isAllowed =  (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(Badass.getApplicationContext(), name));
		}
		return isAllowed;
	}

	public void onPermissionGranted()
	{
		if (listener!=null)	listener.onPermissionGranted();
	}

	public void onPermissionDenied(boolean userHasCheckedNeverAskAgain)
	{
	    this.userHasCheckedNeverAskAgain = userHasCheckedNeverAskAgain;
		if (listener!=null) listener.onPermissionDenied(userHasCheckedNeverAskAgain);
	}

}
