package mobi.samdroid.bootcamp.signup

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import mobi.samdroid.bootcamp.base.SUser

class SignUpViewModel : ViewModel() {
    private val _liveIsRememberMe = MutableLiveData<Boolean>()
    private val _liveIsLoggedIn = MutableLiveData<Boolean>()
    private val _liveUsername = MutableLiveData<String>()
    private val _livePassword = MutableLiveData<String>()
    private val _liveUser = MutableLiveData<SUser?>()
    private val _liveIsUserRegistered = MutableLiveData<Boolean>()
    private val _liveCheckIfUserRegistered = MutableLiveData<Boolean>()

    private val _repository: SignUpRepository = SignUpRepository()

    fun liveIsRememberMe() = _liveIsRememberMe
    fun liveUsername() = _liveUsername
    fun livePassword() = _livePassword
    fun liveIsLoggedIn() = _liveIsLoggedIn
    fun liveUser() = _liveUser
    fun liveIsUserRegistered() = _liveIsUserRegistered
    fun liveCheckIfUserRegistered() = _liveCheckIfUserRegistered

    fun validateUsername(username: String): Boolean {
        return username.length >= 3
    }

    fun areCredentialsAvailable(username: String, password: String): Boolean {
        return username.isNotEmpty() && password.isNotEmpty()
    }

    fun setRememberMe(context: Context, isRememberMeEnabled: Boolean) {
        _repository.setRememberMe(viewModelScope, context, isRememberMeEnabled)
    }

    fun saveUsername(context: Context, username: String) {
        _repository.saveUsername(viewModelScope, context, username)
    }

    fun savePassword(context: Context, password: String) {
        _repository.savePassword(viewModelScope, context, password)
    }

    fun setLoggedIn(context: Context) {
        _repository.setLoggedIn(viewModelScope, context)
    }

    fun getSavedData(context: Context) {
        _repository.getSavedData(viewModelScope, context) { isRememberMe, isLoggedIn ->
            _liveIsRememberMe.value = isRememberMe
            _liveIsLoggedIn.value = isLoggedIn
        }
    }

    fun getCredentials(context: Context) {
        _repository.getCredentials(viewModelScope, context) { username, password ->
            _liveUsername.value = username
            _livePassword.value = password
        }
    }

    fun checkIfUserFound(context: Context, username: String, password: String) {
        _repository.checkIfUserFound(viewModelScope, context, username, password) { user ->
            _liveUser.value = user
        }
    }

    fun registerUser(context: Context, username: String, password: String) {
        _repository.registerUser(viewModelScope, context, username, password, onFinish = { isSuccess ->
            _liveIsUserRegistered.value = isSuccess
        })
    }

    fun checkIfUserAlreadyRegistered(context: Context, username: String) {
        _repository.checkIfUserAlreadyRegistered(viewModelScope, context, username) { user ->
            _liveCheckIfUserRegistered.value = user != null
        }
    }
}