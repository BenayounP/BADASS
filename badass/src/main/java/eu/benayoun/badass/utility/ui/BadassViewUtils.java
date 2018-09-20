package eu.benayoun.badass.utility.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

import eu.benayoun.badass.Badass;

public class BadassViewUtils
{

    //KEYBOARD

	public static void hideKeyboard(View view)
	{
		InputMethodManager in = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getApplicationWindowToken(),
				0);
	}

	public static void showKeyboard(View view)
	{
		InputMethodManager in = (InputMethodManager) view.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.showSoftInput(view,
				InputMethodManager.SHOW_FORCED);
	}

	public static void loseFocus(View view)
	{
		view.setFocusableInTouchMode(false);
		view.setFocusable(false);
		view.setFocusableInTouchMode(true);
		view.setFocusable(true);
	}

	// TEXTVIEW



	public static void underline(TextView textView)
	{
		textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	}

	public static void clearPaintFlags(TextView textView)
	{
		textView.clearComposingText();
	}

	// ADD AND INFLATE VIEW

	public static View addAndGetView(LinearLayout linearLayout, int viewLayoutId)
	{
		return LayoutInflater.from(linearLayout.getContext()).inflate(viewLayoutId, linearLayout, false);
	}

	public static void addViewTo(LinearLayout linearLayout, int viewLayoutId)
	{
		linearLayout.addView(LayoutInflater.from(linearLayout.getContext()).inflate(viewLayoutId, linearLayout, false));
	}

	public static View inflateView(FrameLayout frameLayout, int viewLayoutId)
	{
		return LayoutInflater.from(frameLayout.getContext()).inflate(viewLayoutId, frameLayout, false);
	}

	// GRADIENT

	// WARNING : Only start with a view with a gradient drawable bgnd defined in layout
	@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setGradientBackgroundView(View view, int color1, int color2)
	{
		GradientDrawable drawable = (GradientDrawable) view.getBackground();
		if (drawable!= null)
		{
			drawable.mutate();
			int[] gradientColors = new int[]{color1, color2};
			drawable.setOrientation(GradientDrawable.Orientation.TL_BR);
			drawable.setColors(gradientColors);
		}
	}

    public static void doNotDisplayOnAndroidStatusBar(View view)
    {
        if (Badass.androidVersionSuperiorOrEqualTo(Build.VERSION_CODES.LOLLIPOP))
        {
            int statusBarHeight = getAndroidStatusBarHeight();
            //view.getLayoutParams().height+=statusBarHeight;
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop()+ statusBarHeight ,view.getPaddingRight(),view.getPaddingBottom());
        }
    }

    public static void doNotDisplayOn_portrait_AndroidNavigationBar(View view)
    {
        if (Badass.androidVersionSuperiorOrEqualTo(Build.VERSION_CODES.LOLLIPOP))
        {
            int orientation = Badass.getApplicationContext().getResources().getConfiguration().orientation;
            if (orientation== Configuration.ORIENTATION_PORTRAIT)
            {
                int navigationBarHeight = getAndroidNavigationBarSize().y;
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom() + navigationBarHeight);
            }
        }
    }

    public static void doNotDisplayOn_landscape_AndroidNavigationBar(View view)
    {
        if (Badass.androidVersionSuperiorOrEqualTo(Build.VERSION_CODES.LOLLIPOP))
        {
            int orientation = Badass.getApplicationContext().getResources().getConfiguration().orientation;
            if (orientation== Configuration.ORIENTATION_LANDSCAPE)
            {
                int navigationBarWidth = getAndroidNavigationBarSize().x;
                view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight() + navigationBarWidth, view.getPaddingBottom());
            }
        }
    }

	// ************
	// ANDROID BARS
	// ************

	public static int getAndroidStatusBarHeight()
	{
		int statusBarHeight = 0;
		int resourceId = Badass.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			statusBarHeight = Badass.getResources().getDimensionPixelSize(resourceId);
		}
		return statusBarHeight;
	}

	public static Point getAndroidNavigationBarSize() {
		Point appUsableSize = getAppUsableScreenSize();
		Point realScreenSize = getRealScreenSize();

		// navigation bar on the right
		if (appUsableSize.x < realScreenSize.x) {
			return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
		}

		// navigation bar at the bottom
		if (appUsableSize.y < realScreenSize.y) {
			return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
		}

		// navigation bar is not present
		return new Point();
	}

	public static Point getAppUsableScreenSize() {
		WindowManager windowManager = (WindowManager) Badass.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display       display       = windowManager.getDefaultDisplay();
		Point         size          = new Point();
		if (Badass.androidVersionSuperiorOrEqualTo(13))
		{
			display.getSize(size);
		}
		else
		{
			size.set(display.getHeight(),display.getWidth());
		}

		return size;
	}

	public static Point getRealScreenSize() {
		WindowManager windowManager = (WindowManager) Badass.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point size = new Point();
		size.x = -1;
		size.y = -1;

		if (Build.VERSION.SDK_INT >= 17) {
			display.getRealSize(size);
		} else if (Build.VERSION.SDK_INT >= 14) {
			try {
				size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
				size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
			} catch (IllegalAccessException e) { } catch (InvocationTargetException e) { } catch (NoSuchMethodException e) { }
		}

		return size;
	}

}
