package com.example.Zapasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.room.CategoriasRepository

class CategoríasViewModel (application: Application): AndroidViewModel(application){
    private val repository = CategoriasRepository(application)
    val categorias = repository.getAll()
    fun delete(categoria: Categoria){
        repository.delete(categoria)
    }
}