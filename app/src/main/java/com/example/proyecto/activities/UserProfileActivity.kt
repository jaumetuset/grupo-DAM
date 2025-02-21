package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.R
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.Product
import com.example.proyecto.api.User
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.databinding.ActivityUserProfileBinding
import com.example.proyecto.fragments.FavoritesFragment
import com.example.proyecto.fragments.HomeFragment
import com.example.proyecto.fragments.ProfileFragment

class UserProfileActivity : AppCompatActivity(), ProductsListener {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        user = intent.getSerializableExtra("User") as User

        setContentView(binding.root)


        val frgProfile = ProfileFragment.newInstance(user).apply {
            setProductsListener(this@UserProfileActivity)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentProfile, frgProfile)
            .commit()


        val iconBack = binding.icnBack
        iconBack.setOnClickListener {
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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
}