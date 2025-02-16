package com.ady.tools.pages

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActSmallPicBinding


class SmallPicAct : AppCompatActivity() {

    private lateinit var binding: ActSmallPicBinding
    var pic: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActSmallPicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pic = binding.pic
        binding.pic.setOnClickListener {
            // 法一：系统自带过渡动效（在切换时，可能会看到整个 window 会稍微有点晃白一下）
//            val intent = Intent(this, BigPicAct::class.java)
//            val options = ActivityOptionsCompat.makeSceneTransitionAnimation( // 创建过渡动画
//                this@SmallPicAct,
//                binding.pic,
//                ViewCompat.getTransitionName(binding.pic)!!
//            )
//            startActivity(intent, options.toBundle())

            // 法二：自定义元素的过渡效果（这种方法可能更适合实际应用中稍微复杂一点的场景）
            BigPicAct.bind(this@SmallPicAct)
            val intent2 = Intent(this, BigPicAct::class.java)
            startActivity(intent2)
        }
    }

}