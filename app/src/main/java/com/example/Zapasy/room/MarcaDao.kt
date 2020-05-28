package com.example.Zapasy.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Zapasy.Models.Marca

@Dao
interface MarcaDao {

    @Insert
    fun insert(marca: Marca)

    @Update
    fun update(marca: Marca)

    @Delete
    fun delete(marca: Marca)

    @Query("SELECT * FROM marca" )
    fun getAll(): LiveData<List<Marca>>
}