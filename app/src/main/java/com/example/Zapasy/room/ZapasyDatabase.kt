package com.example.Zapasy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ZapasyDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

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