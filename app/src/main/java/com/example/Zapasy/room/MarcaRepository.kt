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

    fun update(marca: Marca){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (marcaDao!= null){
                marcaDao.update(marca)
            }
        }
    }

    private class GetAllAsyncTask(private val marcaDao: MarcaDao) :
        AsyncTask<Void, Void, List<Marca>>() {
        override fun doInBackground(vararg params: Void?): List<Marca>? {
            val lista = marcaDao.getAllNoLiveData()
            return lista
        }

        override fun onPostExecute(result: List<Marca>?) {
            super.onPostExecute(result)
        }

    }

    private class GetOneAsyncTask(private val marcaDao: MarcaDao) :
        AsyncTask<Marca, Void, List<Marca>>() {
        override fun doInBackground(vararg marcas: Marca): List<Marca>? {
            var lista = listOf<Marca>()
            for (marca in marcas){
                lista = marcaDao.getOne(marca.id)
            }
            return lista
        }

        override fun onPostExecute(result: List<Marca>?) {
            super.onPostExecute(result)
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

    fun getMarcasForList(): MutableList<Marca>{
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        var marcas = mutableListOf<Marca>()
        myExecutor.execute {
            if (marcaDao!= null){
                marcas = marcaDao.getMarcasForList()

            }
        }
        return marcas
    }

    fun getAll2(): List<Marca>?{
        if (marcaDao != null){
            val lista: List<Marca> = GetAllAsyncTask(marcaDao).execute().get()
            return lista
        }
        return null
    }

    fun getOne(marca: Marca): List<Marca>?{
        if (marcaDao != null){
            val lista: List<Marca> = GetOneAsyncTask(marcaDao).execute(marca).get()
            return lista
        }
        return null
    }

    fun getByCategoria(idCategoria: Int): LiveData<List<Marca>>{
        return marcaDao?.getbyCategoria(idCategoria) ?: MutableLiveData<List<Marca>>()
    }

}