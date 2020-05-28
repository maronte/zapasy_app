package com.example.Zapasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.MarcaCardListener
import com.example.Zapasy.interfaces.ProductCardListener

class AdapterMarcaCard(val context: Context, val marcas:List<Marca>, val listener: MarcaCardListener)
    :RecyclerView.Adapter<AdapterMarcaCard.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre: TextView
        var botonEliminar: Button
        init {
            nombre = itemView.findViewById(R.id.nombreMarcaCard)
            botonEliminar = itemView.findViewById(R.id.eliminarCard)
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.marca_card,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return marcas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = marcas.get(position).nombre
        holder.botonEliminar.setOnClickListener {
            listener.eliminarCard(marcas.get(position))
        }
    }

}