package com.ivzb.craftlog.util

import android.os.Build
import android.os.Bundle

inline fun <reified T> getParcelable(bundle: Bundle?, key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        bundle?.getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        bundle?.getParcelable(key)
    }
}