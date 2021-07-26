package example.com.tddlogin

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import example.com.tddlogin.ui.login.LoginViewModel
import org.junit.Rule
import org.junit.rules.RuleChain
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import example.com.tddlogin.data.AuthenticationManager
import example.com.tddlogin.network.AuthenticationService
import example.com.tddlogin.network.LoginResponse
import example.com.tddlogin.ui.login.Error
import example.com.tddlogin.ui.login.TokenLoaded
import example.com.tddlogin.util.getValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response


@HiltAndroidTest
class LoginViewModelTest {
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    @Mock
    lateinit var authenticationManager: AuthenticationManager

    @Mock
    lateinit var authenticationService: AuthenticationService

    @Mock
    lateinit var savedStateHandle: SavedStateHandle

    @Mock
    lateinit var context: Context

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()

        context = getApplicationContext<TddLoginApplication>()
        val sharedPreferences = context.getSharedPreferences(
            "prefs",
            MODE_PRIVATE
        );

        authenticationManager = AuthenticationManager(sharedPreferences)
        authenticationService = AuthenticationService.create()
        savedStateHandle =  SavedStateHandle()

        viewModel = LoginViewModel(authenticationManager,authenticationService,savedStateHandle)

    }

    @Test
    @Throws(InterruptedException::class)
    fun loadToken_success() = runBlocking {
        viewModel.gettingAuthToken()
        delay(500L)


        Assert.assertEquals(
            TokenLoaded("{\n" +
                    "  \"access_token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHA6dXNlcl9pZCI6IjUwY TdkYTFkLWZlMDctNGMxNC04YjFiLTAwNzczN2Y0Nzc2MyIsImlkcDp1c2VyX25h bWUiOiJqZG9lIiwiaWRwOmZ1bGxuYW1lIjoiSm9obiBEb2UiLCJyb2xlIjoiZWR pdG9yIiwiZXhwIjoxNTU2NDc2MjU1fQ.iqFmotBtfAYLplfpLVh_kPgvOIPyV7U Mm-NZA06XA5I\",\n" +
                    "  \"token_type\":\"bearer\",\n" +
                    "  \"expires_in\":119,\n" +
                    "  \"refresh_token\":\"NTBhN2RhMWQtZmUwNy00YzE0LThiMWItMDA3NzM3ZjQ3NzYzIyNkNmQ5OTViZS 1jY2IxLTQ0MGUtODM4NS1lOTkwMTEwMzBhYzA=\"\n" +
                    "}"),
            getValue(viewModel.viewState)
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun gettingAuthToken_emptyBody_error() = runBlocking {
        val emptyBodyAuthService = object : AuthenticationService{
            override suspend fun login(
                username: String,
                password: String,
                grantType: String,
                client_id: String
            ): Response<LoginResponse> =
                Response.success(null)

            override suspend fun extend(
                refreshToken: String,
                grantType: String,
                client_id: String
            ): Response<LoginResponse> =
                Response.success(null)

        }

        viewModel = LoginViewModel(
            authenticationManager,
            emptyBodyAuthService,
            savedStateHandle
        )

        viewModel.gettingAuthToken()

        delay(500L)

        Assert.assertEquals(
            Error("Unexpected error."),
            viewModel.viewState.value
        )
    }


    @Test
    @Throws(InterruptedException::class)
    fun gettingAuthToken_badResponse_error() = runBlocking {
        val badResponseAuthService = object : AuthenticationService{
            override suspend fun login(
                username: String,
                password: String,
                grantType: String,
                client_id: String
            ): Response<LoginResponse> =
                Response.error(
                    401,
                    ResponseBody.create(
                        "application/json".toMediaTypeOrNull(),
                        "{\"message\":\"Not found\"}"
                    )
                )

            override suspend fun extend(
                refreshToken: String,
                grantType: String,
                client_id: String
            ): Response<LoginResponse> =
                Response.success(null)

        }

        viewModel = LoginViewModel(
            authenticationManager,
            badResponseAuthService,
            savedStateHandle
        )

        viewModel.gettingAuthToken()

        delay(500L)

        Assert.assertEquals(
            Error("Invalid username or password."),
            getValue(viewModel.viewState)
        )
    }

}

