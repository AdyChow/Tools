package com.ady.tools.pages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActScanCodeBinding
import com.google.zxing.integration.android.IntentIntegrator


class ScanCodeAct : AppCompatActivity() {

    private lateinit var binding: ActScanCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActScanCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScan()
    }

    private fun initScan() { // 启动内置扫描
        val integrator = IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan a QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                binding.scanResult.text = ""
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show();
                binding.scanResult.text = result.contents
                kotlin.runCatching {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result.contents))
                    startActivity(intent)
                }.onFailure {
                    it.printStackTrace()
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}