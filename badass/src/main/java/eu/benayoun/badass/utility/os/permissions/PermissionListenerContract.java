package eu.benayoun.badass.utility.os.permissions;

/**
 * Created by PierreB on 28/06/2017.
 */

public interface PermissionListenerContract
{
	void onPermissionGranted();
	void onPermissionDenied(boolean userHasCheckedNeverAskAgain);
}
