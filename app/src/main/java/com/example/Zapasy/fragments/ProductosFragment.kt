package com.example.Zapasy.fragments

import android.app.Application
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.R
import com.example.Zapasy.adapters.AdapterProductCard
import com.example.Zapasy.room.Product
import com.example.Zapasy.viewmodels.ProductViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Productos.newInstance] factory method to
 * create an instance of this fragment.
 */
class Productos : Fragment() {

    lateinit var productosReycler: RecyclerView
    lateinit var productosList: MutableList<Product>
    private lateinit var productsViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
                productosReycler.adapter = AdapterProductCard(context!!, products)
            }
        }
        productsViewModel.products.observe(this,observer)
    }
}
