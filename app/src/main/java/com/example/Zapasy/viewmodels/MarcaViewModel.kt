package com.example.Zapasy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.room.MarcaRepository

class MarcaViewModel(application: Application): AndroidViewModel(application) {
    private val repository = MarcaRepository(application)
    val marcas = repository.getAll()

    fun delete(marca: Marca){
        repository.delete(marca)
    }
}