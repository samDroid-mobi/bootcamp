package mobi.samdroid.bootcamp.landing.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobi.samdroid.bootcamp.base.DataStoreManager

class LandingRepository {
    fun getSavedData(
        viewModelScope: CoroutineScope,
        context: Context,
        onFinish: (isRememberMe: Boolean, username: String, password: String, isLoggedIn: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val isRememberMeEnabled = DataStoreManager.isRememberMeEnabled(
                context, booleanPreferencesKey(
                    DataStoreManager.PREF_KEY_REMEMBER_ME
                )
            )

            val username = DataStoreManager.getUsername(
                context, stringPreferencesKey(
                    DataStoreManager.PREF_KEY_USERNAME
                )
            )

            val password = DataStoreManager.getPassword(
                context, stringPreferencesKey(
                    DataStoreManager.PREF_KEY_PASSWORD
                )
            )

            val isLoggedIn = DataStoreManager.isLoggedIn(
                context, booleanPreferencesKey(
                    DataStoreManager.PREF_KEY_LOGGED_IN
                )
            )

            onFinish(isRememberMeEnabled, username, password, isLoggedIn)
        }
    }

    fun logout(viewModelScope: CoroutineScope, context: Context, onFinish: () -> Unit) {
        viewModelScope.launch {
            val isRememberMe = DataStoreManager.isRememberMeEnabled(
                context, booleanPreferencesKey(
                    DataStoreManager.PREF_KEY_REMEMBER_ME
                )
            )

            DataStoreManager.setLoggedIn(
                context,
                false,
                booleanPreferencesKey(DataStoreManager.PREF_KEY_LOGGED_IN)
            )

            if(isRememberMe.not()) {
                DataStoreManager.setUsername(
                    context,
                    "",
                    stringPreferencesKey(DataStoreManager.PREF_KEY_USERNAME)
                )

                DataStoreManager.setPassword(
                    context,
                    "",
                    stringPreferencesKey(DataStoreManager.PREF_KEY_PASSWORD)
                )
            }

            onFinish()
        }
    }
}