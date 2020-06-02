package com.example.Zapasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R

class AdapterListaMarca (val context: Context, val marcas:List<Marca>)
    : RecyclerView.Adapter<AdapterListaMarca.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre: CheckBox
        init {
            nombre = itemView.findViewById(R.id.checkboxLista)
            itemView.setOnClickListener{

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.recycler_list,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return marcas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = marcas.get(position).nombre

    }

}