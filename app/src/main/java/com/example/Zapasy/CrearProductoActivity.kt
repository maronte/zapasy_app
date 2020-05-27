package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.Zapasy.room.Product
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.viewmodels.ProductViewModel
import com.google.android.material.snackbar.Snackbar

class CrearProductoActivity : AppCompatActivity(), ConfirmListener {

    private lateinit var coordinatorLayout: LinearLayout
    private lateinit var nameProduct: EditText
    private lateinit var priceProduct: EditText
    private lateinit var barcodeProduct: EditText
    private lateinit var existingProduct: EditText
    private lateinit var soldProduct: EditText
    private lateinit var damagedProduct: EditText
    private lateinit var lostProduct: EditText
    private lateinit var productsViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)
        enableBackButton()
        inicializeVariables()
        productsViewModel = kotlin.run {
            ViewModelProviders.of(this).get(ProductViewModel::class.java)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return false
    }

    fun enableBackButton(){
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Registrar producto"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save ->{
                val dialog = ConfirmDialog("¿Deseas guardar el producto?","Guardar","Descartar",this)
                dialog.show(supportFragmentManager,null)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun inicializeVariables(){
        coordinatorLayout = findViewById(R.id.rootActivityCrearProducto)
        nameProduct = findViewById(R.id.productnameinput)
        priceProduct = findViewById(R.id.priceproductinput)
        barcodeProduct = findViewById(R.id.barcodeinput)
        existingProduct = findViewById(R.id.existinginput)
        soldProduct = findViewById(R.id.soldinput)
        damagedProduct = findViewById(R.id.damagedinput)
        lostProduct = findViewById(R.id.lostinput)
    }

    // TODO: Corregir el mapeo de los productos
    fun mapingToModel(): Product {
        var product = Product()
        product.name = nameProduct.text.toString()
        product.price = priceProduct.text.toString().toDouble()
        product.barcode = barcodeProduct.text.toString()
        if (!existingProduct.text.toString().equals("")) product.existing = existingProduct.text.toString().toInt()
        if( !soldProduct.text.toString().equals("") ) product.sold = soldProduct.text.toString().toInt()
        if (!lostProduct.text.toString().equals("")) product.lost = lostProduct.text.toString().toInt()
        if (!damagedProduct.text.toString().equals("")) product.damaged = damagedProduct.text.toString().toInt()
        return product
    }

    override fun onPositiveAction() {
        if( priceProduct.text.toString().equals("")
            and barcodeProduct.text.toString().equals("")
            and nameProduct.text.toString().equals("") ){

            Snackbar.make(coordinatorLayout,"No puedes registrar un producto sin nombre, ni " +
                    "sin precio",Snackbar.LENGTH_LONG).show()

        }else{
            val productToRegist = mapingToModel()
            productsViewModel.saveProduct(productToRegist)
            Snackbar.make(coordinatorLayout,"Se creó el producto con éxito",
                Snackbar.LENGTH_LONG).show()
        }
    }

}
