package com.dicoding.kisahrasa.auth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.kisahrasa.MainActivity
import com.dicoding.kisahrasa.core.data.local.preferences.AppPreferences
import com.dicoding.kisahrasa.databinding.ActivityAuthBinding
import com.scottyab.rootbeer.RootBeer
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var auth: FirebaseAuth
    
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val rootBeer = RootBeer(this)
        if (rootBeer.isRooted) {
            Toast.makeText(this, "Rooted device detected! Closing app for security", Toast.LENGTH_LONG).show()
            finishAffinity()
            return
        }
        
        auth = FirebaseAuth.getInstance()
        
        lifecycleScope.launch {
            val isLoggedIn = appPreferences.getLoginSession().first()
            if (isLoggedIn) {
                navigateToMain()
                return@launch
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString().trim()

                if (validateInput(email, password)) {
                    showLoading(true)
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                lifecycleScope.launch {
                                    appPreferences.saveLoginSession(true)
                                    showLoading(false)
                                    navigateToMain()
                                }
                            } else {
                                showLoading(false)
                                handleAuthError(task.exception)
                            }
                        }
                }
            }

            tvRegisterLink.setOnClickListener {
                val intent = Intent(this@AuthActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true
        binding.apply {
            if (email.isEmpty()) {
                tilEmail.error = "Email is required"
                isValid = false
            } else {
                tilEmail.error = null
            }
            
            if (password.isEmpty()) {
                tilPassword.error = "Password is required"
                isValid = false
            } else {
                tilPassword.error = null
            }
        }
        return isValid
    }

    private fun handleAuthError(exception: Exception?) {
        val message = when (exception) {
            is FirebaseAuthInvalidUserException -> "No account found with this email."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email or password."
            else -> exception?.message ?: "Authentication failed."
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            btnLogin.isEnabled = !isLoading
            tvRegisterLink.isEnabled = !isLoading
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
