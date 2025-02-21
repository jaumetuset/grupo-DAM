package com.example.proyecto.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.R
import com.example.proyecto.adapters.FavProductAdapter
import com.example.proyecto.adapters.OnClickListener
import com.example.proyecto.adapters.ProductsAdapter
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.Product
import com.example.proyecto.api.ProductEntity
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.database.ProductApplication
import com.example.proyecto.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FavoritesFragment : Fragment(), OnClickListener {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var productsAdapter: FavProductAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var user: User
    private var products: List<ProductEntity> = listOf()

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

        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        gridLayoutManager = GridLayoutManager(context, 2)
        productsAdapter = FavProductAdapter(products, this)

        binding.recyclerProductsFav.apply {
            layoutManager = gridLayoutManager
            adapter = productsAdapter
        }

        loadProducts()

        return binding.root
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val productosRoom = ProductApplication.database.productDao().getAllProduct()

                withContext(Dispatchers.Main) {
                    products = productosRoom
                    productsAdapter.updateData(products)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("Favoritos", "Error HTTP ${e.code()}: $errorBody")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(u: User) =
            FavoritesFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("User", u)
                }
            }
    }

    override fun onClick(obj: Any) {
        var product = obj as ProductEntity

        CoroutineScope(Dispatchers.IO).launch {
            try {
                ProductApplication.database.productDao().deleteProduct(product)

                val productosRoom = ProductApplication.database.productDao().getAllProduct()

                withContext(Dispatchers.Main) {
                    products = productosRoom
                    productsAdapter.updateData(products)
                }
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("Favoritos", "Error HTTP ${e.code()}: $errorBody")
            }
        }
    }
}
