package com.example.Zapasy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca

import com.example.Zapasy.R
import com.example.Zapasy.adapters.AdapterCategoriaCard
import com.example.Zapasy.adapters.AdapterMarcaCard
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.CategoriaCardListener
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.interfaces.MarcaCardListener
import com.example.Zapasy.viewmodels.CategoríasViewModel
import com.example.Zapasy.viewmodels.MarcaViewModel

class Categorias : Fragment(), MarcaCardListener, ConfirmListener, CategoriaCardListener {

    private lateinit var marcasViewModel: MarcaViewModel
    private lateinit var categoriasViewModel: CategoríasViewModel
    lateinit var marcasReycler: RecyclerView
    private var marcaABorrar = Marca()
    private lateinit var spinner: Spinner
    private lateinit var categoriasRecycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_groups, container, false)
        marcasReycler = view.findViewById(R.id.recyclerMarcas)
        marcasReycler.layoutManager = LinearLayoutManager(context)
        marcasReycler.setHasFixedSize(true)
        categoriasRecycler = view.findViewById(R.id.recyclerCategories)
        categoriasRecycler.layoutManager = LinearLayoutManager(context)
        categoriasRecycler.setHasFixedSize(true)
        marcasViewModel = kotlin.run {
            ViewModelProviders.of(this).get(MarcaViewModel::class.java)
        }
        categoriasViewModel = kotlin.run {
            ViewModelProviders.of(this).get(CategoríasViewModel::class.java)
        }
        addObserver()
        addObserver2()
        spinner = view.findViewById(R.id.spinnerGroups)
        iniciarSpinner()
        return view
    }

    private fun addObserver(){
        val observer = Observer<List<Marca>> {marcas ->
            if (marcas != null){
                marcasReycler.adapter = AdapterMarcaCard(context!!, marcas, this)
            }
        }
        marcasViewModel.marcas.observe(this,observer)
    }

    override fun eliminarCard(marca: Marca) {
        marcaABorrar = marca
        val dialog = ConfirmDialog("¿Deseas eliminar la marca?","Borrar", "Cancelar",this)
        dialog.show(fragmentManager!!,null)
    }

    override fun onPositiveAction() {
        if (!marcaABorrar.nombre.equals("")) marcasViewModel.delete(marcaABorrar)
        marcaABorrar.nombre = ""
    }

    fun iniciarSpinner(){
        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position){
                        0->{
                            marcasReycler.visibility = View.VISIBLE
                            categoriasRecycler.visibility = View.INVISIBLE
                        }
                        1 ->{
                            marcasReycler.visibility = View.INVISIBLE
                            categoriasRecycler.visibility = View.VISIBLE
                        }
                    }
                }

            }
    }

    private fun addObserver2(){
        val observer = Observer<List<Categoria>> {categorias ->
            if (categorias != null){
                categoriasRecycler.adapter = AdapterCategoriaCard(context!!, categorias, this)
            }
        }
        categoriasViewModel.categorias.observe(this,observer)
    }

    override fun eliminarCard(categoria: Categoria) {
        categoriasViewModel.delete(categoria)
        // TODO: Abri dialogo
    }

}
