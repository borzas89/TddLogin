package example.com.tddlogin.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationManager @Inject constructor(val sharedPref: SharedPreferences) {

    fun getClientId(): String  = "69bfdce9-2c9f-4a12-aa7b-4fe15e1228dc"

    fun isAuthenticated(): Boolean =
        sharedPref.getString("token", "")!!.isNotEmpty()

    fun saveToken(token: String) {
        sharedPref.edit().putString("token", token).apply()
    }

    fun saveRefreshToken(token: String) {
        sharedPref.edit().putString("refresh_token", token).apply()
    }

    fun saveExpiryDate(expDate: Long) {
        sharedPref.edit().putLong("expiry", expDate).apply()
    }

    fun getExpiryDate(): Long {
        return sharedPref.getLong("expiry", 0L)
    }

    fun clearRegistration() {
        sharedPref.edit().remove("token").apply()
    }

    fun getAuthenticatedUserToken(): String {
        return checkNotNull(sharedPref.getString("token", "").takeIf { it!!.isNotEmpty() })
    }

    fun getRefreshToken(): String {
        return checkNotNull(sharedPref.getString("refresh_token", "").takeIf { it!!.isNotEmpty() })
    }
}