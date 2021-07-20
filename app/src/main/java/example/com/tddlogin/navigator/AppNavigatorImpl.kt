package example.com.tddlogin.navigator

import android.app.Activity
import androidx.navigation.Navigation
import example.com.tddlogin.R
import example.com.tddlogin.ui.login.LoginFragmentDirections
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(
    private val activity: Activity
) : AppNavigator {

    override fun navigateToHome() {
        navController.navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        )
    }

    private val navController by lazy {
        Navigation.findNavController(activity, R.id.fragmentNavHost)
    }

    override fun popBackStack() {
        navController.popBackStack()
    }


}
