package com.example.Zapasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.Zapasy.room.Product
import com.example.Zapasy.room.ProductRepository

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProductRepository(application)
    val products = repository.getAll()

    fun saveProduct(product: Product){
        repository.insert(product)
    }

    fun deleteProduct(product: Product){
        repository.delete(product)
    }

}