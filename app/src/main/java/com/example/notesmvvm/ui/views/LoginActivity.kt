package com.example.notesmvvm.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.data.remote.source.user.UserRemoteDataSource
import com.example.notesmvvm.databinding.ActivityLoginBinding

class  LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userAPI = UserRemoteDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLoginBtn()
    }

    private fun clickLoginBtn()
    {
        binding.btnLogin.setOnClickListener {
            val user = LoginRequest(
                binding.etMail.text.toString(),
                binding.etPassword.text.toString()
            )

            userAPI.login(user, this)
        }
    }
}