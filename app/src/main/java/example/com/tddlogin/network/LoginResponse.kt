package example.com.tddlogin.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse (
    @Json(name = "access_token")
    var accessToken: String,

    @Json(name = "token_type")
    var tokenType: String,

    @Json(name = "expires_in")
    var expiresIn: Int,

    @Json(name = "refresh_token")
    var refreshToken: String
)