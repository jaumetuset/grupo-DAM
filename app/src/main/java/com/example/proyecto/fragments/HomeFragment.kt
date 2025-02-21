package com.example.proyecto.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.R
import com.example.proyecto.activities.CategoriesActivity
import com.example.proyecto.adapters.OnClickListener
import com.example.proyecto.adapters.ProductsAdapter
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.Product
import com.example.proyecto.api.ProductEntity
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.database.ProductApplication
import com.example.proyecto.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class HomeFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var listener: ProductsListener
    private lateinit var user: User
    private lateinit var searchView: SearchView
    private var queryName: String? = null
    private var products: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user = it.getSerializable("User") as User
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val iconCat = binding.icon

        iconCat.setOnClickListener {
            val intent = Intent(context, CategoriesActivity::class.java)
            intent.putExtra("User", user)
            startActivity(intent)
        }

        gridLayoutManager = GridLayoutManager(context,2)
        productsAdapter = ProductsAdapter(products, this)

        binding.recyclerProducts.apply {
            layoutManager = gridLayoutManager
            adapter = productsAdapter
        }

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

                searchName()

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    queryName = null
                    searchName()
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            queryName = null

            searchName()

            false
        }

        loadProducts()

        return binding.root
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allProducts = RetrofitInstance.api.listProducts()

                val filteredProducts = allProducts.filter { it.usuario.id != user.id }

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

    private fun searchName() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                products = RetrofitInstance.api.listProductsCategory(
                    nomCategoria = null,
                    name = queryName,
                    price = null
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

    override fun onResume() {
        super.onResume()
        loadProducts()
    }

    fun setProductsListener(listener: ProductsListener) {
        this.listener = listener
    }

    override fun onClick(obj: Any) {
        val product: Product = obj as Product

        if (listener != null) {
            listener.onProductSelected(product)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(u: User) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("User", u)
                }
            }
    }
}
