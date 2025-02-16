package com.ady.tools.pages

import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ady.tools.databinding.ActCoverBinding
import com.ady.tools.kit.fontMontserratMedium
import java.io.File
import java.util.UUID

class CoverAct: AppCompatActivity() {
    private lateinit var binding: ActCoverBinding
    private var coverName: String? = "查看大图过渡动效"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.typeface = assets.fontMontserratMedium
        binding.title.text = coverName
        makeCover()
    }

    private fun makeCover() {
        binding.title.setOnLongClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) { // 判断权限
                ActivityCompat.requestPermissions( // 申请权限
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
        if (requestCode == 0x123) { // 申请到了权限
            savePic()
        }
    }


    private fun savePic() { // 保存图片
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
        val displayName = coverName ?: ("ady_" + UUID.randomUUID().toString())
        val values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Tools")
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        if (uri != null) {
            kotlin.runCatching {
                contentResolver.openOutputStream(uri).use { outputStream ->
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG, 100,
                        outputStream!!
                    )
                }
                Toast.makeText(
                    this, "保存成功，" +
                        "请到系统相册中查看 $displayName", Toast.LENGTH_SHORT
                ).show()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}