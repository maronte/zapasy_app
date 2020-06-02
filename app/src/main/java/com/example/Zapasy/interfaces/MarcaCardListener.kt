package com.example.Zapasy.interfaces

import com.example.Zapasy.Models.Marca

interface MarcaCardListener {
    fun eliminarCard(marca: Marca)
    fun onClickCard(marca: Marca)
}