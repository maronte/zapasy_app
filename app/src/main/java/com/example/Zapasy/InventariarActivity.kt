package com.example.Zapasy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Zapasy.Models.Product
import com.example.Zapasy.room.ProductRepository
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class InventariarActivity : AppCompatActivity(), ZXingScannerView.ResultHandler{

    private lateinit var escaner: ZXingScannerView
    private lateinit var container: FrameLayout
    private lateinit var rootLayout: ConstraintLayout
    private lateinit var btnTerminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hacer_inventario)
        container = findViewById(R.id.containercamera)
        rootLayout = findViewById(R.id.rootLayout)
        escaner = ZXingScannerView(this)
        container.addView(escaner)
        supportActionBar?.title = "Inventariando"
        btnTerminar = findViewById(R.id.btnterminarinventario)
        btnTerminar.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        escaner.setResultHandler(this)
        escaner.startCamera()
    }

    override fun onPause() {
        super.onPause()
        escaner.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        var codigo = rawResult?.text
        if (codigo != null){
            val product = Product()
            product.barcode = codigo
            ProductRepository(application).updateExisting(product)
            Snackbar.make(rootLayout,"Inventario actualizado", Snackbar.LENGTH_LONG).show()
        }
        escaner.setResultHandler(this)
        escaner.startCamera()
    }
}
