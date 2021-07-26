package example.com.tddlogin.ui.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import example.com.tddlogin.R
import example.com.tddlogin.data.AuthenticationManager
import example.com.tddlogin.databinding.FragmentLoginBinding
import example.com.tddlogin.navigator.AppNavigator
import example.com.tddlogin.util.hide
import example.com.tddlogin.util.onTextChanged
import example.com.tddlogin.util.show
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var authenticationManager: AuthenticationManager

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etUserName.setText(viewModel.username.value)
        binding.etPassword.setText(viewModel.password.value)

        binding.etUserName.onTextChanged { username -> viewModel.username.value = username
                                            enableLoginIfReady()
                                         }
        binding.etPassword.onTextChanged { password -> viewModel.password.value = password
                                           enableLoginIfReady()
                                         }


        binding.btnLogin.setOnClickListener {
            val isUserEmpty = (""
                    == binding.etUserName.text.toString())
            val isPasswordEmpty = "" == binding.etPassword.text
                .toString()
            if (isUserEmpty) {
                binding.etUserName.error = getString(R.string.error_field_empty)
            }
            if (isPasswordEmpty) {
                binding.etPassword.error = getString(R.string.error_field_empty)
            }
            if (!isUserEmpty && !isPasswordEmpty) {
                binding.progressBar.show()
                with(viewModel) {
                    viewState.observe(viewLifecycleOwner, ::render)
                    gettingAuthToken()
                }
            }
        }

    }


    private fun render(viewState: LoginViewState) {
        when (viewState) {
            Loading -> binding.progressBar.show()

            is Error ->  {
                binding.progressBar.hide()
                binding.tvError.show()
                binding.tvError.text =  viewState.message
            }

            is TokenLoaded -> {
                binding.progressBar.hide()
                viewModel.saveAccessToken(viewState.token)
                navigator.navigateToHome()
            }
        }
    }


    fun enableLoginIfReady() {
        if(!binding.etUserName.text.isNullOrEmpty() && ! binding.etPassword.text.isNullOrEmpty()){
            binding.btnLogin.isEnabled = true
        }
    }


    private val handler = Handler()

    private val finishLoading: Runnable = Runnable {
        if (authenticationManager.isAuthenticated()) {
            binding.progressBar.show()

            if(authenticationManager.getExpiryDate() > Date().time){
                navigator.navigateToHome()
            } else{
                viewModel.refreshToken()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(finishLoading, 10L)
    }

    override fun onStop() {
        handler.removeCallbacks(finishLoading)
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}