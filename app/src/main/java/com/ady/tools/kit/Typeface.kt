package com.ady.tools.kit

import android.content.res.AssetManager
import android.graphics.Typeface


val AssetManager?.fontMontserratRegular: Typeface?
    get() = TypefaceUtils.load(this, "fonts/Montserrat-Regular.ttf")

val AssetManager?.fontMontserratMedium: Typeface?
    get() = TypefaceUtils.load(this, "fonts/Montserrat-Medium.ttf")

val AssetManager?.fontMontserratBold: Typeface?
    get() = TypefaceUtils.load(this, "fonts/Montserrat-Bold.ttf")
