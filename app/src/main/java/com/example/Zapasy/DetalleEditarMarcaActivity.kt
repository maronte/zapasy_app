package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.Zapasy.room.Product
import com.example.Zapasy.viewmodels.ProductViewModel

class DetalleEditarMarcaActivity : AppCompatActivity(), ConfirmListener {

    private lateinit var marcaVisualizada: Marca
    private lateinit var nombreMarca: TextView
    private lateinit var productViewModel: ProductViewModel
    private lateinit var recyclerProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_editar_marca)
        enableBackButton()
        val id = intent.getIntExtra("idMarc",0)
        //Toast.makeText(this,id.toString(),Toast.LENGTH_SHORT).show()
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

}
