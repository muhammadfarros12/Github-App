package com.farroos.githubapp.ui.nightMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farroos.githubapp.ui.main.UserViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

}