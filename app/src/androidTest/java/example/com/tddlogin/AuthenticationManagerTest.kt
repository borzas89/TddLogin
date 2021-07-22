package example.com.tddlogin

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import example.com.tddlogin.data.AuthenticationManager
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.mockito.Mock


@HiltAndroidTest
class AuthenticationManagerTest {

    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var authenticationManager: AuthenticationManager

    @Mock
    lateinit var context: Context

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)

    @Before
    fun setUp() = runBlocking {
        hiltRule.inject()

        context = ApplicationProvider.getApplicationContext<TddLoginApplication>()
        val sharedPreferences = context.getSharedPreferences(
            "prefs",
            Context.MODE_PRIVATE
        );

        authenticationManager = AuthenticationManager(sharedPreferences)

    }

    @Test
    fun savingTokenThenVerifyUserIsAuthenticated() {

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHA6dXNlcl9pZCI6IjUwY TdkYTFkLWZlMDctNGMxNC04YjFiLTAwNzczN2Y0Nzc2MyIsImlkcDp1c2VyX25h bWUiOiJqZG9lIiwiaWRwOmZ1bGxuYW1lIjoiSm9obiBEb2UiLCJyb2xlIjoiZWR pdG9yIiwiZXhwIjoxNTU2NDc2MjU1fQ.iqFmotBtfAYLplfpLVh_kPgvOIPyV7U Mm-NZA06XA5I";

        authenticationManager.saveToken(token)

        assertTrue(authenticationManager.isAuthenticated())
    }


    @Test
    fun savingTokenThenVerifyUserTokenIsExists() {

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHA6dXNlcl9pZCI6IjUwY TdkYTFkLWZlMDctNGMxNC04YjFiLTAwNzczN2Y0Nzc2MyIsImlkcDp1c2VyX25h bWUiOiJqZG9lIiwiaWRwOmZ1bGxuYW1lIjoiSm9obiBEb2UiLCJyb2xlIjoiZWR pdG9yIiwiZXhwIjoxNTU2NDc2MjU1fQ.iqFmotBtfAYLplfpLVh_kPgvOIPyV7U Mm-NZA06XA5I";

        authenticationManager.saveToken(token)

        assertThat(authenticationManager.getAuthenticatedUserToken() == token)
    }


    @Test
    fun deleteRegistrationThenVerifyUserIsNotAuthenticated() {

        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZHA6dXNlcl9pZCI6IjUwY TdkYTFkLWZlMDctNGMxNC04YjFiLTAwNzczN2Y0Nzc2MyIsImlkcDp1c2VyX25h bWUiOiJqZG9lIiwiaWRwOmZ1bGxuYW1lIjoiSm9obiBEb2UiLCJyb2xlIjoiZWR pdG9yIiwiZXhwIjoxNTU2NDc2MjU1fQ.iqFmotBtfAYLplfpLVh_kPgvOIPyV7U Mm-NZA06XA5I";
        authenticationManager.saveToken(token)

        authenticationManager.clearRegistration()

        assertFalse(authenticationManager.isAuthenticated())
    }
}