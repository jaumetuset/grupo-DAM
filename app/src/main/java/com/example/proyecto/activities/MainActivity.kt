package com.example.proyecto.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.proyecto.R
import com.example.proyecto.api.Product
import com.example.proyecto.api.User
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.fragments.FavoritesFragment
import com.example.proyecto.fragments.HomeFragment
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.fragments.ProfileFragment
import com.example.proyecto.fragments.SellFragment
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.Locale

class MainActivity : AppCompatActivity(), ProductsListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        setPreferences()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        user = intent.getSerializableExtra("User") as User

        setContentView(binding.root)

        val fragHome = HomeFragment.newInstance(user).apply {
            setProductsListener(this@MainActivity)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragments, fragHome)
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked = true

            when (it.itemId) {
                R.id.nav_home -> {
                    val fragHome = HomeFragment.newInstance(user).apply {
                        setProductsListener(this@MainActivity)
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments, fragHome)
                        .commit()

                    binding.title.text = getString(R.string.home)
                }

                R.id.nav_fav -> {
                    val frgFav: FavoritesFragment =
                        FavoritesFragment.newInstance(user)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments, frgFav)
                        .commit()

                    binding.title.text = getString(R.string.favorites)
                }

                R.id.nav_sell -> {
                    val frgSell: SellFragment =
                        SellFragment.newInstance(user)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments, frgSell)
                        .commit()

                    binding.title.text = getString(R.string.sell_a_product)
                }

                R.id.nav_profile -> {
                    val frgProfile: ProfileFragment =
                        ProfileFragment.newInstance(user)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragments, frgProfile)
                        .commit()

                    binding.title.text = getString(R.string.my_profile)
                }
            }
            false
        }

        drawerLayout = binding.main

        val navigationView: NavigationView = binding.navMenu
        navigationView.setCheckedItem(R.id.nav_home)

        val headerView = navigationView.getHeaderView(0)
        val txtUserName = headerView.findViewById<TextView>(R.id.userName)
        val txtUserEmail = headerView.findViewById<TextView>(R.id.userEmail)

        txtUserName.text = user.name
        txtUserEmail.text = user.email



        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_cuenta -> {
                    val intent = Intent(this, ModifyUserActivity::class.java)
                    intent.putExtra("User", user)
                    startActivity(intent)
                }
                R.id.nav_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    intent.putExtra("User", user)
                    startActivity(intent)
                }
                R.id.nav_premium -> {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_premium, null)

                    AlertDialog.Builder(this)
                        .setTitle("Quieres hacerte premium por 5€?")
                        .setView(dialogView)
                        .setPositiveButton("Si") { _, _ ->
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    RetrofitInstance.api.odoo(user)
                                    runOnUiThread {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Gracias por suscribirte!",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                } catch (e: HttpException) {
                                    val errorBody = e.response()?.errorBody()?.string()
                                    Log.e("Odoo", "Error HTTP ${e.code()}: $errorBody")

                                } catch (e: Exception) {
                                }

                                factureDialog()
                            }
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                }
                R.id.nav_log_out -> {
                    val intent = Intent(this, LogInActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val iconDrawer = binding.navDrawer
        iconDrawer.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
    }

    override fun onProductSelected(p: Product) {
        if (p != null) {
            val intent = Intent(this, ProductActivity::class.java)
            intent.putExtra("Product", p)
            startActivity(intent)
        }
    }

    private fun setPreferences() {
        val preferences = getSharedPreferences("settings", MODE_PRIVATE)

        val language = preferences.getString("language", "es") ?: "es"
        changeLanguage(this, language)

        val font = preferences.getString("font", "default") ?: "default"
        applyFont(font)
    }

    private fun changeLanguage(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        val preferences = context.getSharedPreferences("settings", MODE_PRIVATE)
        preferences.edit().putString("language", language).apply()
    }

    private fun factureDialog() {
        runOnUiThread {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("¿Quieres ver la factura?")
                .setPositiveButton("Sí") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val facture = RetrofitInstance.api.odooFacture()
                            val url = "http://40.89.147.152:8069/report/html/account.report_invoice/${facture}"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            startActivity(intent)

                        } catch (e: HttpException) {
                            val errorBody = e.response()?.errorBody()?.string()
                            Log.e("Odoo", "Error HTTP ${e.code()}: $errorBody")

                        } catch (e: Exception) {
                            factureDialog()
                        }
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }


    private fun applyFont(font: String) {
        when (font) {
            "roboto" -> setTheme(R.style.ProjectTheme_Roboto)
            "montserrat" -> setTheme(R.style.ProjectTheme_Montserrat)
            "space_mono" -> setTheme(R.style.ProjectTheme_SpaceMono)
        }
    }
}