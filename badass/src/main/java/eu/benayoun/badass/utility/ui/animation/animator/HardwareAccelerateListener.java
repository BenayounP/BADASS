package eu.benayoun.badass.utility.ui.animation.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.view.View;

/**
 * Created by Pierre on 13/01/2016.
 */
@TargetApi(11)
public class HardwareAccelerateListener extends AnimatorListenerAdapter
{

	@Override
	public void onAnimationStart(Animator animation) {
		View view = getView(animation);

		if (view != null) {
			view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		View view = getView(animation);

		if (view != null) {
			view.setLayerType(View.LAYER_TYPE_NONE, null);
		}
	}

	/**
	 * INTERNAL COOKING
	 */

	View getView(Animator animation)
	{
		AnimatorSet set = (AnimatorSet)animation;
		return (View)(((ObjectAnimator)set.getChildAnimations().get(0)).getTarget());
	}
}
