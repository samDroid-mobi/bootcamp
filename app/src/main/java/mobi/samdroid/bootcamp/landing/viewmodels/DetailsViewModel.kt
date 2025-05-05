package mobi.samdroid.bootcamp.landing.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mobi.samdroid.bootcamp.landing.repositories.DetailsRepository

class DetailsViewModel : ViewModel() {
    private val _repository = DetailsRepository()

    private val _liveDescription = MutableLiveData<String?>()

    fun liveDescription() = _liveDescription

    fun getDescription() {
        _repository.getDescription(
            onFinish = { _, description ->
                _liveDescription.value = description
            })
    }
}