package example.com.tddlogin.ui.home

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.tddlogin.data.AuthenticationManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {

    private val _loginResponse = MutableLiveData<String>()
    val loginResponse: LiveData<String>
        get() = _loginResponse


    init {
        fetchToken()
    }

    fun fetchToken(){
        // TODO encode the token...
        viewModelScope.launch {
            val login = authenticationManager.getAuthenticatedUserToken()

            _loginResponse.value = login

        }


    }



}