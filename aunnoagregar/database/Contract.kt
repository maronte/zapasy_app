package com.example.Zapasy.database

import android.provider.BaseColumns

class Contract {
    class Productos: BaseColumns{
        companion object{
            val ID_PRODUCTO = "id_producto"
            val NOMBRE = "nombre"
            val BARCODE = "barcode"
            val PRECIO = "precio"
            val EXISTENCIA = "existencia"
            val VENDIDO = "vendido"
            val PERDIDO = "perdido"
            val DANIADO = "daniado"
        }
    }

    class Grupo: BaseColumns{
        companion object{
            val ID_GRUPO = "id_grupo"
            val NOMBRE = "nombre"
        }
    }

    class Categoria: BaseColumns{
        companion object{
            val ID_CATEGORIA = "id_categoria"
            val NOMBRE = "nombre"
            val ID_GRUPO = "id_grupo"
        }
    }

    class ProductoXCategoria: BaseColumns{
        companion object{
            val ID_PRODUCTO = "id_producto"
            val ID_CATEGORIA = "id_categoria"
        }
    }
}