package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.R
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.databinding.ActivityModifyUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ModifyUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyUserBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityModifyUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("User") as User

        val iconBack = binding.icnBack
        iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("User", user)
            startActivity(intent)
        }

        //binding.txtPassword.setText(user.password)
        binding.txtName.setText(user.name)
        binding.txtLocation.setText(user.poblacion)


        binding.btnUpdate.setOnClickListener {
            val txtPassword = binding.txtPassword.text.toString()
            val txtName = binding.txtName.text.toString()
            val txtLocation = binding.txtLocation.text.toString()

            var correct = true

            if (txtName.isEmpty()) {
                binding.txtName.error = "El nombre no puede estar vacio"
                correct = false
            }

            if (txtLocation.isEmpty()) {
                binding.txtLocation.error = "El nombre no puede estar vacio"
                correct = false
            }

            if (correct) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        if (txtPassword.isNotEmpty()) {
                            user.password = txtPassword
                        }

                        user.name = txtName
                        user.poblacion = txtLocation

                        val userMod = RetrofitInstance.api.modifyUser(user)

                        runOnUiThread {
                            Toast.makeText(
                                this@ModifyUserActivity,
                                "Se han actualizado los valores",
                                Toast.LENGTH_SHORT
                            ).show()

                            if (txtPassword.isEmpty()) {
                                val intent = Intent(this@ModifyUserActivity, MainActivity::class.java)
                                intent.putExtra("User", userMod)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this@ModifyUserActivity, LogInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }

                        }
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string()
                        Log.e("Registro", "Error HTTP ${e.code()}: $errorBody")

                        runOnUiThread {
                            Toast.makeText(
                                this@ModifyUserActivity,
                                "Ha habido un error en las autenticaciones",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }


            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}