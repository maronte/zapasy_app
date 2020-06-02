package com.example.Zapasy.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ListaMarcaListener

class ListaMarcasDialog(val crear: String, val marcas: List<Marca>, val listener: ListaMarcaListener): DialogFragment() {

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
            setPositiveButton("Añadir") { dialog, which ->
                if (selected >= 0){
                    listener.onClickMarca(selected)
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
        val limite = marcas.size - 1
        for ( i in 0..limite ){
            val boton = RadioButton(context)
            boton.setText(marcas[i].nombre)
            contenedor.addView(boton)
            boton.setOnClickListener {
                selected = i
            }
        }
    }

}