package eu.benayoun.badass.utility.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import eu.benayoun.badass.Badass;


/**
 * Created by Pierre on 01/05/2015.
 */
public class BadassSharedPreferencesStorage
{

	protected SharedPreferences sharedPreferences;
	protected SharedPreferences.Editor editor;
	protected BadassSharedPreferencesStorageContract subCache;

	@SuppressLint("CommitPrefEdits")
    public BadassSharedPreferencesStorage(String fileName, BadassSharedPreferencesStorageContract subCache)
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

}
