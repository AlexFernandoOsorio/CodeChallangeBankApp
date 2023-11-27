package com.example.codechallangebankapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.codechallangebankapp.core.utils.Constants.AUTH_KEY
import com.example.codechallangebankapp.core.utils.Constants.AUTH_TIMER
import com.example.codechallangebankapp.core.utils.UserToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthPreferences(private val dataStore:DataStore<Preferences>) {

    suspend fun saveAuthToken(loginToken: String, timerToken:Long){
        dataStore.edit { pref ->
            UserToken(loginToken,timerToken).let {
                pref[AUTH_KEY] = setOf(loginToken)
                pref[AUTH_TIMER] = timerToken
            }
        }
    }

    fun getAuthToken(): Flow<UserToken> {
        return dataStore.data.map { pref ->
            UserToken(
                token = pref[AUTH_KEY]?.first() ?: "",
                timer = pref[AUTH_TIMER] ?: 0L
            )
        }
    }
}