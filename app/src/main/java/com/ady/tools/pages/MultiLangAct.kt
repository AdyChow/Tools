package com.ady.tools.pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActMultiLangBinding
import com.ady.tools.kit.LangUtils

class MultiLangAct : AppCompatActivity() {

    private lateinit var binding: ActMultiLangBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMultiLangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.en.setOnClickListener {
            switchLang("en")
        }

        binding.zh.setOnClickListener {
            switchLang("zh")
        }
    }

    private fun switchLang(language: String) {
        LangUtils.switchLanguage(language)
        finish()
    }

}