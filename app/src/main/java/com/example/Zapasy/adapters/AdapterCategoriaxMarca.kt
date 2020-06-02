package com.example.Zapasy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.DetalleEditarMarcaActivity
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R

class AdapterCategoriaxMarca(val context: Context, val marcas:List<Marca>)
    :RecyclerView.Adapter<AdapterCategoriaxMarca.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre: Button

        init {
            nombre = itemView.findViewById(R.id.txvNombreProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.recyclerproduct,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return marcas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = marcas.get(position).nombre
        holder.nombre.setOnClickListener {
            val intent = Intent(context, DetalleEditarMarcaActivity::class.java)
            intent.putExtra("idMarc", marcas[position].id as Int)
            context.startActivity(intent)
        }
    }

}