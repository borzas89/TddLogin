package example.com.tddlogin.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationManager @Inject constructor(val sharedPref: SharedPreferences) {

    fun isAuthenticated(): Boolean =
        sharedPref.getString("token", "")!!.isNotEmpty()

    fun saveToken(token: String) {
        sharedPref.edit().putString("token", token).apply()
    }

    fun clearRegistration() {
        sharedPref.edit().remove("token").apply()
    }

    fun getAuthenticatedUserToken(): String {
        return checkNotNull(sharedPref.getString("token", "").takeIf { it!!.isNotEmpty() })
    }
}