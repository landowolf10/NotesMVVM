package com.example.notesmvvm.ui.user.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notesmvvm.data.model.user.login.LoginRequest
import com.example.notesmvvm.databinding.ActivityLoginBinding
import com.example.notesmvvm.ui.user.viewmodel.UserActivityViewModel
import com.example.notesmvvm.ui.note.view.NotesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        clickLoginBtn()
    }

    private fun initViewModel()
    {
        viewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]

        viewModel.getLoginLiveData().observe(this) {
            if (it == null)
            {
                Toast.makeText(this, "Error login in", Toast.LENGTH_LONG).show()
                return@observe
            }

            val intent = Intent(this, NotesActivity::class.java)

            //Passes the logged user id to NotesActivity
            intent.putExtra("user_id", it.loginData.userID)
            startActivity(intent)
        }
    }

    private fun clickLoginBtn()
    {
        binding.btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login()
    {
        val user = LoginRequest(
            binding.etMail.text.toString(),
            binding.etPassword.text.toString()
        )

        viewModel.login(user)
    }
}