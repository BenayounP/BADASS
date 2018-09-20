package eu.benayoun.badass.utility.ui.animation.animator;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by Pierre on 29/12/2015.
 */
public class AnimatorHelper
{

	// Zoom_In
	@TargetApi(11)
	public void ZoomIn(View view, int duration)
	{
		ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(view, "scaleX", 0,1f);
		scaleUpX.setDuration(duration);
		ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(view, "scaleY", 0,1f);
		scaleUpY.setDuration(duration);
		AnimatorSet scaleUp = new AnimatorSet();
		// todo remove the unnecessary hardware layer ?
		scaleUp.addListener(new HardwareAccelerateListener());
		scaleUp.play(scaleUpX).with(scaleUpY);
		scaleUp.start();
	}

	@TargetApi(11)
	public static void clearAnimation(AnimatorSet animatorSet)
	{
		animatorSet.removeAllListeners();
		animatorSet.end();
		animatorSet.cancel();
	}

	@TargetApi(11)
	public static ObjectAnimator getRotateAnimator(View view, int angle, int duration)
	{
		return new ObjectAnimator().ofFloat(view,
				"rotation", 0,angle).setDuration(duration);

	}

	@TargetApi(11)
	public static ObjectAnimator getPauseAnimator(View view, int duration)
	{
		return new ObjectAnimator().ofFloat(view,
			"rotation", 0,0).setDuration(duration);

	}

	@TargetApi(11)
	public static ObjectAnimator getEnterFromLeft(View view, int duration)
	{
		ObjectAnimator objectAnimator =new ObjectAnimator().ofFloat(view,
				"translationX",- ((View)view.getParent()).getWidth(),0).setDuration(duration);
		objectAnimator.setInterpolator(new DecelerateInterpolator());
		return objectAnimator;
	}

	@TargetApi(11)
	public static ObjectAnimator getExitToRight(View view, int duration)
	{
		ObjectAnimator objectAnimator = new ObjectAnimator().ofFloat(view,
				"translationX",((View)view.getParent()).getWidth()).setDuration(duration);
		objectAnimator.setInterpolator(new AccelerateInterpolator());
		return objectAnimator;
	}

	@TargetApi(11)
	public static ObjectAnimator getStopTranslation(View view)
	{
		return new ObjectAnimator().ofFloat(view,
				"translationX",0,0).setDuration(0);

	}

	/**
	 * INTERNAL COOKING
	 */

}
