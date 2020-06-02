package com.example.Zapasy

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class CapturarCodigoActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var escaner: ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_capturar_codigo)
        escaner = ZXingScannerView(this)
        setContentView(escaner)
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
        val intent = Intent()
        intent.putExtra("codigo",codigo)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

}
