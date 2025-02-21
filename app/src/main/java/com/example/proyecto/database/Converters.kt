package com.example.proyecto.database

import androidx.room.TypeConverter
import com.example.proyecto.api.CategoryEntity
import com.example.proyecto.api.UserEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromCategory(category: CategoryEntity): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategory(data: String): CategoryEntity {
        val type = object : TypeToken<CategoryEntity>() {}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun fromUser(user: UserEntity): String {
        return gson.toJson(user)
    }

    @TypeConverter
    fun toUser(data: String): UserEntity {
        val type = object : TypeToken<UserEntity>() {}.type
        return gson.fromJson(data, type)
    }
}