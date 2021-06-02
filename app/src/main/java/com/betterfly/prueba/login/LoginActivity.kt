package com.betterfly.prueba.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.betterfly.prueba.PruebaApplication
import com.betterfly.prueba.R
import com.betterfly.prueba.databinding.ActivityLoginBinding
import com.betterfly.prueba.databinding.ActivityMainBinding
import com.betterfly.prueba.detail.viewModel.DetailViewModel
import com.betterfly.prueba.login.viewModel.LoginViewModel
import com.betterfly.prueba.main.MainActivity
import com.betterfly.prueba.main.viewModel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var mLoginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        val currentUser = PruebaApplication.auth.currentUser
        updateUI(currentUser)
    }

    private fun setupViewModel() {
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mLoginViewModel.isLogin().observe(this, { login ->
            if(login){
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }else{
                MaterialAlertDialogBuilder(this)
                        .setTitle(resources.getString(R.string.str_error_login))
                        .setMessage(resources.getString(R.string.str_message_login))
                        .setCancelable(false)
                        .setNeutralButton(resources.getString(R.string.str_btn_cancelar)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton(resources.getString(R.string.str_btn_ok)) { dialog, _ ->
                            dialog.dismiss()
                            mLoginViewModel.create(mBinding.email.text.toString().trim(), mBinding.password.text.toString().trim())
                        }
                        .show()
            }
        })
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }else{
            mBinding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(mBinding.root)
            mBinding.btnLogin.setOnClickListener { login() }

            setupViewModel()
        }
    }

    private fun login() {
        if (!validate()) {
            MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.str_error_validacion))
                    .setMessage(resources.getString(R.string.str_message_validacion))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.str_btn_ok)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            return
        }

        mLoginViewModel.login(mBinding.email.text.toString().trim(), mBinding.password.text.toString().trim())
    }

    fun validate(): Boolean {
        var valid = true
        val txtEmail = findViewById<TextView>(R.id.email)
        val txtPassword = findViewById<TextView>(R.id.password)

        if (txtEmail.text.toString().isEmpty()) {
            txtEmail.error = "Ingrese su usuario."
            valid = false
        } else {
            txtEmail.error = null
        }

        if (txtPassword.text.toString().isEmpty()) {
            txtPassword.error = "Ingrese una contraseña."
            valid = false
        } else {
            txtPassword.error = null
        }
        return valid
    }
}