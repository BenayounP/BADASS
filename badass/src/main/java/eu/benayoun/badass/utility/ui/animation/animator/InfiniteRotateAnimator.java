package eu.benayoun.badass.utility.ui.animation.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.view.View;

/**
 * Created by Pierre on 23/01/2016.
 */
@TargetApi(11)
public class InfiniteRotateAnimator extends AnimatorListenerAdapter
{
	ObjectAnimator rotateObjectAnimator;
	AnimatorSet animatorSet;
	View view = null;
	int duration;

	public void infiniteRotate(View view, int duration)
	{
		this.view = view;
		this.duration = duration;
		internalInfiniteRotate();
	}

	public void stopAnimation()
	{
		AnimatorHelper.clearAnimation(animatorSet);
		view = null;
		animatorSet = null;
	}

	@Override
	public void onAnimationEnd(Animator animation)
	{
		internalInfiniteRotate();
	}

	/**
	 * INTERNAL COOKING
	 */

	void internalInfiniteRotate()
	{
		rotateObjectAnimator = AnimatorHelper.getRotateAnimator(view, 360, duration);
		animatorSet = new AnimatorSet();
		animatorSet.play(rotateObjectAnimator);
		animatorSet.addListener(this);
		animatorSet.start();
	}



}
