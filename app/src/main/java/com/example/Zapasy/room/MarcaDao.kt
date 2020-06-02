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

    @Query("SELECT * FROM marca")
    fun getMarcasForList(): MutableList<Marca>

    @Query (value = "SELECT * FROM marca")
    fun getAllNoLiveData(): List<Marca>

    @Query(value = "SELECT * FROM marca WHERE id = :id")
    fun getOne(id: Int): List<Marca>

}