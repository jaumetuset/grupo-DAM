
package com.example.proyecto.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyecto.R
import com.example.proyecto.adapters.OnClickListener
import com.example.proyecto.adapters.ProductsAdapter
import com.example.proyecto.adapters.ProductsListener
import com.example.proyecto.api.Product
import com.example.proyecto.api.RetrofitInstance
import com.example.proyecto.api.User
import com.example.proyecto.databinding.FragmentHomeBinding
import com.example.proyecto.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProfileFragment : Fragment(),OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: User
    private lateinit var productsAdapter: ProductsAdapter
    private var listener: ProductsListener? = null
    private lateinit var gridLayoutManager: GridLayoutManager
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
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.userName.setText(user.name)
        binding.userEmail.setText(user.email)
        binding.txtProducts.setText("${user.name} Products")

        gridLayoutManager = GridLayoutManager(context,2)
        productsAdapter = ProductsAdapter(products, this)

        binding.recyclerUserProducts.apply {
            layoutManager = gridLayoutManager
            adapter = productsAdapter
        }

        loadProducts()


        return binding.root
    }

    private fun loadProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allProducts = RetrofitInstance.api.listProducts()

                val filteredProducts = allProducts.filter { it.usuario.id == user.id }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user?.let {
            loadFragment(MapsFragment.newInstance(it))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mapsContainer, fragment)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(u: User) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("User", u)
                }
            }
    }

    fun setProductsListener(listener: ProductsListener) {
        this.listener = listener
    }

    override fun onClick(obj: Any) {
        val product: Product = obj as Product

        listener?.onProductSelected(product)
    }
}
