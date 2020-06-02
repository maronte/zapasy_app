package com.example.Zapasy.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Zapasy.Models.Product
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ProductRepository(application: Application) {

    private val productDao: ProductDao? = ZapasyDatabase.getInstance(application)?.productDao()

    fun insert(product: Product){
        if (productDao != null){
            InsertAsyncTask(productDao).execute(product)
        }
    }

    private class InsertAsyncTask(private val productDao: ProductDao) :
        AsyncTask<Product, Void, Void>() {
        override fun doInBackground(vararg products: Product?): Void? {
            for (product in products) {
                if (product != null) productDao.insert(product)
            }
            return null
        }
    }

    private class GetAllAsyncTask(private val productDao: ProductDao) :
        AsyncTask<Void, Void, List<Product>>() {
        override fun doInBackground(vararg params: Void?): List<Product>? {
            val lista = productDao.getAllNoLiveData()
            return lista
        }

        override fun onPostExecute(result: List<Product>?) {
            super.onPostExecute(result)
        }

    }

    private class GetOneAsyncTask(private val productDao: ProductDao) :
        AsyncTask<Product, Void, List<Product>>() {
        override fun doInBackground(vararg products: Product): List<Product>? {
            var lista = listOf<Product>()
            for (product in products){
                lista = productDao.getOne(product.id)
            }
            return lista
        }

        override fun onPostExecute(result: List<Product>?) {
            super.onPostExecute(result)
        }

    }

    private class DeleteAsyncTask(private val productDao: ProductDao) :
        AsyncTask<Product, Void, Void>() {
        override fun doInBackground(vararg products: Product?): Void? {
            for (product in products) {
                if (product != null) productDao.delete(product)
            }
            return null
        }
    }

    private class UpdateAsyncTask(private val productDao: ProductDao) :
        AsyncTask<Product, Void, Void>() {
        override fun doInBackground(vararg products: Product?): Void? {
            for (product in products) {
                if (product != null) productDao.update(product)
            }
            return null
        }
    }

    fun getAll(): LiveData<List<Product>>{
        return productDao?.getAll() ?: MutableLiveData<List<Product>>()
    }

    fun getByMarca(idMarca: Int): LiveData<List<Product>>{
        return productDao?.getByMarca(idMarca) ?: MutableLiveData<List<Product>>()
    }

    fun delete(product: Product){
        if (productDao != null){
            DeleteAsyncTask(productDao).execute(product)
        }
    }

    fun getOne(product: Product): List<Product>?{
        if (productDao != null){
            val lista: List<Product> = GetOneAsyncTask(productDao).execute(product).get()
            return lista
        }
        return null
    }

    fun update(product: Product){
        if (productDao != null){
            UpdateAsyncTask(productDao).execute(product)
        }
    }

    fun updateExisting(product: Product){
        val myExecutor : Executor = Executors.newSingleThreadExecutor()
        myExecutor.execute {
            if (productDao!= null){
                productDao.updateExisting(product.barcode)
            }
        }
    }

    fun getAll2(): List<Product>?{
        if (productDao != null){
            val lista: List<Product> = GetAllAsyncTask(productDao).execute().get()
            return lista
        }
        return null
    }

}