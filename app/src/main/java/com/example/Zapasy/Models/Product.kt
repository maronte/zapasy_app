package com.example.Zapasy.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.Zapasy.Models.Marca

@Entity(
    indices = [ Index(value = ["barcode"], unique = true), Index(value = ["idMarca"]) ],
    foreignKeys = [
        ForeignKey(
            entity = Marca::class,
            parentColumns = ["id"],
            childColumns = ["idMarca"])
    ]
)
data class Product (

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var barcode: String = "",
    var price: Double =0.0,
    var existing: Int =0,
    var sold: Int = 0,
    var lost: Int  = 0,
    var damaged: Int  = 0,
    var image: String  = "",
    var idMarca: Int? = null
)