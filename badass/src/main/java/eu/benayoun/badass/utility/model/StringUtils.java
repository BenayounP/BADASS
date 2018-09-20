package eu.benayoun.badass.utility.model;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.benayoun.badass.Badass;
import eu.benayoun.badass.utility.math.MathUtils;

public class StringUtils
{

	/**
	 * STRING ^^
	 */

	static public String getCapitalizedString(String toCapitalise)
	{
		return toCapitalise.substring(0, 1).toUpperCase() + toCapitalise.substring(1);
	}

	static public String getLowerCasedString(String toLowerCase)
	{
		return toLowerCase.substring(0, 1).toLowerCase() + toLowerCase.substring(1);
	}

	static public String getMaybeNullString(String potentiallyNullString)
	{
		return (potentiallyNullString ==null ? "null" : potentiallyNullString);
	}




	/**
	 * RATES
	 */
	static public String getRateString(float valueOnTotal, int digits)
	{
		String precision = "%." + digits + "f";

		if (valueOnTotal == (int) valueOnTotal)
			return String.format("%d", (int) valueOnTotal) + " %";
		else
			return String.format(precision, valueOnTotal) + " %";
	}

	/**
	 * NUMBERS
	 */

	static public String getNiceRoundedFloat(float originalValue, int digitsPreserved)
	{
		return getNiceFloat(MathUtils.getToughRound(originalValue,digitsPreserved));
	}

	static public String getNiceFloat(float f)
	{
		String toReturn;
		if (f == (int) f)
			toReturn = String.format("%d", (int) f);
		else
			toReturn =  String.format("%s", f);
		return removedUnusedZero(toReturn);
	}

	static public String getNiceFloat(float f, int digits)
	{
		String toReturn;
		String precision = "%." + digits + "f";

		if (f == (int) f)
			toReturn = String.format("%d", (int) f);
		else
			toReturn = String.format(precision, f);
		return removedUnusedZero(toReturn);
	}

	static public String niceDouble(double f)
	{
		if (f == (int) f)
			return String.format("%d", (int) f);
		else
			return String.format("%s", f);
	}

	static public String niceDouble(double f, int digits)
	{
		if (f == (int) f)
			return String.format("%d", (int) f);
		else
			return String.format("%." + digits + "f", f);
	}

	/**
	 * TIME AND DURATION
	 */

	static public String niceMinuteOrHour(int minuteOrHour)
	{
		if (minuteOrHour == 0)
		{
			return "00";
		}
		else
		{
			return ((minuteOrHour < 10) ? "0" : "") + minuteOrHour;
		}
	}

	/**
	 * SPANNABLE STRING
	 */

	static public boolean isNotnullOrEmpty(SpannableStringBuilder spannableStringBuilder)
	{
		return spannableStringBuilder != null && spannableStringBuilder.toString().isEmpty() == false;
	}

	static public void replace(SpannableStringBuilder globalString, String oldText, String newText)
	{
		int index = TextUtils.indexOf(globalString,oldText);
		while(index != -1)
		{
			globalString.replace(index, index + oldText.length(), newText);
			index = TextUtils.indexOf(globalString,oldText);
		}
	}


	static public void setColor(SpannableStringBuilder globalStringSpannableStringBuilder, String stringToColor, int colorId)
	{
		final Pattern                ptn       = Pattern.compile(stringToColor);
		final Matcher                matcher   = ptn.matcher(globalStringSpannableStringBuilder);

		while (matcher.find())
		{
			globalStringSpannableStringBuilder.setSpan(new ForegroundColorSpan(Badass.getColor(colorId)), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	static public void setBold(SpannableStringBuilder globalStringSpannableStringBuilder, String stringToBold)
	{
		final Pattern                ptn       = Pattern.compile(stringToBold);
		final Matcher                matcher   = ptn.matcher(globalStringSpannableStringBuilder);

		while (matcher.find())
		{
			globalStringSpannableStringBuilder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	static public void setColor(SpannableStringBuilder globalString, int stringToColorId, int colorId)
	{
		setColor(globalString, Badass.getString(stringToColorId),colorId);
	}

	static public SpannableStringBuilder getColoredSpannableStringBuilder(String globalString, String stringToColor, int colorId)
	{
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(globalString);
		setColor(spannableStringBuilder,stringToColor,colorId);
		return spannableStringBuilder;
	}


	/**
	 * INTERNAL COOKING
	 */

	static String removedUnusedZero(String s)
	{
		s = !s.contains(".") ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;

	}
}
