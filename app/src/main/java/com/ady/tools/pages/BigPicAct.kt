package com.ady.tools.pages

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.ady.tools.databinding.ActBigPicBinding
import java.lang.ref.WeakReference

class BigPicAct : AppCompatActivity() {

    private lateinit var binding: ActBigPicBinding

    companion object {

        private var smallPicAct: WeakReference<SmallPicAct>? = null // 弱引用防止内存泄露
        fun bind(smallPicAct: SmallPicAct) {
            this.smallPicAct = WeakReference(smallPicAct)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        enterAnim()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActBigPicBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportPostponeEnterTransition() // 延迟执行过渡动效
//        supportStartPostponedEnterTransition() // 执行过渡动效
    }

    private fun enterAnim() {
        overridePendingTransition(0, 0) // ban 掉默认的过渡动效
        val smallPic = smallPicAct?.get()?.pic
        smallPic?.let {
            val bigPic = binding.pic
            binding.root.alpha = 0f // 先隐藏大图所在页内容，避免从小变大过程前，会先看到一眼大图
            bigPic.post {
                /** 先将大图缩小到小图位置大小 */
                bigPic.scaleX = smallPic.width.toFloat() / bigPic.width.toFloat()
                bigPic.scaleY = smallPic.height.toFloat() / bigPic.height.toFloat()
                /**（本样例比较简单，大图小图都是屏幕居中位置，所以不需要处理 translation，
                 * 实际情况中很可能还需要处理 translation 的变化）*/

                /** 再从小图位置大小，开始还原大图 */
                val scaleX = ObjectAnimator.ofFloat(bigPic, "scaleX", bigPic.scaleX, 1f)
                val scaleY = ObjectAnimator.ofFloat(bigPic, "scaleY", bigPic.scaleY, 1f)
                val alpha = ObjectAnimator.ofFloat(binding.root, "alpha", 0f, 1f)
                val animatorSet = AnimatorSet() // 组合动画
                animatorSet.playTogether(scaleX, scaleY, alpha)
                animatorSet.duration = 300 // 设置动画时长
                animatorSet.start()
            }
        }
    }

    override fun finish() {
        exitAnim()
    }

    private fun exitAnim() { // 和 enterAnim 的过程几乎相反
        val smallPic = smallPicAct?.get()?.pic
        smallPic?.let {
            val bigPic = binding.pic
            bigPic.post {
                val toScaleX = smallPic.width.toFloat() / bigPic.width.toFloat()
                val toScaleY = smallPic.height.toFloat() / bigPic.height.toFloat()
                val scaleX = ObjectAnimator.ofFloat(bigPic, "scaleX", 1f, toScaleX)
                val scaleY = ObjectAnimator.ofFloat(bigPic, "scaleY", 1f, toScaleY)
                val animatorSet = AnimatorSet() // 组合动画
                animatorSet.playTogether(scaleX, scaleY)
                animatorSet.duration = 300 // 设置动画时长
                animatorSet.doOnEnd {
                    super.finish()
                    overridePendingTransition(0, 0) // ban 掉默认的过渡动效
                }
                animatorSet.start()
            }
        }
        if (smallPic == null) {
            super.finish()
        }
    }
}