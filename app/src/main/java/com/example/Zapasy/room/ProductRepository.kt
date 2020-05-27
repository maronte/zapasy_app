package com.example.Zapasy.room

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

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

    fun getAll(): LiveData<List<Product>>{
        return productDao?.getAll() ?: MutableLiveData<List<Product>>()
    }

    fun delete(product: Product){
        if (productDao != null){
            DeleteAsyncTask(productDao).execute(product)
        }
    }
}