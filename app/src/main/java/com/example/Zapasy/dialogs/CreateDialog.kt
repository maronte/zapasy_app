package com.example.Zapasy.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ConfirmListener

class CreateDialog(val crear: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.createdialog, null)
        builder.setView(dialogView)
        val titulo = dialogView?.findViewById<TextView>(R.id.dialog_title)
        titulo?.text = crear
        with(builder) {
            setPositiveButton("Crear") { dialog, which ->
                //listener.onPositiveAction()
                dialog.dismiss()
            }
            setNegativeButton("descartar") { dialog, which ->
                dialog.cancel()
            }
        }
        return builder.create()
    }
}