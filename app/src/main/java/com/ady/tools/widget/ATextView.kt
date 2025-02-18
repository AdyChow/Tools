package com.ady.tools.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.ady.tools.R
import com.ady.tools.kit.LangUtils
import com.ady.tools.skin.SkinChangeable

class ATextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs),
    SkinChangeable {

    private var mResTextId = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ATextView)
        if (a.hasValue(R.styleable.ATextView_android_text)) {
            mResTextId = a.getResourceId(R.styleable.ATextView_android_text, 0)
        }
        a.recycle()
    }

    override fun apply() {
        if (mResTextId != 0) {
            text = LangUtils.getString(mResTextId)
        }
    }

}