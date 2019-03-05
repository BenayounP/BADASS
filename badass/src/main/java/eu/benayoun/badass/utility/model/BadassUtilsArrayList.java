package eu.benayoun.badass.utility.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by PierreB on 25/11/2016.
 */
public class BadassUtilsArrayList
{
	public static boolean isNullOrEmpty(ArrayList arrayList)
	{
		return arrayList == null || arrayList.isEmpty();
	}

	public static boolean isNOTNullOrEmpty(ArrayList arrayList)
	{
		return arrayList != null && arrayList.isEmpty() == false;
	}

	public static <T> T getLastElement(ArrayList<T> arrayList)
	{
		T t = null;
		if (false == isNullOrEmpty(arrayList))
		{
			t = arrayList.get(arrayList.size() - 1);
		}
		return t;
	}

	public static <T> T getRandomElement(ArrayList<T> arrayList)
	{
		return arrayList.get(getRandomIndex(arrayList));
	}

	public static <T> int getRandomIndex(ArrayList<T> arrayList)
	{
		return new Random().nextInt(arrayList.size());
	}

	public static int getSize(ArrayList arrayList)
	{
		if (arrayList == null) return -1;
		else return arrayList.size();
	}

	public static <T> ArrayList<T> getCopy(ArrayList<T> oldArrayList)
	{
		ArrayList<T> newArrayList = new ArrayList<>(oldArrayList.size());
		newArrayList.addAll(oldArrayList);
		return newArrayList;
	}



}