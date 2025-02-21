package com.example.proyecto.adapters

import com.example.proyecto.api.Product

interface ProductsListener {
    fun onProductSelected(p: Product)
}