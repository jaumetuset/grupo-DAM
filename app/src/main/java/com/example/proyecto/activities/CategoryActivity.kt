package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.R
import com.example.proyecto.adapters.OnClickListener
import com.example.proyecto.adapters.ProductsAdapter
import com.example.proyecto.api.Category
import com.example.proyecto.api.Product
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.databinding.ActivityCategoryBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class CategoryActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var category: Category
    private lateinit var user: User
    private lateinit var searchView: SearchView
    private var products: List<Product> = listOf()
    private var queryName: String? = null
    private var queryPrice: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCategoryBinding.inflate(layoutInflater)

        category = intent.getSerializableExtra("Category") as Category
        user = intent.getSerializableExtra("User") as User

        setContentView(binding.root)

        searchView = binding.searchBar
        searchView.clearFocus()

        searchView.setOnClickListener {
            searchView.isIconified = false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    queryName = query
                } else {
                    queryName = null
                }

                searchView.clearFocus()

                applyFilters()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    queryName = null
                    applyFilters()
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            queryName = null

            applyFilters()

            false
        }

        val iconBack = binding.icnBack

        iconBack.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            intent.putExtra("User", user)
            startActivity(intent)
        }

        gridLayoutManager = GridLayoutManager(this,2)
        productsAdapter = ProductsAdapter(products, this)

        binding.recyclerProducts.apply {
            layoutManager = gridLayoutManager
            adapter = productsAdapter
        }

        loadProducts()

        val chipPrice = findViewById<Chip>(R.id.chipPrice)

        chipPrice.setOnClickListener {
            if (chipPrice.isChecked) {
                showPriceDialog(chipPrice)
            } else {
                chipPrice.text = "Precio"
                queryPrice = null
                applyFilters()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showPriceDialog(chip: Chip) {
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_price_filter, null)
        val editText = dialogView.findViewById<EditText>(R.id.editTextPrice)

        AlertDialog.Builder(this)
            .setTitle("Filtrar por precio")
            .setView(dialogView)
            .setPositiveButton("Aplicar") { _, _ ->
                val price = editText.text.toString()
                if (price.isNotEmpty()) {
                    chip.text = "Precio: ${price}€"

                    queryPrice = price.toDouble()

                    applyFilters()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onClick(obj: Any) {
        val product: Product = obj as Product

        val intent = Intent(this, ProductActivity::class.java)
        intent.putExtra("Product", product)
        startActivity(intent)
    }

    private fun applyFilters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                products = RetrofitInstance.api.listProductsCategory(
                    nomCategoria = category.name,
                    name = queryName,
                    price = queryPrice
                )

                val filteredProducts = products.filter { it.usuario.id != user.id }

                withContext(Dispatchers.Main) {
                    products = filteredProducts
                    productsAdapter.updateData(products)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("CategoryActivity", "Error HTTP ${e.code()}: $errorBody")
            }
        }
    }


    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                products = RetrofitInstance.api.listProductsCategory(
                    category.name,
                    name = null,
                    price = null
                )

                val filteredProducts = products.filter { it.usuario.id != user.id }

                withContext(Dispatchers.Main) {
                    products = filteredProducts
                    productsAdapter.updateData(products)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("Home", "Error HTTP ${e.code()}: $errorBody")
            }
        }
    }

}