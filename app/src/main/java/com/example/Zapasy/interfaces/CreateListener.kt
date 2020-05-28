package com.example.Zapasy.interfaces

import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca

interface CreateListener {
    fun createMarca(marca: Marca)
    fun createCategoria(categoria: Categoria)
}