package com.example.Zapasy.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ProductCardListener
import com.example.Zapasy.room.Product

class AdapterProductCard(val context: Context, val productos:List<Product>, val listener: ProductCardListener)
    :RecyclerView.Adapter<AdapterProductCard.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nombre: TextView
        var descripcion: TextView
        var botonEliminar: Button
        init {
            nombre = itemView.findViewById(R.id.nombreProductoCard)
            descripcion = itemView.findViewById(R.id.descripcionProductCard)
            botonEliminar = itemView.findViewById(R.id.eliminarproductCard)
            itemView.setOnClickListener{
                listener.onClickCard(productos.get(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(context).inflate(R.layout.product_card,parent,false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return productos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = productos.get(position).name
        val descripcion = "Hay en existencia ${productos.get(position).existing} productos"
        holder.descripcion.text = descripcion
        holder.botonEliminar.setOnClickListener {
            listener.onClickDeleteButton(productos.get(position))
        }
    }

}