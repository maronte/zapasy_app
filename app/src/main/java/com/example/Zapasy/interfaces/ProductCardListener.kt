package com.example.Zapasy.interfaces

import com.example.Zapasy.room.Product

interface ProductCardListener {

    fun onClickDeleteButton(product: Product)
    fun onClickCard(product: Product)

}