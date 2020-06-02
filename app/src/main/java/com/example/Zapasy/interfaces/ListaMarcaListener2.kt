package com.example.Zapasy.interfaces

import com.example.Zapasy.Models.Marca

interface ListaMarcaListener2 {
    fun añadirMarca (marca: Marca)
    fun eliminarMarca(marca: Marca)
}