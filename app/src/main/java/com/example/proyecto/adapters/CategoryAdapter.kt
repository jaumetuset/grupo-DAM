package com.example.proyecto.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.api.Category
import com.example.proyecto.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var category: List<Category>,
    private val listener: OnClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var context: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemCategoryBinding.bind(view)
        fun setListener(category: Category) {
            binding.root.setOnClickListener {
                listener.onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = category.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = category[position]

        with(holder) {
            setListener(category)
            binding.txtNameCat.text = category.name
        }
    }

    fun updateCategories(newCategories: List<Category>) {
        category = newCategories
        notifyDataSetChanged()
    }
}