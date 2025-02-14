package com.ady.tools

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActColorTxtBinding
import com.ady.tools.kit.ForegroundSpan
import com.ady.tools.kit.fontMontserratMedium

class ColorTxtAct : AppCompatActivity() {
    private lateinit var binding: ActColorTxtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActColorTxtBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.typeface = assets.fontMontserratMedium
        showColorfulText1()
        showColorfulText2()
        showColorfulText3()
    }

    private fun showColorfulText1() {
        binding.title.post { // 从左往右的渐变
            val linearGradient = LinearGradient(
                0f, 0f, binding.title.width.toFloat(), 0f, intArrayOf(
                    Color.parseColor("#000000"),
                    Color.parseColor("#ff0000"),
                    Color.parseColor("#00ff00"),
                    Color.parseColor("#0000ff"),
                    Color.parseColor("#000000")
                ), floatArrayOf(0f, 0.25f, 0.5f, 0.75f, 1f), Shader.TileMode.CLAMP
            )
            binding.title.paint.shader = linearGradient
            binding.title.invalidate()
        }
        // ps: 如果想实现从上往下的渐变，只需将LinearGradient的第三、四个参数改为：0f, binding.title.textSize
    }

    private fun showColorfulText2() {
        binding.title.post {
            val gradientDrawable =
                resources.getDrawable(R.drawable.test_color) as GradientDrawable?
            binding.title.paint.shader = LinearGradient(
                0f, 0f, binding.title.width.toFloat(), 0f,
                gradientDrawable?.colors?.get(0) ?: 0,
                gradientDrawable?.colors?.get(1) ?: 0,
                Shader.TileMode.CLAMP
            )
            binding.title.invalidate()
        }
    }

    private fun showColorfulText3() {
        binding.title.post {
            val spannableString = SpannableString(binding.title.text)
            val span = ForegroundSpan(
                resources.getColor(R.color.start_color),
                resources.getColor(R.color.end_color),
                binding.title.width.toFloat()
            )
            spannableString.setSpan(
                span,
                0,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.title.text = spannableString
        }
    }
}