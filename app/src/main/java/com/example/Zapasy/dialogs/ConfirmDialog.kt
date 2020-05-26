package com.example.Zapasy.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ConfirmListener

class ConfirmDialog( val question: String, val accept: String, val cancel: String,
                     val listener: ConfirmListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val dialogView = inflater?.inflate(R.layout.confirmdialog, null)
        builder.setView(dialogView)
        val titulo = dialogView?.findViewById<TextView>(R.id.dialog_title)
        titulo?.text = question
        with(builder) {
            setPositiveButton(accept) { dialog, which ->
                listener.onPositiveAction()
                dialog.dismiss()
            }
            setNegativeButton(cancel) { dialog, which ->
                dialog.cancel()
            }
        }
        return builder.create()
    }
}