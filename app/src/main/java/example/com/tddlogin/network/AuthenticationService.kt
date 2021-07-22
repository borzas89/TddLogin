package example.com.tddlogin.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.*
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*


interface AuthenticationService {

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/idp/api/v1/token")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") client_id: String,
    ): Response<LoginResponse>

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/idp/api/v1/token")
    suspend fun extend(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String,
        @Field("client_id") client_id: String,
    ): Response<LoginResponse>

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