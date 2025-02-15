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
        initInternalScan()
    }

    private fun initScan() { // 启动扫描
        // scanView 是在 layout xml 中声明的 zxing 库中定义的 DecoratedBarcodeView
        binding.scanView.decodeContinuous { result ->
            handleScannedUrl(result.text)
        }
        binding.scanView.resume() // 开始扫描（在已获得相机权限时调用）
    }

    override fun onResume() {
        super.onResume()
        binding.scanView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.scanView.pause()
    }

    private fun initInternalScan() { // 启动内置扫描
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                binding.scanResult.text = ""
            } else { // 扫描成功
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                handleScannedUrl(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleScannedUrl(result: String?) { // 在系统默认浏览器中打开扫描结果
        binding.scanResult.text = result
        kotlin.runCatching {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
            startActivity(intent)
        }.onFailure {
            it.printStackTrace()
        }
    }
}