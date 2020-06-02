package com.example.Zapasy.Models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    onDelete = ForeignKey.SET_NULL,
    onUpdate = ForeignKey.CASCADE,
    entity = Categoria::class,
    parentColumns = ["id"],
    childColumns = ["idCategoria"]
)], indices = [Index(value = ["idCategoria"])]
)
data class Marca (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var nombre: String = "",
    var idCategoria: Int? = null
)