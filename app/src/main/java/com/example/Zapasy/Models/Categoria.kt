package com.example.Zapasy.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Categoria (
    @PrimaryKey (autoGenerate = true)
    var id: Int,
    var nombre: String
)