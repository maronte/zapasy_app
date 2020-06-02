package com.example.Zapasy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.CategoriaCardListener
import com.example.Zapasy.interfaces.ProductCardListener

class AdapterCategoriaCard(val context: Context, val categorias:List<Categoria>, val listener: CategoriaCardListener)
    :RecyclerView.Adapter<AdapterCategoriaCard.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre: TextView
        var botonEliminar: Button
        init {
            nombre = itemView.findViewById(R.id.nombreCategoriaCard)
            botonEliminar = itemView.findViewById(R.id.eliminarCard)
            itemView.setOnClickListener{
                listener.onClickCard(categorias.get(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.categoria_card,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = categorias.get(position).nombre
        holder.botonEliminar.setOnClickListener {
            listener.eliminarCard(categorias.get(position))
        }
    }

}