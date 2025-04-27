package mobi.samdroid.bootcamp.landing.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mobi.samdroid.bootcamp.landing.repositories.LandingRepository
import mobi.samdroid.bootcamp.signup.SignUpRepository

class LandingViewModel: ViewModel() {
    private val _liveLogout = MutableLiveData<Boolean>()
    private val _liveUsername = MutableLiveData<String>()
    private val _livePassword = MutableLiveData<String>()

    private val _repository: LandingRepository = LandingRepository()

    fun liveLogout() = _liveLogout
    fun liveUsername() = _liveUsername
    fun livePassword() = _livePassword

    fun getSavedData(context: Context) {
        _repository.getSavedData(viewModelScope, context) { _, username, password, _ ->
            _liveUsername.value = username
            _livePassword.value = password
        }
    }

    fun logout(context: Context) {
        _repository.logout(viewModelScope, context) {
            _liveLogout.value = true
        }
    }
}