package eu.benayoun.badass.utility.math;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

/**
 * Created by Pierre on 05/04/2015.
 */
public class MathUtils
{
	// ex : classicMod(50,10) =0
	static public int classicMod(int a, int b)
	{
		int c = a % b;
		if (c <0)
		{
			return c+b;
		}
		else return  c;
	}

	static public long classicMod(long a, long b)
	{
		long c = a % b;
		if (c <0)
		{
			return c+b;
		}
		else return  c;
	}

	// 542 -> 2 in digits[0], 4 in digits[1] and 5 in digits[2]
	static public int[] getDigits(long num)
	{
		if (num <0)
		{
			num=-num;
		}
		String sNumber = String.valueOf(num);
		int[] digits = new int[sNumber.length()];
		int maxIndex = digits.length-1;

		for(int i = maxIndex; i >=0; i--)
		{
			int j = Character.digit(sNumber.charAt(i), 10);
			digits[maxIndex-i]=j;
		}
		return digits;
	}

	// basically we just get the 2 first digits as is, the third is reduce to 0 or 5, others to 0
	static public int[] getNiceDigits(long num)
	{
		int[] niceDigits = getDigits(num);
		if (niceDigits.length >2)
		{
			for (int i = 0; i < niceDigits.length - 2; i++)
			{
				if (niceDigits[i] > 5)
				{
					niceDigits[i + 1]++;
				}
				if (i < niceDigits.length - 3)
				{
					niceDigits[i] = 0;
				}
				// special case for the third digit
				if (i == niceDigits.length - 3)
				{
					if (niceDigits[niceDigits.length - 3] == 5) niceDigits[i] = 5;
					else niceDigits[i] = 0;
				}
			}


			// if second digit is superior to 10
			if (niceDigits[niceDigits.length - 2] > 9)
			{
				niceDigits[niceDigits.length - 2] = 0;
				niceDigits[niceDigits.length - 1]++;
			}
		}

		// if first digit is > 9 we must add a new digit
		if (niceDigits[niceDigits.length-1]>9)
		{
			niceDigits[niceDigits.length-1]=0;
			int[] newNiceDigits= new int[niceDigits.length+1];
			System.arraycopy(niceDigits,0,newNiceDigits,0,niceDigits.length);
			newNiceDigits[newNiceDigits.length-1]=1;
			niceDigits=newNiceDigits;
		}
		return niceDigits;
	}

	static public long getNiceNum(long num)
	{
		int[] niceDigits = getDigits(num);
		long niceNum =0;
		long ten_power = 1;
		for (int i = 0; i < niceDigits.length; i++)
		{
			niceNum+=niceDigits[i]*ten_power;
			ten_power*=10;
		}
		return niceNum;
	}


	static public long getNiceRoundedCast(float originalValue)
	{
		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Long.valueOf(df.format(originalValue));

	}

	/*
		Examples :
		getToughRound(2.536, 2) -> 2.54
		getToughRound(2.536, 1) -> 2.5
		getToughRound(2.536, 0) -> 3

		Special
		MathUtils.getToughRound(0.0000067f,1) -> ??
	 */

	static public float getToughRound(float originalValue, int digitsPreserved)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("#");
		if (digitsPreserved >0)
		{
			stringBuilder.append(".");
			for (int i = 0; i < digitsPreserved; i++)
			{
				stringBuilder.append("#");
			}
		}
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat(stringBuilder.toString(),otherSymbols);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Float.valueOf(df.format(originalValue));
	}

	/*
		Examples :
		getToughRound(2.536, 2) -> 2.54
		getToughRound(2.536, 1) -> 2.5
		getToughRound(2.536, 0) -> 3

		Special
		MathUtils.getToughRound(0.0000067f,1) -> ??
	 */

	static public double getToughRound(double originalValue, int digitsPreserved)
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("#");
		if (digitsPreserved >0)
		{
			stringBuilder.append(".");
			for (int i = 0; i < digitsPreserved; i++)
			{
				stringBuilder.append("#");
			}
		}
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat(stringBuilder.toString(),otherSymbols);
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Double.valueOf(df.format(originalValue));
	}

	static public int getTwoDecimalsValue(float originalValue)
	{
		float modulus = originalValue % 1;

		// no decimal
		if (modulus == 0) return 0;
		else
		{
			return (int) (originalValue*100)%100;
		}
	}

	static public boolean getPercentageChance(int percentage)
	{
		return (new Random().nextInt(100)+1 <= percentage);
	}

	static public long getNextLong(long maxValue)
	{
		return (long)(new Random().nextDouble()*(maxValue)+1);
	}
	static public long toLong(double doubleValue)
	{
		return Double.doubleToRawLongBits(doubleValue);
	}

	static public double toDouble(long longValue)
	{
		return Double.longBitsToDouble(longValue);
	}


	/**
	 * INTERNAL COOKING
	 */
}

