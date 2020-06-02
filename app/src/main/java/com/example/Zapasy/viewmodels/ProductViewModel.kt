package com.example.Zapasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.Zapasy.Models.Product
import com.example.Zapasy.room.ProductRepository

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProductRepository(application)
    val products = repository.getAll()
    lateinit var productsByMarca: LiveData<List<Product>>

    fun saveProduct(product: Product){
        repository.insert(product)
    }

    fun deleteProduct(product: Product){
        repository.delete(product)
    }

    fun updateProduct(product: Product){
        repository.update(product)
    }

    fun getByMarca(idMarca: Int){
        productsByMarca = repository.getByMarca(idMarca)
    }

}