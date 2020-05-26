package com.example.Zapasy.database

data class Product (

    val id: Int = 0,
    var nombre: String = "",
    var barcode: String = "",
    var name: String = "",
    var price: Double =0.0,
    var existing: Int =0,
    var sold: Int = 0,
    var lost: Int  = 0,
    var damaged: Int  = 0,
    var image: String  = ""
)