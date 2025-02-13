package com.ady.tools

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ady.tools.databinding.ActMainBinding
import java.io.File
import java.io.IOException
import java.util.UUID


class MainAct : AppCompatActivity() {

    private lateinit var binding: ActMainBinding
    private var coverName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.typeface = assets.fontMontserratMedium

        showColorfulText1()
//        showColorfulText2()
//        showColorfulText3()

        coverName = "showColorfulText1"
        makeCover()
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


    private fun makeCover() {
        binding.title.setOnLongClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    0x123
                )
            } else {
                savePic()
            }
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0x123) {
            savePic()
        }
    }


    private fun savePic() {
        val view = binding.title
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Tools"
        )
        if (!file.exists()) {
            file.mkdirs()
        }
        saveImageToGallery(this, bitmap, coverName ?: ("ady_" + UUID.randomUUID().toString()))


        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.data = Uri.fromFile(file)
        sendBroadcast(mediaScanIntent)

        Toast.makeText(this, "保存成功，请到系统相册中查看", Toast.LENGTH_SHORT).show()
    }

    private fun saveImageToGallery(context: Context, bitmap: Bitmap, displayName: String?): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Tools")
        val uri =
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            try {
                context.contentResolver.openOutputStream(uri).use { outputStream ->
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG, 100,
                        outputStream!!
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return uri
    }

}