package com.example.proyecto.api

import java.io.Serializable

/*
data class UsuarioResponse(
    val count:Int,
    val results:List<Usuario>
)
*/

data class User(
    val id:Int,
    var name:String,
    val email:String,
    var password:String,
    var poblacion:String
) : Serializable