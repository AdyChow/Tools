package com.ady.tools

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.text = "Glide 支持圆角毛玻璃动态 Webp"
        binding.title.typeface = assets.fontMontserratMedium
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
        saveImageToGallery(this, bitmap, "ady_" + UUID.randomUUID().toString())


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