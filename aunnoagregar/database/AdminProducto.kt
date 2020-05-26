package com.example.Zapasy.database

import android.widget.Toast
import com.example.Zapasy.ZapazyApp
import java.lang.Exception

class AdminProducto {

    fun addProducto(product: Product){
        try {
            val db = ZapazyApp.DB.writableDatabase

            val qry = "INSERT INTO producto (nombre,barcode,precio,existencia,vendido,perdido,daniado)" +
                    "VALUES" +
                        "(${product.name}, ${product.barcode}, ${product.price}, ${product.existing}, " +
                    " ${product.sold}, ${product.lost}, ${product.damaged});"


            db.execSQL(qry)
            db.close()
        } catch (ex: Exception){
            Toast.makeText(ZapazyApp.CONTEXT,"No se pudo registrar el producto", Toast.LENGTH_LONG).show()
        }
    }

    fun getAllProductos() :MutableList<Product>{
        var products : MutableList<Product> = mutableListOf()
        val db = ZapazyApp.DB
        try {
            val qry = "SELECT nombre, existencia FROM productos;"
            val response = db.readableDatabase.rawQuery(qry, null)
            response.moveToFirst()
            do {
                val product = Product()
                product.name = response.getString(response.getColumnIndex(Contract.Productos.NOMBRE))
                product.existing = response.getInt(response.getColumnIndex(Contract.Productos.EXISTENCIA))
                products.add(product)
            }while (response.moveToNext())
            db.close()
        } catch (ex: Exception){
            Toast.makeText(ZapazyApp.CONTEXT,"No se pudo recuperar los productos", Toast.LENGTH_LONG).show()
        }

        return products
    }
}