package example.com.tddlogin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testLoginEnteredUserPassword_isEnable() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.etPassword))
            .perform(typeText("password"))

        onView(withId(R.id.btnLogin)).check(matches(isEnabled()));

    }


    @Test
    fun testLoginEnteredUserPassword_isNotEnabled() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.btnLogin)).check(matches(isNotEnabled()));

    }



}