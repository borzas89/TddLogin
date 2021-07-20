package example.com.tddlogin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import example.com.tddlogin.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    lateinit var loginRequest: LoginRequest


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginRequest = LoginRequest()

        binding.etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLoginIfReady()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLoginIfReady()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


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
                loginRequest.send(
                    binding.etUserName.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }


    }

    fun enableLoginIfReady() {
        if(!binding.etUserName.text.isNullOrEmpty() && ! binding.etPassword.text.isNullOrEmpty()){
            binding.btnLogin.isEnabled = true
        }
    }
}