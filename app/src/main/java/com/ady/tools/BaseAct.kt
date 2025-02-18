package com.ady.tools

import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.skin.SkinChangeable
import java.lang.ref.WeakReference

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