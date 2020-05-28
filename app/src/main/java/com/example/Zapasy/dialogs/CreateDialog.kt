package com.example.Zapasy.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.CreateListener

class CreateDialog(val crear: String, val action: Int, val listener: CreateListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.createdialog, null)
        builder.setView(dialogView)
        val titulo = dialogView?.findViewById<TextView>(R.id.dialog_title)
        val nombre = dialogView?.findViewById<EditText>(R.id.nombreitem)
        titulo?.text = crear
        with(builder) {
            setPositiveButton("Crear") { dialog, which ->
                if(action == CREATE_MARCA && nombre != null){
                    val marca = Marca()
                    marca.nombre = nombre.text.toString()
                    listener.createMarca(marca)
                }
                if (action == CREATE_CATEGORIA && nombre != null){
                    val categoria = Categoria()
                    categoria.nombre = nombre.text.toString()
                    listener.createCategoria(categoria)
                }
                dialog.dismiss()
            }
            setNegativeButton("descartar") { dialog, which ->
                dialog.cancel()
            }
        }
        return builder.create()
    }

    companion object{
        val CREATE_MARCA = 1
        val CREATE_CATEGORIA = 2
    }

}