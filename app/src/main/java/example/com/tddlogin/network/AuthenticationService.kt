package example.com.tddlogin.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*

interface AuthenticationService {

    @Headers("Content-Type: application/json")
    @POST("/idp/api/v1/token")
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("grant_type") grantType: String,
              @Field("client_id") client_id: String, ): Deferred<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("/idp/api/v1/token")
    fun extend(
              @Field("refresh_token") refreshToken: String,
              @Field("grant_type") grantType: String,
              @Field("client_id") client_id: String, ): Deferred<LoginResponse>

    companion object {
        private const val BASE_URL = "https://example.vividmindsoft.com/"


        fun create(): AuthenticationService {
            val moshi = Moshi.Builder()
                .add(Date::class.java, Rfc3339DateJsonAdapter())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(AuthenticationService::class.java)
        }
    }

}