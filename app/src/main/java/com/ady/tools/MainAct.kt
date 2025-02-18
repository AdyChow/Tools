package com.ady.tools

import android.content.Intent
import android.os.Bundle
import com.ady.tools.databinding.ActMainBinding
import com.ady.tools.pages.ColorTxtAct
import com.ady.tools.pages.CoverAct
import com.ady.tools.pages.MultiLangAct
import com.ady.tools.pages.ScanCodeAct
import com.ady.tools.pages.SmallPicAct


class MainAct : BaseAct() {

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
        binding.scanCode.text = "扫描二维码"
        binding.preview.text = "查看大图过渡动效"
        binding.multiLang.text = "多语言切换"
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
        binding.preview.setOnClickListener {
            startActivity(Intent(this, SmallPicAct::class.java))
        }
        binding.multiLang.setOnClickListener {
            startActivity(Intent(this, MultiLangAct::class.java))
        }
    }
}