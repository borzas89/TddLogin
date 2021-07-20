package example.com.tddlogin


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    lateinit var loginRequest: LoginRequest;
    lateinit var etUserName: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginRequest = LoginRequest()

        etUserName = findViewById<View>(R.id.etUserName) as TextInputEditText
        etPassword = findViewById<View>(R.id.etPassword) as TextInputEditText
        btnLogin = findViewById<View>(R.id.btnLogin) as Button

        etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLoginIfReady()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                enableLoginIfReady()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        btnLogin.setOnClickListener {
            val isUserEmpty = (""
                    == etUserName.text.toString())
            val isPasswordEmpty = "" == etPassword.text
                .toString()
            if (isUserEmpty) {
                etUserName.error = getString(R.string.error_field_empty)
            }
            if (isPasswordEmpty) {
                etPassword.error = getString(R.string.error_field_empty)
            }
            if (!isUserEmpty && !isPasswordEmpty) {
                loginRequest.send(
                    etUserName.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }


    fun enableLoginIfReady() {
        if(!etUserName.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()){
            btnLogin.isEnabled = true
        }
    }

}