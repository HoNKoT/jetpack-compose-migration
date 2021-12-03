package com.example.jetpackcomposemigration.ext

import android.content.res.Resources

val Int.dp: Int
    get() = (Resources.getSystem().displayMetrics.density * this).toInt()

val Int.dpf: Float
    get() = (Resources.getSystem().displayMetrics.density * this)

val Int.sp: Int
    get() = (Resources.getSystem().displayMetrics.scaledDensity * this).toInt()
