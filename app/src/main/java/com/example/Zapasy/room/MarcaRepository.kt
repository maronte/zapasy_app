package com.example.Zapasy.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Zapasy.Models.Marca
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MarcaRepository(application: Application) {

    private val marcaDao: MarcaDao? = ZapasyDatabase.getInstance(application)?.marcaDao()

    fun insert(marca: Marca){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (marcaDao!= null){
                marcaDao.insert(marca)
            }
        }
    }

    fun getAll(): LiveData<List<Marca>> {
        return marcaDao?.getAll() ?: MutableLiveData<List<Marca>>()
    }

    fun delete(marca: Marca){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (marcaDao!= null){
                marcaDao.delete(marca)
            }
        }
    }

}