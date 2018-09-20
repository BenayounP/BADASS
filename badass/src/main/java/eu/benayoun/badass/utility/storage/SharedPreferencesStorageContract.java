package eu.benayoun.badass.utility.storage;

import android.content.SharedPreferences;

public interface SharedPreferencesStorageContract
{
	void load(SharedPreferences sharedPreferences);
	void save(SharedPreferences.Editor editor);
}
