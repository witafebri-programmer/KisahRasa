package com.dicoding.kisahrasa.auth

import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dicoding.kisahrasa.core.data.local.preferences.AppPreferences
import com.dicoding.kisahrasa.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    
    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        setupAction()
    }

    private fun setupAction() {
        binding.btnCreateAccount.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            if (validateInput(name, email, password)) {
                showLoading(true)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = userProfileChangeRequest {
                                displayName = name
                            }
                            
                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { updateTask ->
                                    lifecycleScope.launch {
                                        appPreferences.saveLoginSession(true)
                                        showLoading(false)
                                        if (updateTask.isSuccessful) {
                                            Toast.makeText(this@RegisterActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                                            finish()
                                        } else {
                                            Toast.makeText(this@RegisterActivity, "Failed to set name: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                    }
                                }
                        } else {
                            showLoading(false)
                            Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        var isValid = true

        if (name.isEmpty()) {
            binding.tilRegisterName.error = "Name is required"
            isValid = false
        } else {
            binding.tilRegisterName.error = null
        }

        if (email.isEmpty()) {
            binding.tilRegisterEmail.error = "Email is required"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilRegisterEmail.error = "Invalid email format"
            isValid = false
        } else {
            binding.tilRegisterEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilRegisterPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.tilRegisterPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.tilRegisterPassword.error = null
        }

        return isValid
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnCreateAccount.isEnabled = !isLoading
    }
}
