package mobi.samdroid.bootcamp.landing.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mobi.samdroid.bootcamp.base.SUser
import mobi.samdroid.bootcamp.landing.repositories.MainRepository

class MainViewModel: ViewModel() {
    private val _repository = MainRepository()

    private val _liveUsers = MutableLiveData<ArrayList<SUser>>()
    var user: SUser? = null

    fun liveUsers() = _liveUsers

    fun getUsers(context: Context) {
        _repository.getAllUsers(viewModelScope, context, onFinish = { users ->
            _liveUsers.value = users as ArrayList<SUser>
        })
    }
}