package com.example.mbcapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mbcapp.databinding.ActivityLogInBinding
import com.example.mbcapp.viewmodels.LogInState
import com.example.mbcapp.viewmodels.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLogInBinding
    private val viewModel: LogInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()

        binding.logInButton.setOnClickListener {
            val email = binding.emailTv.text.toString()
            val password = binding.passwordTv.text.toString()
            viewModel.logInUser(email, password)
        }


    }

    private fun observeViewModel() {
        viewModel.loginState.observe(this) {
            when (it) {
                is LogInState.Success -> {
                    val intent = Intent(this, SurveysActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    Toast.makeText(this, "otro error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}