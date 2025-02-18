package com.ady.tools

import android.app.Application
import android.content.Context
import com.ady.tools.kit.LangUtils
import com.ady.tools.skin.SkinActivityLifecycle

class App : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun attachBaseContext(base: Context) {
        context = base
        super.attachBaseContext(LangUtils.attachBaseContext(base)) // 根据当前本地存储的语言更新上下文
    }

    override fun onCreate() {
        super.onCreate()
        SkinActivityLifecycle.register(this) // 监听全局 activity 生命周期变化
    }
}