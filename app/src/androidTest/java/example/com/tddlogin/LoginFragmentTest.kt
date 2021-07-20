package example.com.tddlogin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith


@HiltAndroidTest
class LoginFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)


    @Test
    fun testLoginEnteredUserPassword_LoginButtonisEnable() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.etPassword))
            .perform(typeText("password"))

        onView(withId(R.id.btnLogin)).check(matches(isEnabled()));

    }


    @Test
    fun testLoginEnteredUser_LoginButtonisNotEnabled() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.btnLogin)).check(matches(isNotEnabled()));

    }

}