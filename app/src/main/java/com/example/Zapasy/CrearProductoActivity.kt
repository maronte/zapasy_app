package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.Zapasy.database.Product
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.interfaces.ConfirmListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)
        enableBackButton()
        inicializeVariables()

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

    override fun onDestroy() {
        Toast.makeText(this,"memueroxd",Toast.LENGTH_SHORT).show()
        super.onDestroy()
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

    fun mapingToModel(): Product {
        var product : Product = Product()
        product.name = priceProduct.text.toString()
        product.barcode = barcodeProduct.text.toString()
        if( !soldProduct.text.toString().equals("")
            and !damagedProduct.text.toString().equals("")
            and !lostProduct.text.toString().equals("")
            and !existingProduct.text.toString().equals("") ){
            product.existing = existingProduct.text.toString().toInt()
            product.sold = soldProduct.text.toString().toInt()
            product.damaged = damagedProduct.text.toString().toInt()
            product.lost = lostProduct.text.toString().toInt()
        }
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
            Snackbar.make(coordinatorLayout,"Se creó el producto con éxito",
                Snackbar.LENGTH_LONG).show()
        }
    }

}
