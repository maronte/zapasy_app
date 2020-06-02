package com.example.Zapasy.room

import android.app.Application
import android.os.AsyncTask
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

    fun update(categoria: Categoria){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (categoriaDao!= null){
                categoriaDao.update(categoria)
            }
        }
    }

    private class GetOneAsyncTask(private val categoriaDao: CategoriaDao) :
        AsyncTask<Categoria, Void, Categoria>() {
        override fun doInBackground(vararg categorias: Categoria): Categoria? {
            var categoria = Categoria()
            for (categoria2 in categorias){
                categoria = categoriaDao.getOne(categoria2.id)
            }
            return categoria
        }

        override fun onPostExecute(result: Categoria?) {
            super.onPostExecute(result)
        }

    }

    fun getOne(categoria: Categoria): Categoria{
        var categoriaDevolver = Categoria()
        if (categoriaDao != null){
            categoriaDevolver = GetOneAsyncTask(categoriaDao).execute(categoria).get()
            return categoriaDevolver
        }
        return categoriaDevolver
    }

}