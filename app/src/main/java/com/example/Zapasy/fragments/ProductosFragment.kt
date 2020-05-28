package com.example.Zapasy.fragments

import android.app.Application
import android.content.Intent
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
import com.example.Zapasy.DetalleProductoActivity
import com.example.Zapasy.R
import com.example.Zapasy.adapters.AdapterProductCard
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.interfaces.ProductCardListener
import com.example.Zapasy.room.Product
import com.example.Zapasy.viewmodels.ProductViewModel


class Productos : Fragment(), ProductCardListener,  ConfirmListener{

    lateinit var productosReycler: RecyclerView
    private lateinit var productsViewModel: ProductViewModel
    private var productoABorrar = Product()

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
        val dialog = ConfirmDialog("¿Seguro que quieres borrar el producto?",
            "Borrar","Cancelar",this)
        dialog.show(fragmentManager!!,null)
        productoABorrar = product
    }

    override fun onClickCard(product: Product) {
        val intent = Intent(context,DetalleProductoActivity::class.java)
        intent.putExtra("idProduct", product.id)
        startActivity(intent)
    }

    override fun onPositiveAction() {
        if( !productoABorrar.name.equals("") ) productsViewModel.deleteProduct(productoABorrar)
        productoABorrar.name = ""
    }


}
