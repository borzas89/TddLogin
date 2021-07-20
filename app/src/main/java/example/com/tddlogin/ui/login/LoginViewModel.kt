package example.com.tddlogin.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.tddlogin.network.AuthenticationService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val authenticationService: AuthenticationService
) : ViewModel() {





}