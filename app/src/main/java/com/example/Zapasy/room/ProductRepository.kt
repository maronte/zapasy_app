package com.example.Zapasy.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun delete(product: Product){
        if (productDao != null){
            DeleteAsyncTask(productDao).execute(product)
        }
    }

    fun getOne(id: Int): LiveData<List<Product>>{
        return productDao?.getOne(id) ?: MutableLiveData<List<Product>>()
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
                productDao.updateExisting(product.id,product.existing)
            }
        }
    }

}