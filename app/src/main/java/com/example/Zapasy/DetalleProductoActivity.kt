package com.example.Zapasy

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.Zapasy.adapters.AdapterProductCard
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.room.Product
import com.example.Zapasy.room.ProductDao
import com.example.Zapasy.room.ProductRepository
import com.example.Zapasy.room.ZapasyDatabase
import com.example.Zapasy.viewmodels.ProductViewModel

class DetalleProductoActivity : AppCompatActivity(), ConfirmListener {

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
    private lateinit var productsViewModel: ProductViewModel
    private lateinit var productVisualizado: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idProduct = intent.extras?.get("idProduct")
        productsViewModel = kotlin.run {
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        }
        if (idProduct is Int){
            productsViewModel.getOne(idProduct)
            Toast.makeText(this,idProduct.toString(),Toast.LENGTH_SHORT).show()

        }
        setContentView(R.layout.activity_detalle_producto)
        enableBackButton()
        inicializeElements()
        addObserver()
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
        //productsViewModel.updateProduct2(productVisualizado)
        ProductRepository(application).updateExisting(productVisualizado)
        Toast.makeText(this,productVisualizado.id.toString(),Toast.LENGTH_SHORT).show()
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
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun addObserver(){
        val observer = Observer<List<Product>> { products ->
            if (products != null){
                val product = products.get(0)
                productVisualizado = product
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
            }
        }
        productsViewModel.oneProduct.observe(this,observer)
    }

}
