package example.com.tddlogin.ui.home

import androidx.lifecycle.*
import com.auth0.android.jwt.JWT
import dagger.hilt.android.lifecycle.HiltViewModel
import example.com.tddlogin.data.AuthenticationManager
import example.com.tddlogin.data.User
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(
    private val authenticationManager: AuthenticationManager
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    init {
        fetchToken()
    }

    fun fetchToken(){
        viewModelScope.launch {
            val token = authenticationManager.getAuthenticatedUserToken()

            _user.value = tokenToUserMapper(token)

        }

    }

    fun tokenToUserMapper(token: String): User{
        val jwt = JWT(token)
        return User (jwt.getClaim("idp:user_id").asString()!!,
            jwt.getClaim("idp:user_name").asString()!!,
            jwt.getClaim("idp:fullname").asString()!!,
            jwt.getClaim("role").asString()!!  )
    }



}