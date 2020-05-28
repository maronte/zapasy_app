package com.example.Zapasy.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM product" )
    fun getAll(): LiveData<List<Product>>

    @Query ("SELECT * FROM Product WHERE id = :id")
    fun getOne(id: Int): LiveData<List<Product>>

    @Query ("UPDATE product SET existing = :existing WHERE id = :id")
    fun updateExisting(id: Int, existing: Int)

}