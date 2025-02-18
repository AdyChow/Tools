package com.ady.tools.skin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.LocaleList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.LayoutInflaterCompat
import com.ady.tools.BaseAct
import com.ady.tools.kit.LangUtils
import com.ady.tools.kit.SpUtil
import com.ady.tools.widget.ATextView
import java.util.Locale

object SkinActivityLifecycle : Application.ActivityLifecycleCallbacks {

    fun register(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityPreCreated(activity, savedInstanceState)
        if (activity is BaseAct) {
            installLayoutFactory(activity)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is BaseAct) {
            val locales: LocaleList = activity.resources.configuration.locales
            val locale = if (locales.isEmpty) Locale.ENGLISH else locales[0]
            val local: String = SpUtil.getString(LangUtils.CURRENT_LANG, "")
            val needUpdateSkin = local != locale.language
            if (needUpdateSkin) {
                LangUtils.updateLocale(activity, local)
                activity.applySkin()
            }
        }
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    private fun installLayoutFactory(activity: BaseAct) {
        kotlin.runCatching {
            LayoutInflaterCompat.setFactory2(LayoutInflater.from(activity), object :
                LayoutInflater.Factory2 {
                override fun onCreateView(
                    parent: View?,
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    when (name) {
                        "TextView" -> { // 返回自定义view
                            val o = ATextView(context, attrs)
                            activity.addSkin(o)
                            return o
                        }
                    }
                    return activity.delegate.createView(parent, name, context, attrs) // 调用系统方法
                }

                override fun onCreateView(
                    name: String,
                    context: Context,
                    attrs: AttributeSet
                ): View? {
                    return onCreateView(null, name, context, attrs)
                }
            })
        }.onFailure {
            it.printStackTrace()
        }
    }
}