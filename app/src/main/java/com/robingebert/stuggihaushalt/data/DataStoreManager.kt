package com.robingebert.stuggihaushalt.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class DataStoreManager(private val context: Context) {

    //region Boilerplate
    companion object {
        private val Context.dataStore by preferencesDataStore(name = "preferences")
    }

    // CoroutineScope für alle Präferenzen
    private val scope = CoroutineScope(Dispatchers.IO)

    // DataStore-Instanz
    private val dataStore = context.dataStore

    class Preference<T>(
        private val keyName: String,
        private val defaultValue: T,
        private val dataStore: DataStore<Preferences>
    ) : ReadOnlyProperty<DataStoreManager, PreferenceData<T>> {

        private val key: Preferences.Key<T> = when (defaultValue) {
            is String -> stringPreferencesKey(keyName)
            is Int -> intPreferencesKey(keyName)
            is Boolean -> booleanPreferencesKey(keyName)
            is Float -> floatPreferencesKey(keyName)
            is Long -> longPreferencesKey(keyName)
            else -> throw IllegalArgumentException("Unsupported type")
        } as Preferences.Key<T>

        private val preferenceData = PreferenceData(
            dataStore = dataStore,
            key = key,
            defaultValue = defaultValue,
            scope = CoroutineScope(Dispatchers.IO)
        )

        override fun getValue(thisRef: DataStoreManager, property: KProperty<*>): PreferenceData<T> {
            return preferenceData
        }
    }

    class PreferenceData<T>(
        private val dataStore: DataStore<Preferences>,
        private val key: Preferences.Key<T>,
        private val defaultValue: T,
        private val scope: CoroutineScope
    ) {
        val flow: StateFlow<T> = dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = defaultValue
            )

        fun set(newValue: T) {
            scope.launch {
                dataStore.edit { preferences ->
                    preferences[key] = newValue
                }
            }
        }
    }
    //endregion

    // Präferenzen definieren
    val userName by Preference("username", "Mone", dataStore)
    val qsEnabled by Preference("qs_enabled", false, dataStore)
    val isSnuActive by Preference("snu_active", false, dataStore)
}
