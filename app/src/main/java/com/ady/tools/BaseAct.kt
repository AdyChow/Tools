package com.ady.tools

import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.skin.SkinChangeable
import java.lang.ref.WeakReference

/** 如果 activity 希望做应用内的多语言变化，需要继承该 BaseAct，记录了当前页面全部需要做语言变更的元素的弱引用。
 * 也可以不采用继承 BaseAct 的方式，使用 delegate 模式实现相应功能即可 */
open class BaseAct : AppCompatActivity() {

    private val skins = mutableListOf<WeakReference<SkinChangeable>>()

    fun addSkin(skinChangeable: SkinChangeable) {
        skins.add(WeakReference(skinChangeable))
    }

    fun applySkin() {
        skins.forEach {
            it.get()?.apply()
        }
    }

}