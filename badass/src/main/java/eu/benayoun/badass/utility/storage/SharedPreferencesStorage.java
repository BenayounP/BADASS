package eu.benayoun.badass.utility.storage;

import android.content.Context;
import android.content.SharedPreferences;

import eu.benayoun.badass.Badass;


/**
 * Created by Pierre on 01/05/2015.
 */
public class SharedPreferencesStorage
{

	protected SharedPreferences sharedPreferences=null;
	protected SharedPreferences.Editor editor=null;
	protected SharedPreferencesStorageContract subCache;

	public SharedPreferencesStorage(String fileName, SharedPreferencesStorageContract subCache)
	{
		sharedPreferences = Badass.getApplicationContext().getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		editor =sharedPreferences.edit();
		this.subCache = subCache;
	}


	public void removeData()
	{
		editor.clear();
	}

	public void load()
	{
		subCache.load(sharedPreferences);

	}

	public void save()
	{
		subCache.save(editor);
		editor.commit();
	}

	public SharedPreferences getSharedPreferences()
	{
		return sharedPreferences;
	}

	public SharedPreferences.Editor getEditor()
	{
		return editor;
	}

	/**
	 * INTERNAL COOKING
	 */

}
