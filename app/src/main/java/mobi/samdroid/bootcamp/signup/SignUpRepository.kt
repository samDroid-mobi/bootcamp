package mobi.samdroid.bootcamp.signup

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobi.samdroid.bootcamp.base.DataStoreManager
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.base.database.AppDatabase

class SignUpRepository {

    fun getSavedData(
        viewModelScope: CoroutineScope,
        context: Context,
        onFinish: (isRememberMe: Boolean, isLoggedIn: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            val isLoggedIn = DataStoreManager.isLoggedIn(
                context, booleanPreferencesKey(
                    DataStoreManager.PREF_KEY_LOGGED_IN
                )
            )

            val isRememberMeEnabled = DataStoreManager.isRememberMeEnabled(
                context, booleanPreferencesKey(
                    DataStoreManager.PREF_KEY_REMEMBER_ME
                )
            )

            onFinish(isRememberMeEnabled, isLoggedIn)
        }
    }


    fun getCredentials(
        viewModelScope: CoroutineScope,
        context: Context,
        onFinish: (username: String, password: String) -> Unit
    ) {
        viewModelScope.launch {
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

            onFinish(username, password)
        }
    }

    fun setRememberMe(
        viewModelScope: CoroutineScope,
        context: Context,
        isRememberMeEnabled: Boolean
    ) {
        // Save the remember me preference in DataStore
        viewModelScope.launch {
            DataStoreManager.setRememberMe(
                context,
                isRememberMeEnabled,
                booleanPreferencesKey(DataStoreManager.PREF_KEY_REMEMBER_ME)
            )
        }
    }

    fun saveUsername(viewModelScope: CoroutineScope, context: Context, username: String) {
        // Save the username in DataStore
        viewModelScope.launch {
            DataStoreManager.setUsername(
                context,
                username,
                stringPreferencesKey(DataStoreManager.PREF_KEY_USERNAME)
            )
        }
    }

    fun setLoggedIn(viewModelScope: CoroutineScope, context: Context) {
        // Save the logged-in status in DataStore
        viewModelScope.launch {
            DataStoreManager.setLoggedIn(
                context,
                true,
                booleanPreferencesKey(DataStoreManager.PREF_KEY_LOGGED_IN)
            )
        }
    }

    fun savePassword(viewModelScope: CoroutineScope, context: Context, password: String) {
        // Save the password in DataStore
        viewModelScope.launch {
            DataStoreManager.setPassword(
                context,
                password,
                stringPreferencesKey(DataStoreManager.PREF_KEY_PASSWORD)
            )
        }
    }

    fun checkIfUserFound(
        viewModelScope: CoroutineScope,
        context: Context,
        username: String,
        password: String,
        onFinish: (user: SUser?) -> Unit
    ) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val user = db.userDao().getUserByCredentials(username, password)
            onFinish(user)
        }
    }

    fun registerUser(
        viewModelScope: CoroutineScope,
        context: Context,
        username: String,
        password: String,
        onFinish: (isSuccess: Boolean) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val db = AppDatabase.getDatabase(context)
                db.userDao().insert(SUser(username = username, password = password))
                onFinish(true)
            } catch (e: Exception) {
                onFinish(false)
            }
        }
    }

    fun checkIfUserAlreadyRegistered(
        viewModelScope: CoroutineScope,
        context: Context,
        username: String,
        onFinish: (user: SUser?) -> Unit
    ) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val user = db.userDao().getUserByUsername(username)
            onFinish(user)
        }
    }
}