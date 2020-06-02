package com.example.Zapasy

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.Zapasy.Models.Marca
import com.example.Zapasy.dialogs.ConfirmDialog
import com.example.Zapasy.dialogs.ListaMarcasDialog
import com.example.Zapasy.interfaces.ConfirmListener
import com.example.Zapasy.interfaces.ListaMarcaListener
import com.example.Zapasy.room.MarcaRepository
import com.example.Zapasy.Models.Product
import com.example.Zapasy.room.ProductRepository
import com.google.android.material.snackbar.Snackbar


class CrearProductoActivity : AppCompatActivity(), ConfirmListener, ListaMarcaListener {

    private val CAMERA_REQUEST_CODE = 0
    private val CODIGO_INTENTO = 1
    private var permisoCamaraConcedido = false
    private  var permisoSolicitadoDesdeBoton: Boolean = false
    private lateinit var coordinatorLayout: LinearLayout
    private lateinit var nameProduct: EditText
    private lateinit var escanear: Button
    private lateinit var priceProduct: EditText
    private lateinit var barcodeProduct: EditText
    private lateinit var existingProduct: EditText
    private lateinit var soldProduct: EditText
    private lateinit var damagedProduct: EditText
    private lateinit var lostProduct: EditText
    private lateinit var buttonAñadirMarca: Button
    private lateinit var marcaProducto: TextView
    private var productToRegist = Product()
    private lateinit var marcasList: List<Marca>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_producto)
        enableBackButton()
        val lista = MarcaRepository(application).getAll2()
        if (lista != null){
            marcasList = lista
        }
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
        escanear = findViewById(R.id.btncapturarcodigo)
        escanear.setOnClickListener {
            if (!permisoCamaraConcedido) {
                Toast.makeText(this, "Por favor permite que la app acceda a la cámara", Toast.LENGTH_SHORT).show()
                permisoSolicitadoDesdeBoton = true
                verificarYPedirPermisosDeCamara()
            }
            escanear()
        }
        nameProduct = findViewById(R.id.productnameinput)
        priceProduct = findViewById(R.id.priceproductinput)
        barcodeProduct = findViewById(R.id.barcodeinput)
        existingProduct = findViewById(R.id.existinginput)
        soldProduct = findViewById(R.id.soldinput)
        damagedProduct = findViewById(R.id.damagedinput)
        marcaProducto = findViewById(R.id.marca_product)
        lostProduct = findViewById(R.id.lostinput)
        buttonAñadirMarca = findViewById(R.id.añadirmarcap)
        if (marcasList != null){
            buttonAñadirMarca.setOnClickListener {
                val dialog = ListaMarcasDialog("Lista de marcas", marcasList, this)
                dialog.show(supportFragmentManager,null)
            }
        }
    }


    fun mapingToModel(product: Product) {
        product.name = nameProduct.text.toString()
        product.price = priceProduct.text.toString().toDouble()
        product.barcode = barcodeProduct.text.toString()
        if (!existingProduct.text.toString().equals("")) product.existing = existingProduct.text.toString().toInt()
        if( !soldProduct.text.toString().equals("") ) product.sold = soldProduct.text.toString().toInt()
        if (!lostProduct.text.toString().equals("")) product.lost = lostProduct.text.toString().toInt()
        if (!damagedProduct.text.toString().equals("")) product.damaged = damagedProduct.text.toString().toInt()
    }

    override fun onPositiveAction() {
        if( priceProduct.text.toString().equals("")
            and barcodeProduct.text.toString().equals("")
            and nameProduct.text.toString().equals("") ){

            Snackbar.make(coordinatorLayout,"No puedes registrar un producto sin nombre, ni " +
                    "sin precio",Snackbar.LENGTH_LONG).show()

        }else{
            mapingToModel(productToRegist)
            ProductRepository(application).insert(productToRegist)
            Snackbar.make(coordinatorLayout,"Has añadido un producto",
                Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onClickMarca(marca: Int) {
        if (marcasList != null){
            marcaProducto.text = marcasList[marca].nombre
            productToRegist.idMarca = marcasList[marca].id
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                     if(permisoSolicitadoDesdeBoton){
                         permisoCamaraConcedido = true
                         escanear()
                     }
                } else {
                    Toast.makeText(this,"No puedes escanear sin otorgar permisos",Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Este else lo dejamos por si sale un permiso que no teníamos controlado.
            }
        }
    }

    fun escanear(){
        val intent = Intent(this, CapturarCodigoActivity::class.java)
        startActivityForResult(intent,CODIGO_INTENTO)
    }

    private fun verificarYPedirPermisosDeCamara() {
        val estadoDePermiso =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoCamaraConcedido = true
        } else {
            // Si no, pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CODIGO_INTENTO) {
            if (resultCode === Activity.RESULT_OK) {
                if (data?.getStringExtra("codigo")!= null) {
                    barcodeProduct.text = data.getStringExtra("codigo").toEditable()
                }
            }
        }
    }

}
