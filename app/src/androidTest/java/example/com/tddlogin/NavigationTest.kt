package example.com.tddlogin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.statement.UiThreadStatement
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import example.com.tddlogin.ui.login.LoginFragment
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@HiltAndroidTest
class NavigationTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)

    @Test
    fun testNavigateToHome() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()).apply {
            UiThreadStatement.runOnUiThread {
                setGraph(R.navigation.app_nav_graph)
            }

            UiThreadStatement.runOnUiThread {
                setCurrentDestination(R.id.loginFragment)
            }

        }

        // Create a graphical FragmentScenario for the Login fragment
        val loginScenario = launchFragmentInContainer<LoginFragment>()

        // Set the NavController property on the fragment
        loginScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.etUserName))
            .perform(ViewActions.typeText("username"))

        Espresso.onView(ViewMatchers.withId(R.id.etPassword))
            .perform(ViewActions.typeText("password"))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin)).perform(ViewActions.click())

        val backStack = navController.backStack
        val currentDestination = backStack.last()
        Truth.assertThat(currentDestination.destination.id).isEqualTo(R.id.homeFragment)
    }

}