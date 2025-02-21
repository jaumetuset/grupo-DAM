package com.example.proyecto.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiGrupoAService {

    @GET("usuarios/login")
    suspend fun loginUser(
        @Query("email") email: String,
        @Query("password") password: String
    ): User

    @POST("usuarios")
    suspend fun registerUser(@Body user: User): User

    @POST("productos")
    suspend fun addProduct(@Body product: Product): Product

    @GET("productos")
    suspend fun listProducts(): List<Product>

    @GET("categorias")
    suspend fun listCategories(): List<Category>

    @GET("odoo")
    suspend fun odooFacture(): String

    @POST("odoo/create")
    suspend fun odoo( @Body user: User): Response<String>

    @DELETE("productos/{id}")
    suspend fun deleteProducto(@Path("id") userId: Int): Response<Void>

    @GET("productos/filtrar")
    suspend fun listProductsCategory(
        @Query("nomCategoria") nomCategoria: String?,
        @Query("name") name: String?,
        @Query("price") price: Double?
    ): List<Product>

    @PUT("usuarios")
    suspend fun modifyUser(@Body user: User): User

    @Multipart
    @POST("uploads/imagen/{articuloId}")
    fun subirImagen(
        @Path("articuloId") articuloId: Int,
        @Part file: MultipartBody.Part
    ): Call<Map<String, String>>

    @GET("uploads/{id}")
    fun cargarImagen(@Path("id") id: Int): String

}