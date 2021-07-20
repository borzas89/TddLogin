package example.com.tddlogin.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    @Json(name = "access_token")
    var accessToken: String? = null,

    @Json(name = "token_type")
    var tokenType: String? = null,

    @Json(name = "expires_in")
    var expiresIn: Int? = null,

    @Json(name = "refresh_token")
    var refreshToken: String? = null
)