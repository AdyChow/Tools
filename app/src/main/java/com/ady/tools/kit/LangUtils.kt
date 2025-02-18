package com.ady.tools.kit

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import androidx.annotation.StringRes
import com.ady.tools.App
import java.util.Locale

object LangUtils {

    const val CURRENT_LANG = "current_lang"

    private val supportLanguage = arrayListOf("zh", "en")

    /** 根据当前语言获取对应的字符串内容 */
    fun getString(@StringRes defResId: Int): String? {
        var value: String? = ""
        if (defResId == 0) {
            return value
        }
        kotlin.runCatching {
            val locales: LocaleList = App.context.resources.configuration.locales
            val locale = if (locales.isEmpty) Locale.ENGLISH else locales[0]
            val local: String = SpUtil.getString(CURRENT_LANG, "")
            value = if (local == locale.language) { // 如果当前语言未发生改变
                App.context.resources.getString(defResId)
            } else { // 如果当前语言发生了改变
                attachBaseContext(App.context, local).resources.getString(defResId)
            }
        }.onFailure {
            it.printStackTrace()
        }
        return value
    }

    fun switchLanguage(language: String) {
        SpUtil.save(CURRENT_LANG, language)
    }

    fun attachBaseContext(context: Context): Context {
        SpUtil.init(context)
        return attachBaseContext(context, SpUtil.getString(CURRENT_LANG, ""))
    }

    private fun attachBaseContext(context: Context, lang: String): Context {
        return context.createConfigurationContext(updateLocale(context, lang))
    }

    fun updateLocale(context: Context, newLanguage: String): Configuration {
        val resources = context.resources
        val configuration = resources.configuration
        val locale: Locale = getLanguageLocale(newLanguage)
        configuration.setLocales(LocaleList(locale))
        val dm = resources.displayMetrics
        resources.updateConfiguration(configuration, dm)
        return configuration
    }

    private fun getLanguageLocale(language: String): Locale {
        val stringBuffer = StringBuffer()
        stringBuffer.append(language)
        if (supportLanguage.contains(stringBuffer.toString())) {
            return Locale(language)
        }
        return Locale("en")
    }
}