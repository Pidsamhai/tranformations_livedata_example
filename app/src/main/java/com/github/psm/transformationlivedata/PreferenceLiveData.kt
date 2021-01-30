package com.github.psm.transformationlivedata

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData

private class PreferenceLiveData(private val preferences: SharedPreferences, private val _key: String) : LiveData<String>() {
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        Log.i(this.javaClass.name, "$_key = $key")
        if (_key == key) {
            value = sharedPreferences.getString(key, null)
        }
    }


    override fun onActive() {
        super.onActive()
        value = preferences.getString(_key, null)
        preferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        if (!hasActiveObservers())
            preferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}

fun SharedPreferences.liveData(key: String): LiveData<String> {
    return PreferenceLiveData(this, key)
}