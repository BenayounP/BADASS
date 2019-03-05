package eu.benayoun.badass.utility.ui.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import eu.benayoun.badass.R;


/**
 * Created by Pierre on 12/07/2015.
 */
public class BadassUtilsSimpleAnimation
{

	static public void stopAllAnimation(final View view)
	{
		Animation animation = view.getAnimation();
		if (animation != null)
		{
			animation.cancel();
			animation.setAnimationListener(null);
			view.setAnimation(null);
			view.clearAnimation();
		}
	}

    /**
     SHAKE
     **/

    static public void shake(final View view)
    {
        Animation swipe = AnimationUtils.loadAnimation(view.getContext(), R.anim.horizontal_shake);

        view.startAnimation(swipe);
    }

	static public void rotationalShake(final View view)
	{
		rotationalShake(view, false);
	}

	static public void longRotationalShake(final View view)
	{
		rotationalShake(view,true);
	}

    /**
     ENTER
     **/

    // There is no eit animation because with this system the user would see the view disappear during animation and reapear after.
    // If you want an exit animation see More advanced BadassUtilsAnimator


	static public void enterFromLeft(final View view, long duration)
	{
		setAnimation(R.anim.enter_from_left, view, duration);
	}

    static public void enterFromRight(final View view, long duration)
    {
        setAnimation(R.anim.enter_from_right, view, duration);
    }


    static public void enterFromTop(final View view, long duration)
    {
        setAnimation(R.anim.enter_from_top, view, duration);
    }


	static public void enterFromBottom(final View view, long duration)
	{
		setAnimation(R.anim.enter_from_bottom, view, duration);
	}


    /**
     ROTATION
     **/


	static public Animation getSimpleRotateAnimation(int angle, int duration)
	{
		RotateAnimation rotate = new RotateAnimation(0, angle,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(duration);
		rotate.setFillEnabled(true);
		rotate.setFillAfter(true);
		return rotate;
	}

	static public void infiniteRotate(final View view)
	{
		Context context = view.getContext();
		final Animation infiniteRotateAnimation = AnimationUtils.loadAnimation(context, R.anim.infinite_rotation);
		view.startAnimation(infiniteRotateAnimation);
	}


    /**
     FADE
     **/


	static public void fade_in(final View view)
	{
		Context context = view.getContext();
		final Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
		animation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				view.setVisibility(View.VISIBLE);
			}
		});
		view.startAnimation(animation);
	}

	static public void fade_out(final View view)
	{
		Context context = view.getContext();
		final Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
		animation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{
				view.setVisibility(View.INVISIBLE);
			}
		});
		view.startAnimation(animation);
	}

    /**
     ZOOM
     **/

	static public void zoom_in(final View view)
	{
		Context context = view.getContext();
		final Animation zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
		zoomInAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				view.setVisibility(View.VISIBLE);
			}
		});
		view.startAnimation(zoomInAnimation);
	}

	static public void zoom_out(final View view)
	{
		Context context = view.getContext();
		final Animation zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
		zoomAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{
				view.setVisibility(View.INVISIBLE);
			}
		});
		view.startAnimation(zoomAnimation);
	}

    /**
     SWAP
     **/

	static public void swapViews(final View toAppear,final View toDisappear)
	{
		Context context = toAppear.getContext();
		final Animation zoomOutAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
		final Animation zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

		zoomOutAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{
				toDisappear.setVisibility(View.INVISIBLE);
				toAppear.startAnimation(zoomInAnimation);
			}
		});

		zoomInAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				toAppear.setVisibility(View.VISIBLE);
			}
		});
		toDisappear.startAnimation(zoomOutAnimation);
	}

    /**
     SWITCH
     **/

	static public void switchVisibility(final View view, final int visibility)
	{
		Context context = view.getContext();
		if (visibility == View.VISIBLE)
		{
			final Animation zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);
			zoomInAnimation.setAnimationListener(new Animation.AnimationListener()
			{
				@Override
				public void onAnimationStart(Animation animation)
				{
					view.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationEnd(Animation animation)
				{
				}

				@Override
				public void onAnimationRepeat(Animation animation)
				{
				}
			});
			view.startAnimation(zoomInAnimation);
		}
		else // INVISIBLE or GONE
		{
			final Animation zoomOutAnimation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.zoom_out);

			zoomOutAnimation.setAnimationListener(new UtilitiesAnimationListener()
			{
				@Override
				public void onAnimationEnd(Animation animation)
				{
					view.setVisibility(visibility);
				}

			});
			view.startAnimation(zoomOutAnimation);
		}
	}

	public static void switchText(final TextView textview, final String text)
	{
		Context context = textview.getContext();
		final Animation zoomOutAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
		final Animation zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

		zoomInAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				textview.setVisibility(View.VISIBLE);
			}

		});

		zoomOutAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{

				textview.setVisibility(View.INVISIBLE);
				textview.setText(text);
				textview.startAnimation(zoomInAnimation);
			}

		});
		textview.startAnimation(zoomOutAnimation);
	}

	public static void switchImage(final ImageView imageView, final int drawableId)
	{
		Context context = imageView.getContext();
		final Animation zoomOutAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);
		final Animation zoomInAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in);

		zoomInAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
				imageView.setVisibility(View.VISIBLE);
				imageView.setImageResource(drawableId);
			}
		});

		zoomOutAnimation.setAnimationListener(new UtilitiesAnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{

				imageView.setVisibility(View.INVISIBLE);
				imageView.startAnimation(zoomInAnimation);
			}
		});
		imageView.startAnimation(zoomOutAnimation);
	}

	/**
	 * INTERNAL COOKING
	 */
	static protected void rotationalShake(final View view, boolean more)
	{
		Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotational_shake);
		if (more)
		{
			shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.long_rotational_shake);
		}

		view.startAnimation(shake);
	}

	static protected void setAnimation(int animationId, View view, long duration)
    {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),animationId);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }
}
