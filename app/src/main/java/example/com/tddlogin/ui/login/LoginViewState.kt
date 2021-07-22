package example.com.tddlogin.ui.login

sealed class LoginViewState

object Loading : LoginViewState()

data class Error(
    val message: String
) : LoginViewState()

data class TokenLoaded(
    val token: String
) : LoginViewState()
