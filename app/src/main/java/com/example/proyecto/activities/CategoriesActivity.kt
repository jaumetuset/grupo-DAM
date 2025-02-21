package com.example.proyecto.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.R
import com.example.proyecto.adapters.CategoryAdapter
import com.example.proyecto.adapters.OnClickListener
import com.example.proyecto.api.Product
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.databinding.ActivityCategoriesBinding
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CategoriesActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        user = intent.getSerializableExtra("User") as User

        setContentView(binding.root)

        val iconBack = binding.icnBack

        iconBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("User", user)
            startActivity(intent)
        }

        gridLayoutManager = GridLayoutManager(this, 3)

        categoryAdapter = CategoryAdapter(listOf(), this)

        binding.recyclerCategories.apply {
            layoutManager = gridLayoutManager
            adapter = categoryAdapter
        }

        loadCategories()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = RetrofitInstance.api.listCategories()
                for (category in categories) {
                    Log.e("Category", "Category: ${category.name}")
                }

                launch(Dispatchers.Main) {
                    categoryAdapter.updateCategories(categories)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("Category", "Error HTTP ${e.code()}: $errorBody")
            }
        }
    }

    override fun onClick(obj: Any) {
        val category: Category = obj as Category

        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("Category", category)
        intent.putExtra("User", user)
        startActivity(intent)
    }

}