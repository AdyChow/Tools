package com.ady.tools

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActMainBinding
import com.ady.tools.pages.ColorTxtAct
import com.ady.tools.pages.CoverAct
import com.ady.tools.pages.ScanCodeAct


class MainAct : AppCompatActivity() {

    private lateinit var binding: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initEvents()
    }

    private fun initView() {
        binding.makeCover.text = "view 存图到相册"
        binding.colorTxt.text = "彩色渐变字"
        binding.scanCode.text = "扫描二维码下载"
    }

    private fun initEvents() {
        binding.makeCover.setOnClickListener {
            startActivity(Intent(this, CoverAct::class.java))
        }
        binding.colorTxt.setOnClickListener {
            startActivity(Intent(this, ColorTxtAct::class.java))
        }
        binding.scanCode.setOnClickListener {
            startActivity(Intent(this, ScanCodeAct::class.java))
        }
    }
}