package com.example.proyecto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.R
import com.example.proyecto.api.*
import com.example.proyecto.database.ProductApplication
import com.example.proyecto.databinding.ActivityProductBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityProductBinding.inflate(layoutInflater)
        product = intent.getSerializableExtra("Product") as Product

        setContentView(binding.root)

        Log.e("Product", "Imagen URL: http://40.89.147.152:8080/MyApp/uploads/" + product.id)

        Picasso.get()
            .load("http://40.89.147.152:8080/MyApp/uploads/" + product.id)
            .into(binding.imgProd)

        binding.nameUser.text = product.usuario.name
        binding.priceProd.text = product.price.toString()
        binding.nameProd.text = product.name
        binding.textProd.text = product.description

        binding.btnBuy.setOnClickListener {
            AlertDialog.Builder(this@ProductActivity)
                .setTitle("Quieres comprar el producto ${product.name} por ${product.price}?")
                .setPositiveButton("Si") { _, _ ->
                    dialogRating {
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                RetrofitInstance.api.deleteProducto(product.id)

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@ProductActivity,
                                        "Se ha comprado el producto: ${product.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    finish()
                                }
                            } catch (e: HttpException) {
                                val errorBody = e.response()?.errorBody()?.string()
                                Log.e("Producto", "Error HTTP ${e.code()}: $errorBody")
                            }
                        }
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        val iconHeart = binding.icnHeart
        iconHeart.setOnClickListener {
            iconHeart.setImageResource(R.drawable.ic_heart)
            Thread {
                val newUser = UserEntity(
                    name = product.usuario.name,
                    email = product.usuario.email,
                    password = product.usuario.password,
                    poblacion = product.usuario.poblacion
                )

                val newCategory = CategoryEntity(
                    id = product.categoria.id,
                    name = product.categoria.name,
                    description = product.categoria.description
                )

                val newProduct = ProductEntity(
                    name = product.name,
                    description = product.description,
                    category = newCategory,
                    price = product.price,
                    user = newUser,
                    antiquity = product.antiquity,
                    serverId = product.id
                )
                ProductApplication.database.productDao().addProduct(newProduct)
            }.start()
        }

        val iconBack = binding.icnBack
        iconBack.setOnClickListener {
            finish()
        }

        val iconProfile = binding.imgUser
        iconProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            intent.putExtra("User", product.usuario)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun dialogRating(onRatingSubmitted: () -> Unit) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rating, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        AlertDialog.Builder(this@ProductActivity)
            .setTitle("Valora el producto.")
            .setView(dialogView)
            .setPositiveButton("Enviar") { _, _ ->
                val rating = ratingBar.rating
                Toast.makeText(this@ProductActivity, "Valoración de $rating estrellas", Toast.LENGTH_SHORT).show()
                onRatingSubmitted()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                onRatingSubmitted()
            }
            .show()
    }
}