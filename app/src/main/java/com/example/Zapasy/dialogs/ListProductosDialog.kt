package com.example.Zapasy.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.Zapasy.Models.Product
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ListaProductoListener

class ListProductosDialog(val crear: String, val productos: List<Product>, val listener: ListaProductoListener,
                          val action: Int): DialogFragment() {

    private var selected: Int = -1
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.list_marca_dialog, null)
        builder.setView(dialogView)
        val titulo = dialogView?.findViewById<TextView>(R.id.tituloListMarcas)
        val contenedor = dialogView?.findViewById<RadioGroup>(R.id.rgMarcas)
        titulo?.text = crear
        if (contenedor != null){
            crearRadioGroup(contenedor)
        }
        with(builder) {
            setPositiveButton("Seleccionar") { dialog, which ->
                if (selected >= 0 && action == AÑADIR){
                    listener.añadirProducto(productos[selected])
                } else if (selected >= 0 && action == ELIMINAR){
                    listener.eliminarProducto(productos[selected])
                }
                dialog.dismiss()
            }
            setNegativeButton("Descartar") { dialog, which ->
                dialog.cancel()
            }
        }
        return builder.create()
    }

    private fun crearRadioGroup(contenedor: RadioGroup){
        val limite = productos.size - 1
        for ( i in 0..limite ){
            val boton = RadioButton(context)
            boton.setText(productos[i].name)
            contenedor.addView(boton)
            boton.setOnClickListener {
                selected = i
            }
        }
    }

    companion object{
        val AÑADIR = 1
        val ELIMINAR = 2
    }

}