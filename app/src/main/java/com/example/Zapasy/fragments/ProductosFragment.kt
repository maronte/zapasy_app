package com.example.Zapasy.fragments

import android.app.Application
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.R
import com.example.Zapasy.adapters.AdapterProductCard
import com.example.Zapasy.interfaces.ProductCardListener
import com.example.Zapasy.room.Product
import com.example.Zapasy.viewmodels.ProductViewModel


class Productos : Fragment(), ProductCardListener {

    lateinit var productosReycler: RecyclerView
    private lateinit var productsViewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_productos, container, false)
        productosReycler = view.findViewById(R.id.recyclerProducts)
        productosReycler.layoutManager = LinearLayoutManager(context)
        productosReycler.setHasFixedSize(true)
        productsViewModel = kotlin.run {
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        }
       addObserver()
        return view
    }
    private fun addObserver(){
        val observer = Observer<List<Product>> {products ->
            if (products != null){
                productosReycler.adapter = AdapterProductCard(context!!, products, this)
            }
        }
        productsViewModel.products.observe(this,observer)
    }

    override fun onClickDeleteButton(product: Product) {
        Toast.makeText(context,"Se va a morir xd", Toast.LENGTH_LONG).show()
        productsViewModel.deleteProduct(product)
    }

    override fun onClickCard(product: Product) {
        Toast.makeText(context,"Se va a abrir xd", Toast.LENGTH_LONG).show()
    }
}
