package com.example.proyecto.database

import android.app.Application
import androidx.room.Room

class ProductApplication : Application() {

    companion object {
        lateinit var database: ProductDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, ProductDatabase::class.java, "ProductDatabase").fallbackToDestructiveMigration().build()

    }

}
