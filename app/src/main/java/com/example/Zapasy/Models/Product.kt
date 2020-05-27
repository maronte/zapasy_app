package com.example.Zapasy.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [ Index(value = ["barcode"], unique = true) ]
)
data class Product (

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String = "",
    var barcode: String = "",
    var price: Double =0.0,
    var existing: Int =0,
    var sold: Int = 0,
    var lost: Int  = 0,
    var damaged: Int  = 0,
    var image: String  = ""
)