package com.example.proyecto.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.proyecto.R
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.databinding.ActivityLogInBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Locale

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAccept.setOnClickListener {
            val txtEmail = binding.txtEmail.text.toString()
            val txtPassword = binding.txtPassword.text.toString()

            var correct = true

            if (txtEmail.isEmpty()) {
                binding.txtEmail.error = "El email no puede estar vacio"
                correct = false
            }

            if (txtPassword.isEmpty()) {
                binding.txtPassword.error = "La contraseña no puede estar vacia"
                correct = false
            }

            if (correct) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val user = RetrofitInstance.api.loginUser(txtEmail, txtPassword)
                        runOnUiThread {
                            Toast.makeText(
                                this@LogInActivity,
                                "Bienvenido, ${user.name}  ${user.email}",
                                Toast.LENGTH_SHORT
                            ).show()

                            val intent = Intent(this@LogInActivity, MainActivity::class.java)
                            intent.putExtra("User", user)
                            startActivity(intent)
                        }
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string()
                        Log.e("Login", "Error HTTP ${e.code()}: $errorBody")

                        runOnUiThread {
                            Toast.makeText(
                                this@LogInActivity,
                                "Usuario no encontrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this@LogInActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase)
        val language = sharedPreferences.getString("language", "es") ?: "es"

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)

        val context = newBase.createConfigurationContext(config)

        val font = sharedPreferences.getString("font", "roboto") ?: "roboto"
        when (font) {
            "roboto" -> context.setTheme(R.style.ProjectTheme_Roboto)
            "montserrat" -> context.setTheme(R.style.ProjectTheme_Montserrat)
            "space_mono" -> context.setTheme(R.style.ProjectTheme_SpaceMono)
        }

        super.attachBaseContext(context)
    }


}