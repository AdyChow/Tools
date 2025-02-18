package com.ady.tools.kit

import android.content.Context
import android.content.SharedPreferences

object SpUtil {
    private const val SP = "shared_preference"
    private var mSp: SharedPreferences? = null
    fun init(context: Context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP, Context.MODE_PRIVATE)
        }
    }

    fun save(key: String?, value: Any?) {
        if (value is String) {
            mSp!!.edit().putString(key, value as String?).commit()
        } else if (value is Boolean) {
            mSp!!.edit().putBoolean(key, (value as Boolean?)!!).commit()
        } else if (value is Int) {
            mSp!!.edit().putInt(key, (value as Int?)!!).commit()
        }
    }

    fun getString(key: String?, defValue: String?): String {
        return mSp!!.getString(key, defValue) ?: ""
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return mSp!!.getBoolean(key, defValue)
    }

    fun getInt(key: String?, defValue: Int): Int {
        return mSp!!.getInt(key, defValue)
    }
}