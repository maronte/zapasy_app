package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.adapters.AdapterProductxMarca
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.room.MarcaRepository
import com.example.Zapasy.Models.Product
import com.example.Zapasy.dialogs.ListProductosDialog
import com.example.Zapasy.interfaces.ListaProductoListener
import com.example.Zapasy.room.ProductRepository
import com.example.Zapasy.viewmodels.ProductViewModel

class DetalleEditarMarcaActivity : AppCompatActivity(), ConfirmListener, ListaProductoListener {

    private lateinit var marcaVisualizada: Marca
    private lateinit var nombreMarca: TextView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerProducts: RecyclerView
    private lateinit var añadirProducto: Button
    private lateinit var eliminarProducto: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_editar_marca)
        enableBackButton()
        añadirProducto = findViewById(R.id.añadirproducto)
        eliminarProducto = findViewById(R.id.eliminarproducto)
        val id = intent.getIntExtra("idMarc",0)
        marcaVisualizada = Marca()
        marcaVisualizada.id = id
        val marca = MarcaRepository(application).getOne(marcaVisualizada)
        if (marca != null){
            marcaVisualizada = marca.get(0)
        }
        nombreMarca = findViewById(R.id.marcaName)
        nombreMarca.text = marcaVisualizada.nombre
        productViewModel = kotlin.run {
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        }
        productViewModel.getByMarca(marcaVisualizada.id)
        recyclerProducts = findViewById(R.id.recyclerProducts)
        recyclerProducts.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        addObserver()
        añadirProducto.setOnClickListener {
            val lista = ProductRepository(application).getAll2()
            if (lista != null){
                val dialog = ListProductosDialog("Lista productos",lista, this,ListProductosDialog.AÑADIR)
                dialog.show(supportFragmentManager,null)
            }
        }
        eliminarProducto.setOnClickListener {
            val lista = productViewModel.productsByMarca.value
            if (lista != null){
                val dialog = ListProductosDialog("Lista productos",lista, this,ListProductosDialog.ELIMINAR)
                dialog.show(supportFragmentManager,null)
            }
        }
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
        actionBar.title = "Detalle de marca"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->{
                val dialog = ConfirmDialog("¿Deseas los cambios?","Guardar","Descartar",this)
                dialog.show(supportFragmentManager,null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPositiveAction() {
        marcaVisualizada.nombre = nombreMarca.text.toString()
        MarcaRepository(application).update(marcaVisualizada)
    }

    private fun addObserver(){
        val observer = Observer<List<Product>> { products ->
            if (products != null){
                recyclerProducts.adapter = AdapterProductxMarca(this,products)
            }
        }
        productViewModel.productsByMarca.observe(this,observer)
    }

    override fun añadirProducto(product: Product) {
        product.idMarca = marcaVisualizada.id
        ProductRepository(application).update(product)
    }

    override fun eliminarProducto(product: Product) {
        product.idMarca = null
        ProductRepository(application).update(product)
    }

}
