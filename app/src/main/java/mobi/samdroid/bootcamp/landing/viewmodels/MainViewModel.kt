package mobi.samdroid.bootcamp.landing.viewmodels

import androidx.lifecycle.ViewModel
import mobi.samdroid.bootcamp.base.SUser

class MainViewModel: ViewModel() {
    var user: SUser? = null

    fun getUsers(): ArrayList<SUser> {
        return ArrayList<SUser>().apply {
            add(SUser("Sam Shouman", "sam@samdroid.mobi", "+961 3 943 517"))
            add(SUser("Hussein Mbarak", "hussein.mbarak@gmail.com", "+961 70 891 123"))
        }
    }
}