package com.example.Zapasy.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Zapasy.Models.Categoria
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CategoriasRepository(val application: Application) {
    val categoriaDao: CategoriaDao? = ZapasyDatabase.getInstance(application)?.categoriaDao()

    fun insert(categoria: Categoria){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (categoriaDao!= null){
                categoriaDao.insert(categoria)
            }
        }
    }

    fun getAll(): LiveData<List<Categoria>>{
        return categoriaDao?.getAll()?: MutableLiveData()
    }

    fun delete(categoria: Categoria){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (categoriaDao!= null){
                categoriaDao.delete(categoria)
            }
        }
    }
}