package com.example.proyecto.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class Product(
    val id:Int,
    val name:String,
    val description:String,
    val categoria:Category,
    val price:Float,
    val usuario:User,
    val antiquity:String,
    //val etiqueta:List<String>,
    //val foto:Photo?
) : Serializable

data class Category(
    val id:Int,
    val name:String,
    val description:String
): Serializable {
    override fun toString(): String {
        return name
    }
}

data class Photo(
    val id:Int,
    val url:String
): Serializable


@Entity(tableName = "products")
class ProductEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var name:String?,
    @ColumnInfo val description:String,
    @ColumnInfo val category:CategoryEntity,
    @ColumnInfo val price:Float,
    @ColumnInfo val user:UserEntity,
    @ColumnInfo val antiquity:String,
    @ColumnInfo val serverId: Int

): Serializable

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name:String,
    @ColumnInfo val description:String
): Serializable

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val name:String,
    @ColumnInfo val email:String,
    @ColumnInfo val password:String,
    @ColumnInfo val poblacion:String
) : Serializable