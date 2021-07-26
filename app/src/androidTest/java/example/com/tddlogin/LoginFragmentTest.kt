package example.com.tddlogin

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain


@HiltAndroidTest
class LoginFragmentTest {

    private val hiltRule = HiltAndroidRule(this)
    private val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(activityTestRule)


    @Test
    fun testLoginEnteredUserPassword_LoginButtonIsEnable() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.etPassword))
            .perform(typeText("password"))

        onView(withId(R.id.btnLogin)).check(matches(isEnabled()));

    }


    @Test
    fun testLoginEnteredUser_LoginButtonIsNotEnabled() {

        onView(withId(R.id.etUserName))
            .perform(typeText("username"))

        onView(withId(R.id.btnLogin)).check(matches(isNotEnabled()));

    }

}