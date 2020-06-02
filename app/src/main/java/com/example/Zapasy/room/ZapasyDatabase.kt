package com.example.Zapasy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.Models.Product

@Database(entities = [Product::class, Marca::class, Categoria::class], version = 1, exportSchema = false)
abstract class ZapasyDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun marcaDao(): MarcaDao
    abstract fun categoriaDao(): CategoriaDao

    companion object{

        private const val DATABASE_NAME = "zapasy_db"

        @Volatile
        private var INSTANCE: ZapasyDatabase? = null

        fun getInstance(context: Context): ZapasyDatabase? {
            INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                ZapasyDatabase::class.java, DATABASE_NAME).fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }
}