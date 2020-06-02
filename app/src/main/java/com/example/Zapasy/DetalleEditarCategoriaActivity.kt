package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.adapters.AdapterCategoriaxMarca
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.dialogs.ListaMarcasDialog2
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.interfaces.ListaMarcaListener2
import com.example.Zapasy.room.CategoriasRepository
import com.example.Zapasy.room.MarcaRepository
import com.example.Zapasy.viewmodels.MarcaViewModel

class DetalleEditarCategoriaActivity : AppCompatActivity(),  ConfirmListener, ListaMarcaListener2{

    private lateinit var nombre: EditText
    private lateinit var recyclerMarcas: RecyclerView
    private lateinit var categoriaVisualizada: Categoria
    private lateinit var añadirMarca: Button
    private lateinit var marcaViewModel: MarcaViewModel
    private lateinit var eliminarMarca: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_categoria)
        añadirMarca = findViewById(R.id.btnañadirmarca)
        eliminarMarca= findViewById(R.id.btneliminarmarca)
        enableBackButton()
        val id = intent.getIntExtra("idCategori",0)
        categoriaVisualizada = Categoria()
        categoriaVisualizada.id = id
        categoriaVisualizada = CategoriasRepository(application).getOne(categoriaVisualizada)
        nombre = findViewById(R.id.categoryname)
        nombre.text = categoriaVisualizada.nombre.toEditable()
        marcaViewModel = kotlin.run {
            ViewModelProviders.of(this).get(MarcaViewModel::class.java)
        }
        marcaViewModel.marcaByCategoria(categoriaVisualizada.id)
        recyclerMarcas = findViewById(R.id.recyclerCategories)
        recyclerMarcas.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true)
        addObserver()
        añadirMarca.setOnClickListener {
            val lista = MarcaRepository(application).getAll2()
            if (lista != null){
                val dialog = ListaMarcasDialog2("Lista marcas", lista,this,ListaMarcasDialog2.AÑADIR)
                dialog.show(supportFragmentManager,null)
            }
        }
        eliminarMarca.setOnClickListener {
            val lista = MarcaRepository(application).getAll2()
            if (lista != null){
                val lista2 = marcaViewModel.marcasPorCategoria.value
                if (lista2 != null){
                    val dialog = ListaMarcasDialog2("Lista marcas", lista2,this,ListaMarcasDialog2.ELIMINAR)
                    dialog.show(supportFragmentManager,null)
                }
            }
        }
    }

    private fun addObserver(){
        val observer = Observer<List<Marca>> { marcas ->
            if (marcas != null){
                recyclerMarcas.adapter = AdapterCategoriaxMarca(this, marcas)
            }
        }
        marcaViewModel.marcasPorCategoria.observe(this,observer)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

    fun enableBackButton(){
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        if (intent.getIntExtra("Accion",0) == 1){
            actionBar.title = "Registrar Categoría"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->{
                val dialog = ConfirmDialog("¿Deseas guardar la categoría?","Guardar","Descartar",this)
                dialog.show(supportFragmentManager,null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPositiveAction() {
        categoriaVisualizada.nombre = nombre.text.toString()
        CategoriasRepository(application).update(categoriaVisualizada)
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun añadirMarca(marca: Marca) {
        marca.idCategoria = categoriaVisualizada.id
        MarcaRepository(application).update(marca)
    }

    override fun eliminarMarca(marca: Marca) {
        marca.idCategoria = null
        MarcaRepository(application).update(marca)
    }

}
