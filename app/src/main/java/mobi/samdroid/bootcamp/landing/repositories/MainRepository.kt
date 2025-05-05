package mobi.samdroid.bootcamp.landing.repositories

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.base.database.AppDatabase

class MainRepository {

    fun getAllUsers(viewModelScope: CoroutineScope, context: Context, onFinish: (users: List<SUser>) -> Unit) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val users = db.userDao().getAllUsers()

            onFinish(users)
        }

    }
}