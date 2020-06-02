package com.example.Zapasy.interfaces

import com.example.Zapasy.Models.Product

interface ListaProductoListener {
    fun añadirProducto (product: Product)
    fun eliminarProducto(product: Product)
}