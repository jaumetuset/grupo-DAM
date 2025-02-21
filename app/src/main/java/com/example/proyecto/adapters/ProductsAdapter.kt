package com.example.proyecto.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.api.Product
import com.example.proyecto.api.ProductEntity
import com.example.proyecto.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class ProductsAdapter (private var products: List<Product>?, private val listener: OnClickListener):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemProductBinding.bind(view)
        fun setListener(product: Product){
            binding.root.setOnClickListener {
                listener.onClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products!!.size
    }

    fun updateData(newProducts: List<Product>) {
        products = newProducts
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products?.get(position) as Product

        with(holder) {
            setListener(product)
            Picasso.get()
                .load("http://40.89.147.152:8080/MyApp/uploads/" + product.id)
                .into(binding.imgProduct)

            binding.txtPrice.text = product.price.toString()
            binding.txtName.text = product.name
        }
    }


}