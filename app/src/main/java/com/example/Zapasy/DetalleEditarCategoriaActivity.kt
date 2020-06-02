package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Categoria
import com.example.Zapasy.adapters.AdapterProductCard
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.room.CategoriasRepository
import com.example.Zapasy.room.Product
import com.example.Zapasy.viewmodels.ProductViewModel

class DetalleEditarCategoriaActivity : AppCompatActivity(),  ConfirmListener{

    private lateinit var nombre: EditText
    private lateinit var recyclerMarcas: RecyclerView
    private lateinit var categoriaVisualizada: Categoria
    private lateinit var categoriasRepository: CategoriasRepository
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_categoria)
        enableBackButton()
        val id = intent.getIntExtra("idCategori",0)
        categoriaVisualizada = Categoria()
        categoriaVisualizada.id = id
        categoriaVisualizada = CategoriasRepository(application).getOne(categoriaVisualizada)
        nombre = findViewById(R.id.categoryname)
        nombre.text = categoriaVisualizada.nombre.toEditable()
        productViewModel = kotlin.run {
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        }
        addObserver()

    }

    private fun addObserver(){
        val observer = Observer<List<Product>> { products ->
            if (products != null){
                //productosReycler.adapter = AdapterProductCard(context!!, products, this)
            }
        }
        productViewModel.products.observe(this,observer)
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

}
