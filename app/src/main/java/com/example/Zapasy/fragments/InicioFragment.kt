package com.example.Zapasy.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.Zapasy.InventariarActivity

import com.example.Zapasy.R

class Inicio : Fragment() {

    private lateinit var buttonInventariar: Button
    private val CAMERA_REQUEST_CODE = 0
    private val CODIGO_INTENTO = 1
    private var permisoCamaraConcedido = false
    private  var permisoSolicitadoDesdeBoton: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        buttonInventariar = view.findViewById<Button>(R.id.btnInventariar)
        loadbuttonInventariar()

        return view
    }

    fun loadbuttonInventariar(){
        buttonInventariar.setOnClickListener{
            if (!permisoCamaraConcedido) {
                Toast.makeText(context, "Por favor permite que la app acceda a la cámara", Toast.LENGTH_SHORT).show()
                permisoSolicitadoDesdeBoton = true
                verificarYPedirPermisosDeCamara()
            }
            escanear()
        }
    }

    fun escanear(){
        val intent = Intent(this.context, InventariarActivity::class.java)
        startActivity(intent)
    }

    private fun verificarYPedirPermisosDeCamara() {
        val estadoDePermiso =
            ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoCamaraConcedido = true
        } else {
            // Si no, pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(
                activity!!, arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    if(permisoSolicitadoDesdeBoton){
                        permisoCamaraConcedido = true
                        escanear()
                    }
                } else {
                    Toast.makeText(context,"No puedes escanear sin otorgar permisos", Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Este else lo dejamos por si sale un permiso que no teníamos controlado.
            }
        }
    }
}
