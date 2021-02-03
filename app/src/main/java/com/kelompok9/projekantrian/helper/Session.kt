package com.kelompok9.projekantrian.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object Session {
    private const val TOKEN_BEARER_KEY = "id"

    private var dataStore: DataStore<Preferences>? = null

    fun init(context: Context) {
        dataStore = context.createDataStore(name = "session")
    }

    fun unset() {
        GlobalScope.launch {
            dataStore?.edit { session ->
                session.clear()
            }
        }
    }

    var id: String?
        get() =
            runBlocking {
                val dataStoreKey = stringPreferencesKey(TOKEN_BEARER_KEY)
                val preferences = dataStore?.data?.first()

                preferences?.get(dataStoreKey)
            }
        set(value) {
            GlobalScope.launch {
                val dataStoreKey = stringPreferencesKey(TOKEN_BEARER_KEY)
                dataStore?.edit { session ->
                    session[dataStoreKey] = value ?: ""
                }
            }
        }
}