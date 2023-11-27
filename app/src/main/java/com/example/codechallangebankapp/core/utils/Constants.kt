package com.example.codechallangebankapp.core.utils

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.codechallangebankapp.BuildConfig


object Constants {
    const val BASE_URL = BuildConfig.BASE_URL
    const val END_POINT_LOGIN = BuildConfig.END_POINT_LOGIN
    const val END_POINT_ACCOUNT = BuildConfig.END_POINT_ACCOUNT
    const val END_POINT_ACCOUNT_UPDATED = BuildConfig.END_POINT_ACCOUNT_UPDATED
    const val END_POINT_ACCOUNT_MOVEMENTS = BuildConfig.END_POINT_ACCOUNT_DETAILS

    const val AUTH_PREFERENCES = "AUTH_PREF"
    val AUTH_KEY = stringSetPreferencesKey("auth_key")
    val AUTH_TIMER = longPreferencesKey("auth_timer")
}