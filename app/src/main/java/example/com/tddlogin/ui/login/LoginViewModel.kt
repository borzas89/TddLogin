package example.com.tddlogin.ui.login

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.tddlogin.data.AuthenticationManager
import example.com.tddlogin.network.AuthenticationService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val authenticationService: AuthenticationService,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableLiveData<LoginViewState>()
    val viewState: LiveData<LoginViewState>
        get() = _viewState

    val username: MutableLiveData<String> = savedStateHandle.getLiveData("username", "")
    val password: MutableLiveData<String> = savedStateHandle.getLiveData("password", "")

    fun saveAccessToken(accessToken: String) {
        authenticationManager.saveToken(accessToken)
    }

    fun gettingAuthToken(){
        _viewState.value = Loading

        val clientId = authenticationManager.getClientId()

        viewModelScope.launch {

         try {

             val loginResponse =  authenticationService.login(username.value!!.toString(),
                 password.value!!.toString(),
                 "password",clientId)

             if(loginResponse.isSuccessful){

                 val tokenResponse = loginResponse.body()!!

                 if(tokenResponse != null){

                     _viewState.value = TokenLoaded(tokenResponse.accessToken)


                 } else {
                     _viewState.value = Error("Response body is empty")
                 }

             } else {
                 _viewState.value =
                     Error("Bad response: HTTP ${loginResponse.code()}")
             }


         } catch (e: Exception) {
             _viewState.value = Error("Response body is empty")
         }

      }


    }





}