package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.dialogs.ListaMarcasDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.interfaces.ListaMarcaListener
import com.example.Zapasy.room.MarcaRepository
import com.example.Zapasy.room.Product
import com.example.Zapasy.room.ProductRepository

class DetalleProductoActivity : AppCompatActivity(), ConfirmListener, ListaMarcaListener {

    private lateinit var coordinatorLayout: LinearLayout
    private lateinit var nameProduct: EditText
    private lateinit var priceProduct: EditText
    private lateinit var barcodeProduct: EditText
    private lateinit var existingProduct: EditText
    private lateinit var soldProduct: EditText
    private lateinit var damagedProduct: EditText
    private lateinit var lostProduct: EditText
    private lateinit var existingMoney: TextView
    private lateinit var soldProductMoney: TextView
    private lateinit var damagedProductMoney: TextView
    private lateinit var lostProductMoney: TextView
    private lateinit var productoVisualizado: Product
    private lateinit var marcaProducto: TextView
    private lateinit var editarMarcaBoton: Button
    private lateinit var marcasList: List<Marca>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idProduct = intent.extras?.get("idProduct")
        setContentView(R.layout.activity_detalle_producto)
        enableBackButton()
        val lista = MarcaRepository(application).getAll2()
        if (lista != null){
            marcasList = lista
        }
        inicializeElements()
        if (idProduct is Int){
            val p = Product()
            p.id = idProduct
            mapearProductoVista(p)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

    fun enableBackButton(){
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Detalle de producto"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    fun mapearProductoVista(product: Product){
        val listaProductos = ProductRepository(application).getOne(product)
        if (listaProductos != null){
            productoVisualizado = listaProductos[0]
            val product = productoVisualizado
            nameProduct.text = product.name.toEditable()
            priceProduct.text = product.price.toString().toEditable()
            barcodeProduct.text = product.barcode.toEditable()

            existingProduct.text = product.existing.toString().toEditable()
            val em = "En valor: \$" + (product.existing * product.price).toString()
            existingMoney.text = em.toEditable()

            soldProduct.text = product.sold.toString().toEditable()
            val sm = "En valor: \$" + (product.sold * product.price).toString()
            soldProductMoney.text = sm.toEditable()

            lostProduct.text = product.lost.toString().toEditable()
            val lm = "En valor: \$" + (product.lost * product.price).toString()
            lostProductMoney.text = lm.toEditable()

            damagedProduct.text = product.damaged.toString().toEditable()
            val dm = "En valor: \$" + (product.damaged * product.price).toString()
            damagedProductMoney.text = dm.toEditable()

            marcaProducto.text = product.idMarca.toString()
            for (marca in marcasList){
                if (marca.id == product.idMarca){
                    marcaProducto.text = marca.nombre
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->{
                val dialog = ConfirmDialog("¿Deseas guardar los cambios?","Guardar","Descartar",this)
                dialog.show(supportFragmentManager,null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPositiveAction() {

        productoVisualizado.name = nameProduct.text.toString()
        productoVisualizado.price = priceProduct.text.toString().toDouble()
        productoVisualizado.barcode = barcodeProduct.text.toString()
        productoVisualizado.existing = existingProduct.text.toString().toInt()
        productoVisualizado.sold = soldProduct.text.toString().toInt()
        productoVisualizado.damaged = damagedProduct.text.toString().toInt()
        productoVisualizado.lost = damagedProduct.text.toString().toInt()
        ProductRepository(application).update(productoVisualizado)

    }

    fun inicializeElements(){
        coordinatorLayout  = findViewById(R.id.linearLayoutDetalleProducto)
        nameProduct = findViewById(R.id.productnameinput)
        priceProduct = findViewById(R.id.priceproductinput)
        barcodeProduct = findViewById(R.id.barcodeinput)
        existingProduct = findViewById(R.id.existingquantityinput)
        soldProduct = findViewById(R.id.soldquantityinput)
        lostProduct = findViewById(R.id.lostquantityinput)
        damagedProduct = findViewById(R.id.damagedquantityinput)
        existingMoney = findViewById(R.id.existingmoney)
        soldProductMoney = findViewById(R.id.soldmoney)
        lostProductMoney = findViewById(R.id.lostmoney)
        damagedProductMoney = findViewById(R.id.damagedmoney)
        marcaProducto = findViewById(R.id.marca_productdetail)
        editarMarcaBoton = findViewById(R.id.editarMarca)
        editarMarcaBoton.setOnClickListener {
            val dialog = ListaMarcasDialog("Lista de marcas", marcasList, this)
            dialog.show(supportFragmentManager,null)
        }
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun onClickMarca(marca: Int) {
        if (marcasList != null){
            marcaProducto.text = marcasList[marca].nombre
            productoVisualizado.idMarca = marcasList[marca].id
        }
    }

}
