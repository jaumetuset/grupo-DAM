package com.example.proyecto.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyecto.api.ProductEntity
import com.example.proyecto.dao.ProductDao

@Database(entities = [ProductEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}