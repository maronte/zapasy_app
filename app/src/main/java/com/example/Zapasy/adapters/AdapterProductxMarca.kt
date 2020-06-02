package com.example.Zapasy.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.DetalleProductoActivity
import com.example.Zapasy.R
import com.example.Zapasy.interfaces.ProductCardListener
import com.example.Zapasy.room.Product

class AdapterProductxMarca(val context: Context, val productos:List<Product>)
    :RecyclerView.Adapter<AdapterProductxMarca.ViewHolder>() {

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
        return productos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = productos.get(position).name
        holder.nombre.setOnClickListener {
            val intent = Intent(context, DetalleProductoActivity::class.java)
            intent.putExtra("idProduct", productos[position].id)
            context.startActivity(intent)
        }
    }

}