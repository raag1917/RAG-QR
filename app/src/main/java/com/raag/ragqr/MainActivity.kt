package com.raag.ragqr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.raag.ragqr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(view: View?){
        when(view?.id){
            R.id.btnReadQR -> {
                intentIntegrator.setBeepEnabled(true).initiateScan()
            }
            R.id.btnGenerateQR ->{
                val code = findViewById<EditText>(R.id.etQR)
                val img = findViewById<ImageView>(R.id.ivQR)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(code.text.toString(), BarcodeFormat.QR_CODE, 400, 400)
                img.setImageBitmap(bitmap)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if(intentResult != null){
            if(intentResult.contents != null){
                val code = findViewById<EditText>(R.id.etQR)
                code.setText(intentResult.contents)
            } else {
                Toast.makeText(this, getString(R.string.no_data), Toast.LENGTH_LONG).show()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    private lateinit var intentIntegrator: IntentIntegrator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityMainBinding.inflate(layoutInflater).apply{
        setContentView(root)

            intentIntegrator = IntentIntegrator(this@MainActivity)
            btnGenerateQR.setOnClickListener(this@MainActivity)
            btnReadQR.setOnClickListener(this@MainActivity)

        }

    }
}