package com.example.Zapasy.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Zapasy.Models.Categoria

@Dao
interface CategoriaDao {

    @Insert
    fun insert(categoria: Categoria)

    @Update
    fun update(categoria: Categoria)

    @Delete
    fun delete(categoria: Categoria)

    @Query("SELECT * FROM categoria" )
    fun getAll(): LiveData<List<Categoria>>

}