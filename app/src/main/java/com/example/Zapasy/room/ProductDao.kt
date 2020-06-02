package com.example.Zapasy.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * FROM product" )
    fun getAll(): LiveData<List<Product>>

    @Query ("SELECT * FROM Product WHERE id = :id")
    fun getOne(id: Int): List<Product>

    @Query ("UPDATE product SET existing = :existing WHERE id = :id")
    fun updateExisting(id: Int, existing: Int)

    @Query (value = "SELECT * FROM product")
    fun getAllNoLiveData(): List<Product>

    @Query(value = "SELECT * FROM product WHERE idMarca = :idMarca")
    fun getByMarca(idMarca: Int): LiveData<List<Product>>

}