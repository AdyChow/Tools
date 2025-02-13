package com.ady.tools

import android.graphics.LinearGradient
import android.graphics.Shader
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance

class ForegroundSpan: CharacterStyle, UpdateAppearance {

    private var startColor = 0

    private var endColor = 0

    private var lineWidth = 0f

    constructor(startColor: Int, endColor: Int, lineWidth: Float) {
        this.startColor = startColor
        this.endColor = endColor
        this.lineWidth = lineWidth
    }
    override fun updateDrawState(tp: TextPaint?) {
        tp?.shader = LinearGradient(0f, 0f, lineWidth, 0f,
            startColor, endColor, Shader.TileMode.REPEAT)

    }
}