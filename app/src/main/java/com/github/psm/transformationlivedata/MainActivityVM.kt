package com.github.psm.transformationlivedata

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*

@Suppress("EXPERIMENTAL_API_USAGE")
class MainActivityVM(application: Application) : AndroidViewModel(application) {
    private val pref = application.getSharedPreferences("pref", Context.MODE_PRIVATE)
    private val _key = MutableLiveData("initKey")
    val key: LiveData<String> = _key
    val prefValue: LiveData<String> = key.switchMap {
        Log.i(this.javaClass.simpleName, "Key Change: $it")
        pref.liveData(it)
    }

    private val _filterMap = MutableLiveData("")
    private val filterMap: LiveData<String> = _filterMap

    val prefValueWithFilter = prefValue.map {
        "$it : ${filterMap.value}"
    }

    fun submitKeyValue(key: String, value: String) {
        pref.edit()
            .putString(key, value)
            .apply()
        _key.postValue(key)
        viewModelScope
    }

    fun submitOnlyValue(value: String) {
        pref.edit()
            .putString(key.value, value)
            .apply()
    }

    fun submitOnlyKey(key: String) = _key.postValue(key)

    fun submitFilterMap(filterMap: String) = _filterMap.postValue(filterMap)
}
